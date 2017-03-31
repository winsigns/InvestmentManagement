package com.winsigns.investment.framework.measure.resource;

import java.util.ArrayList;

import com.winsigns.investment.framework.hal.HALResponse;
import com.winsigns.investment.framework.measure.MeasureHost;
import com.winsigns.investment.framework.measure.TradingMeasureValue;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 宿主资源
 * <p>
 * 其包含指标
 * 
 * @author yimingjin
 * @since 0.0.3
 *
 * @param <T> 宿主的类型
 */
public class MeasureHostResource<T extends MeasureHost> extends HALResponse<T> {

  @Data
  @AllArgsConstructor
  protected static class MeasureInfo {

    String measureHostType;

    Long measureHostId;

    String measureName;

    Boolean isFloat;

    String version;

    Double value;
  }


  public MeasureHostResource(T measureHost) {
    super(measureHost);
  }

  public void addMeasures() {
    ArrayList<MeasureInfo> values = new ArrayList<MeasureInfo>();

    for (TradingMeasureValue value : this.getContent().getMeasureValues()) {
      values.add(new MeasureInfo(value.getMeasureHost().getHostType().getName(),
          value.getMeasureHost().getId(), value.getMeasure().getName(), value.isFloat(),
          value.getVersion(), value.getValue()));
    }

    this.add("measures", values);
  }

}
