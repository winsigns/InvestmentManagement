package com.winsigns.investment.inventoryService.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.winsigns.investment.framework.measure.MeasureHost;
import com.winsigns.investment.framework.measure.MeasureHostType;
import com.winsigns.investment.framework.spring.SpringManager;
import com.winsigns.investment.inventoryService.measure.FACapitalDetailMHT;

import lombok.Getter;
import lombok.Setter;

@Entity
@Relation(value = "fa-capital-detail", collectionRelation = "fa-capital-details")
public class FundAccountCapitalDetail extends MeasureHost {

  @ManyToOne
  @JsonIgnore
  @Getter
  @Setter
  private FundAccountCapital fundAccountCapital;

  @Getter
  @Setter
  private Long externalCapitalAccountId;

  @Override
  @JsonIgnore
  public MeasureHostType getType() {
    return SpringManager.getApplicationContext().getBean(FACapitalDetailMHT.class);
  }

  @OneToMany(mappedBy = "fundAccountCapitalDetail", cascade = CascadeType.ALL,
      fetch = FetchType.LAZY)
  @JsonIgnore
  List<FundAccountCapitalSerial> fundAccountCapitalSerials =
      new ArrayList<FundAccountCapitalSerial>();
}
