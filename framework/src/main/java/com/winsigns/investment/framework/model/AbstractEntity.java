package com.winsigns.investment.framework.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.hateoas.Identifiable;

import lombok.Getter;

/**
 * 带有一个序号的实体基类，跟数据库的具体表进行绑定
 * 
 * @author yimingjin
 * @since 0.0.1
 *
 */
@MappedSuperclass
public abstract class AbstractEntity implements Identifiable<Long> {
  /**
   * 对应于数据库表中的主键，目前采用Long和auto-increasement
   */
  @Id
  @GeneratedValue
  @Getter
  private Long id;

  @Override
  public boolean equals(Object o) {

    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    AbstractEntity that = (AbstractEntity) o;

    if (id != null ? !id.equals(that.id) : that.id != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }
}
