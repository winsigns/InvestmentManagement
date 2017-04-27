package com.winsigns.investment.tradeService.resource;

import static com.winsigns.investment.tradeService.service.common.TradeServiceManager.getTradeServiceManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.hateoas.core.Relation;

import com.winsigns.investment.framework.hal.HALResponse;
import com.winsigns.investment.framework.model.Item;
import com.winsigns.investment.tradeService.constant.EntrustOperatorType;
import com.winsigns.investment.tradeService.model.Entrust;
import com.winsigns.investment.tradeService.model.EntrustMessage;
import com.winsigns.investment.tradeService.service.common.IPriceType;
import com.winsigns.investment.tradeService.service.common.ITradeService;
import com.winsigns.investment.tradeService.service.common.ITradeType;

import lombok.Getter;

@Relation(value = "entrust", collectionRelation = "entrusts")
public class EntrustResource extends HALResponse<Entrust> {
  @Getter
  protected final HashMap<String, String> errors = new HashMap<String, String>();

  @Getter
  protected final HashMap<String, String> warnings = new HashMap<String, String>();

  @Getter
  protected final String tradeServiceLabel;

  @Getter
  protected final String tradeTypeLabel;

  @Getter
  protected final String statusLabel;

  @Getter
  protected final String priceTypeLabel;

  @Getter
  protected final List<Item> supportedOperator = new ArrayList<Item>();

  @Getter
  protected final List<Item> supportedTradeService = new ArrayList<Item>();

  @Getter
  protected final List<Item> supportPriceType = new ArrayList<Item>();

  public EntrustResource(Entrust entrust) {
    super(entrust);

    // 0.字段信息
    for (EntrustMessage entrustMessage : entrust.getMessages()) {
      switch (entrustMessage.getMessageType()) {
        case ERROR:
          errors.put(entrustMessage.getFieldName(), entrustMessage.getMessage());
          break;
        case WARNING:
          warnings.put(entrustMessage.getFieldName(), entrustMessage.getMessage());
          break;
        default:
          break;

      }
    }

    // 1.字段的国际化
    this.statusLabel = entrust.getStatus().i18n();
    ITradeService service = getTradeServiceManager().getService(entrust.getTradeService());
    if (service != null) {
      this.tradeServiceLabel = service.getSimpleName();
      if (service.getPriceType(entrust.getPriceType()) != null) {
        this.priceTypeLabel = service.getPriceType(entrust.getPriceType()).i18n();
      } else {
        this.priceTypeLabel = null;
      }
      if (service.getTradeType(entrust.getTradeType()) != null) {
        this.tradeTypeLabel = service.getTradeType(entrust.getTradeType()).i18n();
      } else {
        this.tradeTypeLabel = null;
      }
    } else {
      this.tradeServiceLabel = null;
      this.tradeTypeLabel = null;
      this.priceTypeLabel = null;
    }

    // 2.状态支持的操作
    for (EntrustOperatorType type : entrust.getStatus().getSupportedOperator()) {
      this.supportedOperator.add(new Item(type.name(), type.i18n()));
    }
    // 3.支持的交易服务
    for (ITradeService svc : getTradeServiceManager().getServices()) {
      ITradeType[] tradeTypes = svc.getTradeType();
      for (int i = 0; i < tradeTypes.length; ++i) {
        this.supportedTradeService.add(new Item(svc.getName() + "." + tradeTypes[i].name(),
            svc.getSimpleName() + "-" + tradeTypes[i].i18n()));
      }
    }
    // 4.支持的价格类型
    if (service != null) {
      IPriceType[] priceTypes = service.getPriceType();
      for (int i = 0; i < priceTypes.length; ++i) {
        this.supportPriceType.add(new Item(priceTypes[i].name(), priceTypes[i].i18n()));
      }
    }
  }

}
