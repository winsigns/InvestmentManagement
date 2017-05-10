package com.winsigns.investment.tradeService.service.common;

import com.winsigns.investment.framework.constant.CurrencyCode;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 模拟资金服务
 * 
 * @author yimingjin
 *
 */
@Data
@AllArgsConstructor
public class RemoteCapitalService {

  String name;

  CurrencyCode currency;

}
