package com.winsigns.investment.inventoryService.service;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.winsigns.investment.framework.spring.SpringManager;
import com.winsigns.investment.inventoryService.capital.common.CapitalServiceManager;
import com.winsigns.investment.inventoryService.capital.common.ICapitalService;
import com.winsigns.investment.inventoryService.command.ApplyResourceCommand;
import com.winsigns.investment.inventoryService.command.ResponseResourceApplication;
import com.winsigns.investment.inventoryService.exception.PortfolioCannotMatchFundAccount;
import com.winsigns.investment.inventoryService.exception.PositionServiceNotSupported;
import com.winsigns.investment.inventoryService.exception.ResourceApplicationExcepiton;
import com.winsigns.investment.inventoryService.exception.SystemError;
import com.winsigns.investment.inventoryService.integration.FundServiceIntegration;
import com.winsigns.investment.inventoryService.model.CapitalSerial;
import com.winsigns.investment.inventoryService.model.PositionSerial;
import com.winsigns.investment.inventoryService.model.ResourceApplicationForm;
import com.winsigns.investment.inventoryService.model.ResourceApplicationForm.ApplyStatus;
import com.winsigns.investment.inventoryService.position.common.IPositionService;
import com.winsigns.investment.inventoryService.position.common.PositionServiceManager;
import com.winsigns.investment.inventoryService.repository.ResourceApplicationFormRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 资源申请服务
 * <p>
 * 资源申请单根据产品账户通过redis的队列进行维护<br>
 * 每次申请插入两条记录，仅有status字段不同<br>
 * 线程的主逻辑中，每个时间片，遍历所有的产品队列<br>
 * 如果队首记录的status为INIT，则pop出该记录进行业务逻辑处理,处理完，再pop出PROCESSING的记录<br>
 * 否则跳过这次循环<br>
 * 这样的处理逻辑的目的，保证多进程处理的时候，同一资源队列只有一条单子在处理
 * 
 * @author yimingjin
 *
 */
@Service
@Slf4j
public class ResourceApplicationService extends Thread implements SmartInitializingSingleton {
  @Bean
  private RedisTemplate<String, ResourceApplicationForm> inventoryTemplate(
      RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, ResourceApplicationForm> template =
        new RedisTemplate<String, ResourceApplicationForm>();
    RedisSerializer<String> stringSerializer = new StringRedisSerializer();
    GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();
    template.setConnectionFactory(redisConnectionFactory);
    template.setKeySerializer(stringSerializer);
    template.setValueSerializer(serializer);
    template.setHashKeySerializer(stringSerializer);
    template.setHashValueSerializer(serializer);

    return template;
  }

  @Autowired
  RedisTemplate<String, ResourceApplicationForm> inventoryTemplate;

  @Autowired
  StringRedisTemplate keyTemplate;

  @Autowired
  FundServiceIntegration fundServiceIntegration;

  @Autowired
  PositionServiceManager positionServiceManager;

  @Autowired
  CapitalServiceManager capitalServiceManager;

  @Autowired
  KafkaTemplate<Boolean, ResponseResourceApplication> resourceTemplate;

  @Autowired
  ResourceApplicationFormRepository resourceApplicationFormRepository;

  DefaultRedisScript<ResourceApplicationForm> script =
      new DefaultRedisScript<ResourceApplicationForm>();

  static final String applyKey = "fund-accounts";
  static final String applyKeyTemp = "fund-accounts:%d";
  static final String applyTopic = "resource-application";

  /**
   * 接受一条资源申请
   * 
   * @param applyInventoryCommand
   */
  public void apply(ApplyResourceCommand applyInventoryCommand) {

    ResourceApplicationForm form = new ResourceApplicationForm();
    form.setVirtualDoneId(applyInventoryCommand.getVirtualDoneId());
    form.setInstructionId(applyInventoryCommand.getInstructionId());
    form.setPortfolioId(applyInventoryCommand.getPortfolioId());
    form.setSecurityId(applyInventoryCommand.getSecurityId());
    form.setCurrency(applyInventoryCommand.getCurrency());
    form.setAppliedCapital(applyInventoryCommand.getAppliedCapital());
    form.setAppliedPosition(applyInventoryCommand.getAppliedPosition());
    form.setCapitalService(applyInventoryCommand.getCapitalService());
    form.setPositionService(applyInventoryCommand.getPositionService());
    form.setLanguage(applyInventoryCommand.getLanguage());
    LocaleContextHolder.setLocale(new Locale(form.getLanguage()));
    ResourceApplicationForm formStatus = form.clone();
    formStatus.setStatus(ApplyStatus.PROCESSING);
    Long fundAccountId =
        fundServiceIntegration.getFundAccountId(applyInventoryCommand.getPortfolioId());

    if (fundAccountId == null) {
      throw new PortfolioCannotMatchFundAccount();
    }

    String key = String.format(applyKeyTemp, fundAccountId);
    keyTemplate.boundSetOps(applyKey).add(key);
    inventoryTemplate.boundListOps(key).leftPushAll(form, formStatus);
  }

  private final Long sleepTime = 100L;

  /**
   * 主函数轮询资源申请单
   */
  @Override
  public void run() {
    log.info("线程:" + Thread.currentThread().getName() + "运行中.....");
    while (this.isAlive()) {
      try {
        sleep(sleepTime);

        Set<String> keys = keyTemplate.boundSetOps(applyKey).members();
        for (String key : keys) {
          // 将逻辑放在lua中，由redis调用，方便事务的管理
          ResourceApplicationForm form =
              inventoryTemplate.execute(script, Collections.singletonList(key), new Object[] {});

          if (form != null) {
            if (form.getStatus() == ApplyStatus.INIT) {
              processForm(form);
              inventoryTemplate.boundListOps(key).rightPop(1, TimeUnit.MILLISECONDS);
            } else {
              processErrorForm(form);
            }
          }
        }
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

    }
  }

  @Override
  public void afterSingletonsInstantiated() {
    this.start();
  }

  @PostConstruct
  public void init() {
    script.setScriptSource(
        new ResourceScriptSource(new ClassPathResource("redis/resourceApply.lua")));
    script.setResultType(ResourceApplicationForm.class);// Must Set
  }

  /**
   * 处理申请单
   * 
   * @param form
   */
  public void processForm(ResourceApplicationForm form) {

    form = resourceApplicationFormRepository.save(form);

    PlatformTransactionManager platformTransactionManager =
        SpringManager.getApplicationContext().getBean(PlatformTransactionManager.class);

    // PROPAGATION_REQUIRES_NEW 表示挂起当前事务，重新启动一个新事务
    DefaultTransactionDefinition transactionDefinition =
        new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    TransactionStatus status = platformTransactionManager.getTransaction(transactionDefinition);
    LocaleContextHolder.setLocale(new Locale(form.getLanguage()));
    try {
      IPositionService positionService =
          positionServiceManager.getService(form.getPositionService());
      if (positionService == null) {
        throw new PositionServiceNotSupported();
      }

      // TODO 这里需要给positionSerials和capitalSerials记录formId;
      List<PositionSerial> positionSerials = positionService.apply(form.getPortfolioId(),
          form.getSecurityId(), null, form.getAppliedPosition());

      ICapitalService capitalService = capitalServiceManager.getService(form.getCapitalService());
      if (capitalService == null) {
        throw new PositionServiceNotSupported();
      }

      List<CapitalSerial> capitalSerials =
          capitalService.apply(fundServiceIntegration.getFundAccountId(form.getPortfolioId()),
              form.getCurrency(), form.getAppliedCapital());
      platformTransactionManager.commit(status);
      ResponseResourceApplication application = new ResponseResourceApplication();
      application.setVirtualDoneId(form.getVirtualDoneId());
      application.setApplicationFormId(form.getId());
      application.setInstructionId(form.getInstructionId());
      resourceTemplate.send(applyTopic, true, application);

      form.setStatus(ApplyStatus.FINISHED);

    } catch (ResourceApplicationExcepiton e) {
      platformTransactionManager.rollback(status);
      doError(form, false, e.getCode(), e.getFullMessage());
    } catch (Exception e) {
      platformTransactionManager.rollback(status);
      doError(form, false, e.getMessage(), e.getMessage());
    } finally {
      resourceApplicationFormRepository.save(form);
    }
  }

  /**
   * 处理失败的流程
   * 
   * @param form
   */
  private void processErrorForm(ResourceApplicationForm form) {
    form = resourceApplicationFormRepository.findByVirtualDoneId(form.getVirtualDoneId());
    if (form != null) {
      SystemError error = new SystemError();
      doError(form, false, error.getCode(), error.getMessage());
      resourceApplicationFormRepository.save(form);
    } else {
      // TODO
    }
  }

  /**
   * 申请单处理失败的返回
   * <p>
   * 更新数据库状态<br>
   * 发送kafka消息
   * 
   * @param form
   * @param result
   * @param code
   * @param message
   */
  private void doError(ResourceApplicationForm form, boolean result, String code, String message) {
    ResponseResourceApplication application = new ResponseResourceApplication();
    application.getHeader().setResult(result);
    application.getHeader().setCode(code);
    application.getHeader().setMessage(message);
    application.setVirtualDoneId(form.getVirtualDoneId());
    application.setApplicationFormId(form.getId());
    application.setInstructionId(form.getInstructionId());
    form.setMessage(message);
    form.setStatus(ApplyStatus.FAILED);
    resourceTemplate.send(applyTopic, false, application);
  }
}
