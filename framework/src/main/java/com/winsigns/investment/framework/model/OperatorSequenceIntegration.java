package com.winsigns.investment.framework.model;

import org.springframework.stereotype.Component;

import com.winsigns.investment.framework.integration.AbstractIntegration;

/**
 * 封装了向sequence-service访问的接口
 * 
 * @author yimingjin
 * @since 0.0.2
 *
 */
@Component
public class OperatorSequenceIntegration extends AbstractIntegration {

  private static final String OPERATOR_SEQUENCE_URL = "/operator-sequences";

  @Override
  public String getIntegrationName() {
    // TODO 目前服务太多，小功能暂时合并为同一个服务
    // return "sequence-service";
    return "general-service";
  }

  /**
   * 向sequence-service获取一个指定的序列
   * 
   * @return 获取的序列
   */
  public String getSequence() {
    return this.getNode(getIntegrationURI() + OPERATOR_SEQUENCE_URL).get("operatorSequence")
        .asText();
  }
}
