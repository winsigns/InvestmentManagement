package com.winsigns.investment.fundService.resource;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.winsigns.investment.fundService.controller.FundAccountController;
import com.winsigns.investment.fundService.model.FundAccount;

public class FundAccountResourceAssembler extends ResourceAssemblerSupport<FundAccount, FundAccountResource> {

	public FundAccountResourceAssembler() {
		super(FundAccountController.class, FundAccountResource.class);
	}

	@Override
	public FundAccountResource toResource(FundAccount fundAccount) {
		return createResourceWithId(fundAccount.getId(), fundAccount, fundAccount.getFund().getId());
	}

	@Override
	protected FundAccountResource instantiateResource(FundAccount entity) {
		return new FundAccountResource(entity);
	}
}
