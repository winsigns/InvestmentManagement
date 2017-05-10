package com.winsigns.investment.tradeService.model;

import static com.winsigns.investment.tradeService.service.common.TradeServiceManager.getTradeServiceManager;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.winsigns.investment.framework.constant.CurrencyCode;
import com.winsigns.investment.framework.measure.MeasureHost;
import com.winsigns.investment.framework.model.OperatorEntity;
import com.winsigns.investment.framework.spring.SpringManager;
import com.winsigns.investment.tradeService.command.ApplyResourceCommand;
import com.winsigns.investment.tradeService.constant.VirtualDoneStatus;
import com.winsigns.investment.tradeService.exception.SendResourceApplicationFailed;
import com.winsigns.investment.tradeService.integration.InventoryServiceIntegration;
import com.winsigns.investment.tradeService.service.common.ITradeService;

import lombok.Getter;
import lombok.Setter;

@Entity
public class VirtualDone extends OperatorEntity {

  // 指令Id
  @Getter
  @Setter
  private Long instructionId;

  // 投资组合
  @Getter
  @Setter
  private Long portfolioId;

  // 投资标的
  @Getter
  @Setter
  private Long securityId;

  // 投资服务
  @Getter
  @Setter
  private String investService;

  // 投资类型
  @Getter
  @Setter
  private String investType;

  // 币种
  @Getter
  @Setter
  @Enumerated(EnumType.STRING)
  private CurrencyCode currency;

  // 交易服务
  @Getter
  @Setter
  private String tradeService;

  // 申请的资金
  @Getter
  @Setter
  private Double appliedCapital;

  // 申请的持仓
  @Getter
  @Setter
  private Long appliedPosition;

  // 资源申请单ID
  @Getter
  @Setter
  private Long formId;

  // 资源申请单信息
  @Getter
  @Setter
  private String formMessage;

  // 状态
  @Setter
  @Getter
  @Enumerated(EnumType.STRING)
  private VirtualDoneStatus status = VirtualDoneStatus.INIT;

  @Override
  protected List<MeasureHost> doOperator() {

    return null;
  }

  @Override
  public boolean isAffectedFloatMeasure() {
    return true;
  }

  @Override
  public boolean isAffectedNomalMeasure() {
    return false;
  }

  /**
   * 虚拟成交申请资源
   * 
   */
  public void applyResource() throws SendResourceApplicationFailed {

    ITradeService tradeService = getTradeServiceManager().getService(this.getTradeService());

    ApplyResourceCommand applyCmd = new ApplyResourceCommand();
    applyCmd.setVirtualDoneId(this.getId());
    applyCmd.setInstructionId(this.getInstructionId());
    applyCmd.setOperatorSequence(getOperatorSequence());
    applyCmd.setPortfolioId(this.getPortfolioId());
    applyCmd.setSecurityId(this.getSecurityId());
    applyCmd.setCurrency(this.getCurrency());
    applyCmd.setCapitalService(tradeService.getUsedCapitalService().getName());
    applyCmd.setPositionService(tradeService.getUsedPositionService().getName());
    applyCmd.setAppliedCapital(this.getAppliedCapital());
    applyCmd.setAppliedPosition(this.getAppliedPosition());

    SpringManager.getApplicationContext().getBean(InventoryServiceIntegration.class)
        .applyResource(applyCmd);

  }
}
