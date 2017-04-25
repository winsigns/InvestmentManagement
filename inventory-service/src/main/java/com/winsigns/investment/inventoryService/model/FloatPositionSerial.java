package com.winsigns.investment.inventoryService.model;

import javax.persistence.Entity;

import org.springframework.hateoas.core.Relation;

@Entity
@Relation(value = "position-serial", collectionRelation = "position-serials")
public class FloatPositionSerial extends PositionSerial {

}
