package com.winsigns.investment.framework.exception;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 通用的错误
 * 
 * @author yimingjin
 * @since 0.0.4
 *
 */
@NoArgsConstructor
@ToString
public class CommonError {

  protected static ObjectMapper objectMapper =
      new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

  @Getter
  @Setter
  private Date timestamp;

  @Getter
  @Setter
  private String code;

  @Getter
  @Setter
  private String message;

  static public CommonError fromString(String errorString) {
    try {
      CommonError error = objectMapper.readValue(errorString, CommonError.class);
      return error;
    } catch (IOException e) {
      e.printStackTrace();
      return new CommonError(e.getMessage(), e.getMessage());
    }
  }

  public CommonError(String code, String message) {
    this.code = code;
    this.message = message;
    this.timestamp = new Timestamp(System.currentTimeMillis());
  }

}
