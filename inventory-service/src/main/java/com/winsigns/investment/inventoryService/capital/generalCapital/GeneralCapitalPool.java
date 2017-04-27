package com.winsigns.investment.inventoryService.capital.generalCapital;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.winsigns.investment.inventoryService.model.FundAccountCapitalPool;

@Entity
@DiscriminatorValue("general_capital_pool")
public class GeneralCapitalPool extends FundAccountCapitalPool {

}
