package com.winsigns.investment.inventoryService.resource;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.winsigns.investment.inventoryService.controller.FundAccountCapitalController;
import com.winsigns.investment.inventoryService.model.FundAccountCapital;

public class FundAccountCapitalResourceAssembler
		extends ResourceAssemblerSupport<FundAccountCapital, FundAccountCapitalResource> {

	public FundAccountCapitalResourceAssembler() {
		super(FundAccountCapitalController.class, FundAccountCapitalResource.class);
	}

	@Override
	public FundAccountCapitalResource toResource(FundAccountCapital fundAccountCapital) {
		return createResourceWithId(fundAccountCapital.getId(), fundAccountCapital,
				fundAccountCapital.getFundAccountId());
	}

	@Override
	protected FundAccountCapitalResource instantiateResource(FundAccountCapital entity) {
		return new FundAccountCapitalResource(entity);
	}
}
