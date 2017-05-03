package com.winsigns.investment.tradeService.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winsigns.investment.framework.exception.CommonError;
import com.winsigns.investment.framework.exception.CommonException;

/**
 * 用来统一返回 系统的错误
 * 
 * @author yimingjin
 * @since 0.0.4
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
  @ExceptionHandler(value = CommonException.class)
  public Object CommonHandler(HttpServletRequest request, CommonException exception)
      throws Exception {

    CommonError error = new CommonError(exception.getCode(), exception.getFullMessage());

    return new ResponseEntity<Object>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
