package com.winsigns.investment.framework.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.hateoas.Identifiable;

import lombok.Getter;


@MappedSuperclass
public abstract class OperatorEntity implements Identifiable<String> {

  @Id
  @Getter
  @GeneratedValue(generator = "idGenerator")
  @GenericGenerator(name = "idGenerator",
      strategy = "com.winsigns.investment.framework.model.OperatorSequenceGenerate")
  private String id;


  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    OperatorEntity that = (OperatorEntity) o;

    if (id != null ? !id.equals(that.id) : that.id != null)
      return false;

    return true;
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }
}
