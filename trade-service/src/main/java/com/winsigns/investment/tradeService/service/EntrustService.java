package com.winsigns.investment.tradeService.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.winsigns.investment.tradeService.command.CreateEntrustCommand;
import com.winsigns.investment.tradeService.command.UpdateEntrustCommand;
import com.winsigns.investment.tradeService.constant.EntrustMessageCode;
import com.winsigns.investment.tradeService.constant.EntrustMessageType;
import com.winsigns.investment.tradeService.constant.EntrustStatus;
import com.winsigns.investment.tradeService.integration.InvestServiceIntegration;
import com.winsigns.investment.tradeService.model.Entrust;
import com.winsigns.investment.tradeService.model.EntrustMessage;
import com.winsigns.investment.tradeService.repository.EntrustMessageRepository;
import com.winsigns.investment.tradeService.repository.EntrustRepository;

/**
 * 委托服务
 * <p>
 * 查询一条委托<br>
 * 增加一条委托<br>
 * 修改一条委托<br>
 * 删除一条委托<br>
 * 
 * @author yimingjin
 *
 */
@Service
public class EntrustService {

  @Autowired
  EntrustRepository entrustRepository;

  @Autowired
  InvestServiceIntegration investService;

  @Autowired
  EntrustMessageRepository entrustMessageRepository;

  /**
   * 根据条件查询指定指令下的未删除的委托
   * 
   * @param instructionId
   * @return
   */
  public Collection<Entrust> findNormalEntrustByCondition(Long instructionId) {
    Assert.notNull(instructionId);
    return entrustRepository.findByInstructionIdAndStatusNotOrderByEntrustTimeDesc(instructionId,
        EntrustStatus.DELETED);
  }

  /**
   * 查询一条委托
   * 
   * @param entrustId
   * @return 指定的委托
   */
  public Entrust readEntrust(Long entrustId) {

    Assert.notNull(entrustId);

    Entrust thisEntrust = entrustRepository.findOne(entrustId);

    Assert.notNull(thisEntrust);

    return thisEntrust;
  }

  /**
   * 增加一条委托
   * 
   * @param command
   * @return
   */
  public Entrust addEntrust(CreateEntrustCommand command) {

    Entrust newEntrust = new Entrust();

    newEntrust.setInstructionId(command.getInstructionId());;
    newEntrust.setSecurityId(investService.getInstructionSecurityId(command.getInstructionId()));

    newEntrust = entrustRepository.save(newEntrust);
    check(newEntrust);
    return entrustRepository.save(newEntrust);
  }

  /**
   * 修改一条委托
   * 
   * @param entrustId
   * @param command
   * @return
   */
  @Transactional
  public Entrust updateEntrust(UpdateEntrustCommand command) {
    Long entrustId = command.getEntrustId();
    Assert.notNull(entrustId);
    Entrust thisEntrust = entrustRepository.findOne(entrustId);
    Assert.notNull(thisEntrust);

    thisEntrust.setTradeService(command.getTradeService());
    thisEntrust.setTradeType(command.getTradeType());
    thisEntrust.setBrokerageFirmId(command.getBrokerageFirmId());
    thisEntrust.setPriceType(command.getPriceType());
    thisEntrust.setEntrustPrice(command.getEntrustPrice());
    thisEntrust.setEntrustQuantity(command.getEntrustQuantity());
    check(thisEntrust);
    return entrustRepository.save(thisEntrust);
  }

  protected void check(Entrust thisEntrust) {
    if (!thisEntrust.getMessages().isEmpty()) {
      entrustMessageRepository.delete(thisEntrust.getMessages());
      thisEntrust.getMessages().clear();
    }
    checkSecurityAndDirection(thisEntrust);
  }

  /**
   * 检查委托的交易类型
   * 
   * @param thisInstruction
   */
  protected void checkSecurityAndDirection(Entrust thisEntrust) {

    if (thisEntrust.getTradeService() == null) {
      thisEntrust.addEntrustMessage(new EntrustMessage(thisEntrust, "tradeService",
          EntrustMessageType.ERROR, EntrustMessageCode.TRADE_SERVICE_CANNOT_NULL));
    }

    if (thisEntrust.getTradeType() == null) {
      thisEntrust.addEntrustMessage(new EntrustMessage(thisEntrust, "tradeType",
          EntrustMessageType.ERROR, EntrustMessageCode.TRADE_TYPE_CANNOT_NULL));
    }
  }

  /**
   * 删除一条委托
   * 
   * @param entrustId
   */
  public void deleteEntrust(Long entrustId) {

    Assert.notNull(entrustId);

    Entrust thisEntrust = entrustRepository.findOne(entrustId);

    Assert.notNull(thisEntrust);

    thisEntrust.setStatus(EntrustStatus.DELETED);
    entrustRepository.save(thisEntrust);
  }

}
