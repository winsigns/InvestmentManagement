package com.winsigns.investment.inventoryService.resource;

import org.springframework.hateoas.Resource;

import com.winsigns.investment.inventoryService.model.FundAccountCapital;

public class FundAccountCapitalResource extends Resource<FundAccountCapital> {

    public FundAccountCapitalResource(FundAccountCapital fundAccountCapital) {
        super(fundAccountCapital);
    }

}
