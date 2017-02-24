package com.winsigns.investment.inventoryService.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.winsigns.investment.inventoryService.command.CashChangeCommand;
import com.winsigns.investment.inventoryService.controller.ECACashPoolController;
import com.winsigns.investment.inventoryService.hal.HALResponse;
import com.winsigns.investment.inventoryService.model.ECACashPool;

public class ECACashPoolResource extends HALResponse<ECACashPool> {

    public ECACashPoolResource(ECACashPool externalCapitalAccountCapital) {
        super(externalCapitalAccountCapital);

        Long fundId = externalCapitalAccountCapital.getFundId();
        Long externalCapitalAccountId = externalCapitalAccountCapital.getExternalCapitalAccountId();
        add(linkTo(methodOn(ECACashPoolController.class, fundId, externalCapitalAccountId,
                externalCapitalAccountCapital.getId()).transferToExternalCapitalAccount(fundId,
                        externalCapitalAccountId, new CashChangeCommand())).withRel("transferTo"));
        add(linkTo(methodOn(ECACashPoolController.class, fundId, externalCapitalAccountId,
                externalCapitalAccountCapital.getId()).transferFromExternalCapitalAccount(fundId,
                        externalCapitalAccountId, new CashChangeCommand())).withRel("transferFrom"));
    }

}
