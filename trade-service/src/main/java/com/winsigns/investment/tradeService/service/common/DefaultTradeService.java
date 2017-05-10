package com.winsigns.investment.tradeService.service.common;

import com.winsigns.investment.framework.i18n.i18nHelper;
import com.winsigns.investment.tradeService.command.CommitInstructionCommand;
import com.winsigns.investment.tradeService.model.Done;

/**
 * 该类的作用：
 * <p>
 * 在查询服务的时候，不再需要判断是否为null，简化代码
 * 
 * @author yimingjin
 *
 */
public class DefaultTradeService implements ITradeService {

  @Override
  public String getName() {
    return this.getClass().getSimpleName();
  }

  @Override
  public String getSimpleName() {
    return i18nHelper.i18n(getName());
  }

  @Override
  public RemoteInvestService getSupportedInvestService() {
    return null;
  }

  @Override
  public RemoteCapitalService getUsedCapitalService() {
    return null;
  }

  @Override
  public RemotePositionService getUsedPositionService() {
    return null;
  }

  @Override
  public String getSupportedSecurity() {
    return null;
  }

  @Override
  public ITradeType[] getTradeType() {
    return null;
  }

  @Override
  public ITradeType getTradeType(String name) {
    return null;
  }

  @Override
  public IPriceType[] getPriceType() {
    return null;
  }

  @Override
  public IPriceType getPriceType(String name) {
    return null;
  }

  @Override
  public Resource calculateRequiredResource(CommitInstructionCommand command) {
    return null;
  }

  @Override
  public void virtualDone(CommitInstructionCommand command, Resource resource) {

  }

  @Override
  public void done(Done thisDone) {

  }


}
