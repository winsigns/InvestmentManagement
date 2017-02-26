package com.winsigns.investment.fundService.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.core.Relation;

import com.winsigns.investment.fundService.controller.ExternalCapitalAccountController;
import com.winsigns.investment.fundService.controller.FundAccountController;
import com.winsigns.investment.fundService.hal.HALResponse;
import com.winsigns.investment.fundService.model.ExternalCapitalAccount;
import com.winsigns.investment.fundService.model.Fund;
import com.winsigns.investment.fundService.model.FundAccount;

public class FundResource extends HALResponse<Fund> {

    public FundResource(Fund fund) {
        super(fund);

        Long fundId = fund.getId();
        add(linkTo(methodOn(FundAccountController.class).readFundAccounts(fundId))
                .withRel(FundAccount.class.getAnnotation(Relation.class).collectionRelation()));
        add(linkTo(methodOn(ExternalCapitalAccountController.class).readExternalCapitalAccounts(fundId))
                .withRel(ExternalCapitalAccount.class.getAnnotation(Relation.class).collectionRelation()));
    }

}
