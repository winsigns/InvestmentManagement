package com.winsigns.investment.inventoryService.controller;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.winsigns.investment.inventoryService.command.AssignAccountCommand;
import com.winsigns.investment.inventoryService.command.CreateFundAccountCapitalDetailCommand;
import com.winsigns.investment.inventoryService.command.EnfeoffAccountCommand;
import com.winsigns.investment.inventoryService.model.FundAccountCapitalDetail;
import com.winsigns.investment.inventoryService.resource.FundAccountCapitalDetailResource;
import com.winsigns.investment.inventoryService.resource.FundAccountCapitalDetailResourceAssembler;
import com.winsigns.investment.inventoryService.service.FundAccountCapitalDetailService;

@RestController
@RequestMapping(path = "/fa-capital-details", produces = { HAL_JSON_VALUE, APPLICATION_JSON_VALUE,
        APPLICATION_JSON_UTF8_VALUE })
public class FundAccountCapitalDetailController {

    @Autowired
    FundAccountCapitalDetailService fundAccountCapitalDetailService;

    @GetMapping
    public Resources<FundAccountCapitalDetailResource> readFundAccountCapitalDetails() {
        Link link = linkTo(FundAccountCapitalDetailController.class).withSelfRel();
        return new Resources<FundAccountCapitalDetailResource>(
                new FundAccountCapitalDetailResourceAssembler().toResources(fundAccountCapitalDetailService.findAll()),
                link);
    }

    @GetMapping("/{faCapitalDetailId}")
    public FundAccountCapitalDetailResource readFundAccountCapitalDetail(@PathVariable Long faCapitalDetailId) {
        FundAccountCapitalDetail fundAccountCapitalDetail = fundAccountCapitalDetailService.findOne(faCapitalDetailId);
        FundAccountCapitalDetailResource fundAccountCapitalResource = new FundAccountCapitalDetailResourceAssembler()
                .toResource(fundAccountCapitalDetail);

        return fundAccountCapitalResource;
    }

    @PostMapping
    public ResponseEntity<?> createFundAccountCapital(
            @RequestBody CreateFundAccountCapitalDetailCommand createFundAccountCapitalDetailCommand) {

        FundAccountCapitalDetail fundAccountCapitalDetail = fundAccountCapitalDetailService
                .addFundAccountCapitalDetail(createFundAccountCapitalDetailCommand);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(linkTo(methodOn(FundAccountCapitalDetailController.class)
                .readFundAccountCapitalDetail(fundAccountCapitalDetail.getId())).toUri());
        return new ResponseEntity<Object>(fundAccountCapitalDetail, responseHeaders, HttpStatus.CREATED);
    }

    @PostMapping("/{faCapitalDetailId}/assign-from")
    public FundAccountCapitalDetailResource assignFrom(@PathVariable Long faCapitalDetailId,
            @RequestBody AssignAccountCommand assignAccountCommand) {
        return new FundAccountCapitalDetailResourceAssembler()
                .toResource(fundAccountCapitalDetailService.assignFrom(faCapitalDetailId, assignAccountCommand));
    }

    @PostMapping("/{faCapitalDetailId}/assign-to")
    public FundAccountCapitalDetailResource assignTo(@PathVariable Long faCapitalDetailId,
            @RequestBody AssignAccountCommand assignAccountCommand) {
        return new FundAccountCapitalDetailResourceAssembler()
                .toResource(fundAccountCapitalDetailService.assignTo(faCapitalDetailId, assignAccountCommand));
    }

    @PostMapping("/{faCapitalDetailId}/enfeoff-from")
    public Resources<FundAccountCapitalDetailResource> enfeoffFrom(@PathVariable Long faCapitalDetailId,
            @RequestBody EnfeoffAccountCommand enfeoffAccountCommand) {
        Link link = linkTo(methodOn(FundAccountCapitalDetailController.class).enfeoffFrom(faCapitalDetailId,
                enfeoffAccountCommand)).withSelfRel();
        return new Resources<FundAccountCapitalDetailResource>(new FundAccountCapitalDetailResourceAssembler()
                .toResources(fundAccountCapitalDetailService.enfeoff(faCapitalDetailId,
                        enfeoffAccountCommand.getMatchFACapitalDetailId(), enfeoffAccountCommand.getAssignedCash())),
                link);
    }

    @PostMapping("/{faCapitalDetailId}/enfeoff-to")
    public Resources<FundAccountCapitalDetailResource> enfeoffTo(@PathVariable Long faCapitalDetailId,
            @RequestBody EnfeoffAccountCommand enfeoffAccountCommand) {
        Link link = linkTo(
                methodOn(FundAccountCapitalDetailController.class).enfeoffTo(faCapitalDetailId, enfeoffAccountCommand))
                        .withSelfRel();
        return new Resources<FundAccountCapitalDetailResource>(new FundAccountCapitalDetailResourceAssembler()
                .toResources(fundAccountCapitalDetailService.enfeoff(enfeoffAccountCommand.getMatchFACapitalDetailId(),
                        faCapitalDetailId, enfeoffAccountCommand.getAssignedCash())),
                link);
    }

}
