package com.winsigns.investment.inventoryService.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.core.Relation;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.winsigns.investment.inventoryService.command.AssignAccountCommand;
import com.winsigns.investment.inventoryService.command.EnfeoffAccountCommand;
import com.winsigns.investment.inventoryService.controller.FundAccountCapitalController;
import com.winsigns.investment.inventoryService.controller.FundAccountCapitalDetailController;
import com.winsigns.investment.inventoryService.model.FundAccountCapital;
import com.winsigns.investment.inventoryService.model.FundAccountCapitalDetail;

public class FundAccountCapitalDetailResourceAssembler
        extends ResourceAssemblerSupport<FundAccountCapitalDetail, FundAccountCapitalDetailResource> {

    public FundAccountCapitalDetailResourceAssembler() {
        super(FundAccountCapitalDetailController.class, FundAccountCapitalDetailResource.class);
    }

    @Override
    public FundAccountCapitalDetailResource toResource(FundAccountCapitalDetail fundAccountCapitalDetail) {

        FundAccountCapitalDetailResource fundAccountCapitalDetailResource = createResourceWithId(
                fundAccountCapitalDetail.getId(), fundAccountCapitalDetail);

        fundAccountCapitalDetailResource.add(linkTo(methodOn(FundAccountCapitalController.class)
                .readFundAccountCapital(fundAccountCapitalDetail.getFundAccountCapital().getId()))
                        .withRel(FundAccountCapital.class.getAnnotation(Relation.class).value()));

        fundAccountCapitalDetailResource.add(linkTo(methodOn(FundAccountCapitalDetailController.class)
                .assignFrom(fundAccountCapitalDetail.getId(), new AssignAccountCommand())).withRel("assign-from"));
        fundAccountCapitalDetailResource.add(linkTo(methodOn(FundAccountCapitalDetailController.class)
                .assignTo(fundAccountCapitalDetail.getId(), new AssignAccountCommand())).withRel("assign-to"));

        fundAccountCapitalDetailResource.add(linkTo(methodOn(FundAccountCapitalDetailController.class)
                .enfeoffFrom(fundAccountCapitalDetail.getId(), new EnfeoffAccountCommand())).withRel("enfeoff-from"));
        fundAccountCapitalDetailResource.add(linkTo(methodOn(FundAccountCapitalDetailController.class)
                .enfeoffTo(fundAccountCapitalDetail.getId(), new EnfeoffAccountCommand())).withRel("enfeoff-to"));

        return fundAccountCapitalDetailResource;
    }

    @Override
    protected FundAccountCapitalDetailResource instantiateResource(FundAccountCapitalDetail entity) {
        return new FundAccountCapitalDetailResource(entity);
    }
}
