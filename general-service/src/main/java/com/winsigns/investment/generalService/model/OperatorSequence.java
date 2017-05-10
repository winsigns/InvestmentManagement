package com.winsigns.investment.generalService.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author yimingjin
 *
 */
@ToString
@JsonInclude(Include.NON_NULL)
public class OperatorSequence {

  @Getter
  @Setter
  String operatorSequence;

  public OperatorSequence(String sequence) {
    this.operatorSequence = sequence;
  }

  public static OperatorSequence formatSequence(String date, Long sequence) {
    return new OperatorSequence(String.format("%s%012d", date.substring(2), sequence));
  }
}
