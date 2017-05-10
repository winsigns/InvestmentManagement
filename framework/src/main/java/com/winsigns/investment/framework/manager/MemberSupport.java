package com.winsigns.investment.framework.manager;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 成员的通用基类
 * 
 * @author yimingjin
 * @since 0.0.4
 * 
 * @param <T>
 */
@SuppressWarnings("rawtypes")
public class MemberSupport<T extends ManagerSupport> implements IMember {

  @Autowired
  T manager;

  @Override
  public String getName() {
    return this.getClass().getSimpleName();
  }

  @SuppressWarnings("unchecked")
  @Override
  @PostConstruct
  public void register() {
    manager.register(this);
  }

}
