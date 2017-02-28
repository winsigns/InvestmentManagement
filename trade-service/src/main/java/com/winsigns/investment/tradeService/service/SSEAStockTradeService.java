package com.winsigns.investment.tradeService.service;

@SupportedInvestService("stock")
public class SSEAStockTradeService extends TradeServiceBase {

  static String name = "sse-a-stock";

  static String buy = "buy";
  static String sell = "sell";

  @Override
  Double calculateRequiredCapital() {

    Double requiredCapital = 0.0;

    if (metadata.getInvestDirection().equals(buy)) {
      Double costPrice = 0.0;
      // TODO 不限价需要获取价格
      if (metadata.getCostPrice() == null) {

      } else {
        costPrice = metadata.getCostPrice();
      }

      switch (metadata.getVolumeType()) {
        case AmountType:
          requiredCapital = metadata.getAmount();
          break;
        case FixedType:
          requiredCapital = costPrice * metadata.getQuantity();
          break;
        default:
          requiredCapital = costPrice * metadata.getQuantity();
          break;
      }
    }
    return requiredCapital;
  }

  @Override
  Integer calculateRequiredPosition() {
    Integer requiredPosition = 0;

    if (metadata.getInvestDirection().equals(sell)) {

      Double costPrice = 0.0;
      // TODO 不限价需要获取价格
      if (metadata.getCostPrice() == null) {

      } else {
        costPrice = metadata.getCostPrice();
      }

      switch (metadata.getVolumeType()) {
        case AmountType:
          requiredPosition = (int) Math.floor(metadata.getAmount() / costPrice);
          break;
        case FixedType:
          requiredPosition = metadata.getQuantity();
          break;
        default:
          requiredPosition = metadata.getQuantity();
          break;
      }
    }

    return requiredPosition;
  }

  @Override
  public String getName() {
    return name;
  }

}
