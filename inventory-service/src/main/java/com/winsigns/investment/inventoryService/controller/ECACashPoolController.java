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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.winsigns.investment.inventoryService.command.CashChangeCommand;
import com.winsigns.investment.inventoryService.model.ECACashPool;
import com.winsigns.investment.inventoryService.resource.ECACashPoolResource;
import com.winsigns.investment.inventoryService.resource.ECACashPoolResourceAssembler;
import com.winsigns.investment.inventoryService.service.ECACashPoolService;

@RestController
@RequestMapping(path = "/funds/{fundId}/externalCapitalAccounts/{externalCapitalAccountId}/eCACashPools", produces = {
        HAL_JSON_VALUE, APPLICATION_JSON_VALUE, APPLICATION_JSON_UTF8_VALUE })
public class ECACashPoolController {

    @Autowired
    ECACashPoolService eCACashPoolService;

    // 查询资金
    @GetMapping
    public Resources<ECACashPoolResource> readExternalCapitalAccountCapitals(@PathVariable Long fundId,
            @PathVariable Long externalCapitalAccountId) {
        Link link = linkTo(ECACashPoolController.class, fundId, externalCapitalAccountId).withSelfRel();

        return new Resources<ECACashPoolResource>(new ECACashPoolResourceAssembler().toResources(
                eCACashPoolService.readExternalCapitalAccountCapitals(fundId, externalCapitalAccountId)), link);
    }

    @GetMapping("/{eCACashPoolId}")
    public ECACashPoolResource readECACashPool(@PathVariable Long eCACashPoolId) {
        ECACashPool eCACashPool = eCACashPoolService.findOne(eCACashPoolId);
        ECACashPoolResource eCACashPoolResource = new ECACashPoolResourceAssembler().toResource(eCACashPool);

        return eCACashPoolResource;
    }

    @PostMapping
    public ResponseEntity<?> createECACashPool(@PathVariable Long fundId, @PathVariable Long externalCapitalAccountId,
            @RequestBody CashChangeCommand cashChangeCommand) {

        ECACashPool eCACashPool = eCACashPoolService.addECACashPool(fundId, externalCapitalAccountId,
                cashChangeCommand);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(linkTo(methodOn(ECACashPoolController.class, fundId, externalCapitalAccountId)
                .readECACashPool(eCACashPool.getId())).toUri());
        return new ResponseEntity<Object>(eCACashPool, responseHeaders, HttpStatus.CREATED);
    }

    // 转入资金
    @RequestMapping(value = "/{eCACashPoolId}/transferTo", method = RequestMethod.POST)
    public ECACashPoolResource transferToExternalCapitalAccount(@PathVariable Long fundId,
            @PathVariable Long externalCapitalAccountId, @RequestBody CashChangeCommand capitalChangeCommand) {
        return new ECACashPoolResourceAssembler().toResource(eCACashPoolService.transferToExternalCapitalAccount(fundId,
                externalCapitalAccountId, capitalChangeCommand));
    }

    // 转出资金
    @RequestMapping(value = "/{eCACashPoolId}/transferFrom", method = RequestMethod.POST)
    public ECACashPoolResource transferFromExternalCapitalAccount(@PathVariable Long fundId,
            @PathVariable Long externalCapitalAccountId, @RequestBody CashChangeCommand capitalChangeCommand) {
        return new ECACashPoolResourceAssembler().toResource(eCACashPoolService
                .transferFromExternalCapitalAccount(fundId, externalCapitalAccountId, capitalChangeCommand));
    }

    // 调拨入金
    @RequestMapping(value = "/{eCACashPoolId}/allotInFrom/{matchEexternalCapitalAccountId}", method = RequestMethod.POST)
    public Resources<ECACashPoolResource> allotInCapital(@PathVariable Long fundId,
            @PathVariable Long externalCapitalAccountId, @PathVariable Long matchEexternalCapitalAccountId,
            @RequestBody CashChangeCommand capitalChangeCommand) {
        Link link = linkTo(ECACashPoolController.class).withSelfRel();
        return new Resources<ECACashPoolResource>(new ECACashPoolResourceAssembler().toResources(eCACashPoolService
                .allot(fundId, externalCapitalAccountId, matchEexternalCapitalAccountId, capitalChangeCommand)), link);
    }

    // 调拨出金
    @RequestMapping(value = "/{eCACashPoolId}/allotOutTo/{matchEexternalCapitalAccountId}", method = RequestMethod.POST)
    public Resources<ECACashPoolResource> allotOutCapital(@PathVariable Long fundId,
            @PathVariable Long externalCapitalAccountId, @RequestParam Long matchEexternalCapitalAccountId,
            @RequestBody CashChangeCommand capitalChangeCommand) {
        Link link = linkTo(ECACashPoolController.class).withSelfRel();
        return new Resources<ECACashPoolResource>(new ECACashPoolResourceAssembler().toResources(eCACashPoolService
                .allot(fundId, matchEexternalCapitalAccountId, externalCapitalAccountId, capitalChangeCommand)), link);
    }

}
