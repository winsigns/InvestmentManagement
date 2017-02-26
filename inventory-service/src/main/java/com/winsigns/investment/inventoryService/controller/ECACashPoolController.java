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

import com.winsigns.investment.inventoryService.command.AllotAccountCommand;
import com.winsigns.investment.inventoryService.command.CreateCashPoolCommand;
import com.winsigns.investment.inventoryService.command.TransferAccountCommand;
import com.winsigns.investment.inventoryService.model.ECACashPool;
import com.winsigns.investment.inventoryService.resource.ECACashPoolResource;
import com.winsigns.investment.inventoryService.resource.ECACashPoolResourceAssembler;
import com.winsigns.investment.inventoryService.service.ECACashPoolService;

@RestController
@RequestMapping(path = "/ecaCashPools", produces = { HAL_JSON_VALUE, APPLICATION_JSON_VALUE,
        APPLICATION_JSON_UTF8_VALUE })
public class ECACashPoolController {

    @Autowired
    ECACashPoolService ecaCashPoolService;

    @GetMapping
    public Resources<ECACashPoolResource> readECACashPools() {
        Link link = linkTo(ECACashPoolController.class).withSelfRel();
        return new Resources<ECACashPoolResource>(
                new ECACashPoolResourceAssembler().toResources(ecaCashPoolService.findAll()), link);
    }

    @GetMapping("/{ecaCashPoolId}")
    public ECACashPoolResource readECACashPool(@PathVariable Long ecaCashPoolId) {
        ECACashPool ecaCashPool = ecaCashPoolService.findOne(ecaCashPoolId);
        ECACashPoolResource ecaCashPoolResource = new ECACashPoolResourceAssembler().toResource(ecaCashPool);

        return ecaCashPoolResource;
    }

    @PostMapping
    public ResponseEntity<?> createECACashPool(@RequestBody CreateCashPoolCommand createCashPoolCommand) {

        ECACashPool ecaCashPool = ecaCashPoolService.addECACashPool(createCashPoolCommand);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(
                linkTo(methodOn(ECACashPoolController.class).readECACashPool(ecaCashPool.getId())).toUri());
        return new ResponseEntity<Object>(ecaCashPool, responseHeaders, HttpStatus.CREATED);
    }

    // 转入资金
    @PostMapping("/{ecaCashPoolId}/transferTo")
    public ECACashPoolResource transferTo(@PathVariable Long ecaCashPoolId,
            @RequestBody TransferAccountCommand capitalChangeCommand) {
        return new ECACashPoolResourceAssembler()
                .toResource(ecaCashPoolService.transferTo(ecaCashPoolId, capitalChangeCommand));
    }

    // 转出资金
    @PostMapping("/{ecaCashPoolId}/transferFrom")
    public ECACashPoolResource transferFrom(@PathVariable Long ecaCashPoolId,
            @RequestBody TransferAccountCommand capitalChangeCommand) {
        return new ECACashPoolResourceAssembler()
                .toResource(ecaCashPoolService.transferFrom(ecaCashPoolId, capitalChangeCommand));
    }

    // 调拨入金
    @PostMapping("/{ecaCashPoolId}/allotIn")
    public Resources<ECACashPoolResource> allotIn(@PathVariable Long ecaCashPoolId,
            @RequestBody AllotAccountCommand allotAccountCommand) {
        Link link = linkTo(ECACashPoolController.class).withSelfRel();
        return new Resources<ECACashPoolResource>(new ECACashPoolResourceAssembler().toResources(
                ecaCashPoolService.allot(ecaCashPoolId, allotAccountCommand.getMatchECACashPoolId(),
                        allotAccountCommand.getChangedCapital())),
                link);
    }

    // 调拨出金
    @PostMapping("/{ecaCashPoolId}/allotOut")
    public Resources<ECACashPoolResource> allotOut(@PathVariable Long ecaCashPoolId,
            @RequestBody AllotAccountCommand allotAccountCommand) {
        Link link = linkTo(ECACashPoolController.class).withSelfRel();
        return new Resources<ECACashPoolResource>(new ECACashPoolResourceAssembler()
                .toResources(ecaCashPoolService.allot(allotAccountCommand.getMatchECACashPoolId(),
                        ecaCashPoolId, allotAccountCommand.getChangedCapital())),
                link);
    }

}
