package com.winsigns.investment.generalService.service;

import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class ChronoService {

  /**
   * 获取系统日期
   * 
   * @return
   */
  public Date getSystemDate() {
    return new Date();
  }

  /**
   * 获取系统时间
   * 
   * @return
   */
  public Date getSystemTime() {
    return new Date();
  }
}
