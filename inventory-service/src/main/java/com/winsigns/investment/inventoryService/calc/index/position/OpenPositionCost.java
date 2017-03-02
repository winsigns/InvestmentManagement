package com.winsigns.investment.inventoryService.calc.index.position;

import com.winsigns.investment.framework.measure.AbstractMeasure;
import com.winsigns.investment.framework.measure.data.MeasureData;

/**
 * Created by jmx on 2017/2/25.
 */
public class OpenPositionCost extends AbstractMeasure {

  @Override
  public String getHost() {
    return "inventory";
  }

  @Override
  public String getName() {
    return "position.open-position-cost";
  }

  @Override
  public MeasureData calc(String key, MeasureData value, boolean isInternal,
      MeasureData stateValue) {
    // TODO: 获得昨日开仓成本价
    double yt_open_pos_cost = 100.0;
    // TODO: 获得昨日持仓量
    int yt_pos = 10;

    double trade_buy_amount = 0.0;
    int trade_buy_volume = 0;
    // TODO: 通过value获得标的等信息。根据这些信息获得相关数据对应版本的信息
    // for(trade_service : get_dep_trader_service(value.related_info){
    // temp_buy_amount += get_trade_buy_amount(trade_service, value.version)
    // temp_buy_volume += get_trade_buy_volume(trade_service, value.version)
    // if (temp_buy_amount == null || temp_buy_volume == null){
    // //有数据没有准备好，直接跳过不再计算
    // return null;
    // }
    // trade_buy_amount += temp_buy_amount;
    // trade_buy_volume += temp_buy_volume;
    // }

    MeasureData result = new MeasureData();
    result.version = value.version;
    result.value = (yt_open_pos_cost * yt_pos + trade_buy_amount * trade_buy_volume)
        / (yt_pos + trade_buy_volume);

    return result;
  }

  @Override
  public void createDependencies() {
    dependencies.add("trade.buy-amount");
    dependencies.add("trade.buy-volume");
  }
}
