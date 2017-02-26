package com.winsigns.investment.fundService.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.core.Relation;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.winsigns.investment.fundService.controller.FundController;
import com.winsigns.investment.fundService.model.ExternalCapitalAccount;
import com.winsigns.investment.fundService.model.Fund;
import com.winsigns.investment.fundService.model.FundAccount;

public class FundResourceAssembler extends ResourceAssemblerSupport<Fund, FundResource> {

    public FundResourceAssembler() {
        super(FundController.class, FundResource.class);
    }

    @Override
    public FundResource toResource(Fund fund) {
        FundResource resource = createResourceWithId(fund.getId(), fund);

        Long fundId = fund.getId();
        resource.add(linkTo(methodOn(FundController.class).readFundAccounts(fundId))
                .withRel(FundAccount.class.getAnnotation(Relation.class).collectionRelation()));
        resource.add(linkTo(methodOn(FundController.class).readExternalCapitalAccounts(fundId))
                .withRel(ExternalCapitalAccount.class.getAnnotation(Relation.class).collectionRelation()));
        return resource;
    }

    @Override
    protected FundResource instantiateResource(Fund entity) {
        return new FundResource(entity);
    }

}
