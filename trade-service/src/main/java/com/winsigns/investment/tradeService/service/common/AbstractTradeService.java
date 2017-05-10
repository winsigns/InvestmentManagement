package com.winsigns.investment.tradeService.service.common;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsigns.investment.framework.exception.CommonException;
import com.winsigns.investment.framework.i18n.i18nHelper;
import com.winsigns.investment.tradeService.command.CommitInstructionCommand;
import com.winsigns.investment.tradeService.constant.VirtualDoneStatus;
import com.winsigns.investment.tradeService.integration.InventoryServiceIntegration;
import com.winsigns.investment.tradeService.model.VirtualDone;
import com.winsigns.investment.tradeService.repository.VirtualDoneRepository;


/**
 * 实现一种简单的交易服务的抽象类
 * 
 * @author yimingjin
 *
 */
@Service
public abstract class AbstractTradeService implements ITradeService {

  @Autowired
  TradeServiceManager tradeServiceManager;

  @Autowired
  protected InventoryServiceIntegration inventoryService;

  @Autowired
  protected VirtualDoneRepository virtualDoneRepository;

  @PostConstruct
  public void register() {
    tradeServiceManager.register(this);
  }

  @Override
  public String getName() {
    return this.getClass().getSimpleName();
  }

  @Override
  public String getSimpleName() {
    return i18nHelper.i18n(getName());
  }

  @Override
  public ITradeType getTradeType(String name) {
    for (ITradeType type : this.getTradeType()) {
      if (type.name().equals(name)) {
        return type;
      }
    }
    return null;
  }

  @Override
  public IPriceType getPriceType(String name) {
    for (IPriceType type : this.getPriceType()) {
      if (type.name().equals(name)) {
        return type;
      }
    }
    return null;
  }

  @Override
  public void virtualDone(CommitInstructionCommand command, Resource resource) {

    VirtualDone thisDone = new VirtualDone();

    thisDone.setInstructionId(command.getInstructionId());
    thisDone.setPortfolioId(command.getPortfolioId());
    thisDone.setSecurityId(command.getSecurityId());
    thisDone.setInvestService(command.getInvestService());
    thisDone.setInvestType(command.getInvestType());
    thisDone.setCurrency(command.getCurrency());
    thisDone.setTradeService(this.getName());
    thisDone.setAppliedCapital(resource.getAppliedCapital());
    thisDone.setAppliedPosition(resource.getAppliedPosition());
    thisDone = virtualDoneRepository.save(thisDone);

    try {
      thisDone.applyResource();
      thisDone.setStatus(VirtualDoneStatus.PROCESSING);
    } catch (CommonException e) {
      thisDone.setStatus(VirtualDoneStatus.CANCELED);
      throw e;
    } finally {
      thisDone = virtualDoneRepository.save(thisDone);
    }
  }

}
