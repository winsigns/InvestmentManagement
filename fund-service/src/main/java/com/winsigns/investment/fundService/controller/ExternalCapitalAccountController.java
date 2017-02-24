package com.winsigns.investment.fundService.controller;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.client.Traverson;
import org.springframework.hateoas.client.Traverson.TraversalBuilder;
import org.springframework.hateoas.core.Relation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jayway.jsonpath.PathNotFoundException;
import com.winsigns.investment.fundService.command.ExternalCapitalAccountCommand;
import com.winsigns.investment.fundService.model.ExternalCapitalAccount;
import com.winsigns.investment.fundService.model.ExternalTradeAccount;
import com.winsigns.investment.fundService.resource.ExternalCapitalAccountResource;
import com.winsigns.investment.fundService.resource.ExternalCapitalAccountResourceAssembler;
import com.winsigns.investment.fundService.resource.ExternalTradeAccountResourceAssembler;
import com.winsigns.investment.fundService.service.ExternalCapitalAccountService;

import net.minidev.json.JSONArray;

@RestController
@RequestMapping(path = "/funds/{fundId}/externalCapitalAccounts", produces = { HAL_JSON_VALUE, APPLICATION_JSON_VALUE,
        APPLICATION_JSON_UTF8_VALUE })
public class ExternalCapitalAccountController {

    @Autowired
    ExternalCapitalAccountService externalCapitalAccountService;

    @RequestMapping(method = RequestMethod.GET)
    public Resources<ExternalCapitalAccountResource> readExternalCapitalAccounts(@PathVariable Long fundId) {
        Link link = linkTo(ExternalCapitalAccountController.class, fundId).withSelfRel();
        return new Resources<ExternalCapitalAccountResource>(new ExternalCapitalAccountResourceAssembler()
                .toResources(externalCapitalAccountService.findByFundId(fundId)), link);
    }

    @RequestMapping(value = "/{externalCapitalAccountId}", method = RequestMethod.GET)
    public ExternalCapitalAccountResource readExternalCapitalAccount(@PathVariable Long fundId,
            @PathVariable Long externalCapitalAccountId) {
        ExternalCapitalAccount externalCapitalAccount = externalCapitalAccountService.findOne(externalCapitalAccountId);
        ExternalCapitalAccountResource externalCapitalAccountResource = new ExternalCapitalAccountResourceAssembler()
                .toResource(externalCapitalAccountService.findOne(externalCapitalAccountId));

        externalCapitalAccountResource.add(
                ExternalTradeAccount.class.getAnnotation(Relation.class).collectionRelation(),
                new ExternalTradeAccountResourceAssembler()
                        .toResources(externalCapitalAccount.getExternalTradeAccounts()));

        String url = linkTo(ExternalCapitalAccountController.class, fundId).slash(externalCapitalAccountId).toUri()
                .getPath() + "/eCACashPools";
        try {
            Traverson traverson = new Traverson(new URI("http://localhost:10011" + url), MediaTypes.HAL_JSON);

            TraversalBuilder newBuilder = traverson.follow();

            JSONArray eCACashPools = newBuilder.toObject("$._embedded.eCACashPools");
            externalCapitalAccountResource.add("eCACashPools", eCACashPools);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (PathNotFoundException e) {
            // 理论上不会存在
        }
        return externalCapitalAccountResource;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createExternalCapitalAccount(@PathVariable Long fundId,
            @RequestBody ExternalCapitalAccountCommand externalCapitalAccountCommand) {

        ExternalCapitalAccount externalCapitalAccount = externalCapitalAccountService.addExternalCapitalAccount(fundId,
                externalCapitalAccountCommand);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(linkTo(methodOn(ExternalCapitalAccountController.class)
                .readExternalCapitalAccount(fundId, externalCapitalAccount.getId())).toUri());
        return new ResponseEntity<Object>(externalCapitalAccount, responseHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{externalCapitalAccountId}", method = RequestMethod.PUT)
    public ExternalCapitalAccountResource updateExternalCapitalAccount(@PathVariable Long externalCapitalAccountId,
            @RequestBody ExternalCapitalAccountCommand externalCapitalAccountCommand) {
        return new ExternalCapitalAccountResourceAssembler().toResource(externalCapitalAccountService
                .updateExternalCapitalAccount(externalCapitalAccountId, externalCapitalAccountCommand));
    }

    @RequestMapping(value = "/{externalCapitalAccountId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteExternalCapitalAccount(@PathVariable Long externalCapitalAccountId) {
        externalCapitalAccountService.deleteExternalCapitalAccount(externalCapitalAccountId);
        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }
}
