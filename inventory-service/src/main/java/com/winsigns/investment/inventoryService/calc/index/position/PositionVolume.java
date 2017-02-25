package com.winsigns.investment.inventoryService.calc.index.position;

import com.winsigns.investment.measure.AbstractMeasure;
import com.winsigns.investment.measure.data.MeasureData;

/**
 * Created by jmx on 2017/2/25.
 */
public class PositionVolume extends AbstractMeasure{
    @Override
    public String getHost() {
        return "inventory";
    }

    @Override
    public String getName() {
        return "position.position-volume";
    }

    @Override
    public MeasureData calc(String key, MeasureData value, boolean isInternal, MeasureData stateValue) {
        //TODO: 获得昨日持仓量
        int yt_pos = 10;

        int trade_pos_volume = 0;
        //TODO: 通过value获得标的等信息。根据这些信息获得相关数据对应版本的信息
        //for(trade_service : get_dep_trader_service(value.related_info){
        //  temp_pos_volume_delta += get_trade_pos_volume_delta(trade_service, value.version)
        //  if (emp_pos_volume_delta == null){
        //       //有数据没有准备好，直接跳过不再计算
        //       return null;
        //  }
        //  trade_pos_volume += temp_pos_volume_delta;
        //}

        MeasureData result = new MeasureData();
        result.version = value.version;
        result.value = yt_pos + trade_pos_volume;

        return result;
    }

    @Override
    public void createDependencies() {

    }
}
