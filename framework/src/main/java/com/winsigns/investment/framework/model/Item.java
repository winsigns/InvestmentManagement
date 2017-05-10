package com.winsigns.investment.framework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用来存储字典项
 * 
 * @author yimingjin
 * @since 0.0.3
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

  protected Object value;

  protected String label;
}
