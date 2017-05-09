package com.winsigns.investment.framework.manager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Getter;

/**
 * 管理者的通用基类
 * 
 * @author yimingjin
 * @since 0.0.4
 *
 * @param <T>
 */
@Component
public abstract class ManagerSupport<T extends IMember> {

  @Getter
  protected List<T> members = new ArrayList<T>();


  public void register(T service) {
    this.members.add(service);
  }

  public T getMember(String name) {
    T result = this.getDefaultMember();
    for (T member : members) {
      if (member.getName().equals(name)) {
        result = member;
      }
    }
    return result;
  }

  public abstract T getDefaultMember();

}
