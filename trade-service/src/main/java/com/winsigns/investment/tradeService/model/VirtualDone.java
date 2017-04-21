package com.winsigns.investment.tradeService.model;

import static com.winsigns.investment.tradeService.service.common.TradeServiceManager.getTradeServiceManager;

import java.util.List;

import javax.persistence.Entity;

import com.winsigns.investment.framework.measure.MeasureHost;
import com.winsigns.investment.framework.model.OperatorEntity;
import com.winsigns.investment.framework.spring.SpringManager;
import com.winsigns.investment.tradeService.command.ApplyResourceCommand;
import com.winsigns.investment.tradeService.constant.CurrencyCode;
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
  private CurrencyCode currency;

  @Getter
  @Setter
  private String tradeService;

  @Getter
  @Setter
  private Double appliedCapital;

  @Getter
  @Setter
  private Long appliedPosition;

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

  public void applyResource() {

    ITradeService tradeService = getTradeServiceManager().getService(this.getTradeService());

    ApplyResourceCommand applyCmd = new ApplyResourceCommand();
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
