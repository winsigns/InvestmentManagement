package com.winsigns.investment.fundService.controller;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.Relation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.winsigns.investment.fundService.command.CreateExternalCapitalAccountCommand;
import com.winsigns.investment.fundService.command.UpdateExternalCapitalAccountCommand;
import com.winsigns.investment.fundService.model.ExternalCapitalAccount;
import com.winsigns.investment.fundService.model.ExternalTradeAccount;
import com.winsigns.investment.fundService.resource.ExternalCapitalAccountResource;
import com.winsigns.investment.fundService.resource.ExternalCapitalAccountResourceAssembler;
import com.winsigns.investment.fundService.resource.ExternalTradeAccountResourceAssembler;
import com.winsigns.investment.fundService.service.ExternalCapitalAccountService;

@RestController
@RequestMapping(path = "/external-capital-accounts", produces = { HAL_JSON_VALUE, APPLICATION_JSON_VALUE,
        APPLICATION_JSON_UTF8_VALUE })
public class ExternalCapitalAccountController {

    @Autowired
    ExternalCapitalAccountService externalCapitalAccountService;

    @GetMapping
    public Resources<ExternalCapitalAccountResource> readExternalCapitalAccounts() {
        Link link = linkTo(ExternalCapitalAccountController.class).withSelfRel();
        return new Resources<ExternalCapitalAccountResource>(
                new ExternalCapitalAccountResourceAssembler().toResources(externalCapitalAccountService.findAll()),
                link);
    }

    @GetMapping("/{externalCapitalAccountId}")
    public ExternalCapitalAccountResource readExternalCapitalAccount(@PathVariable Long externalCapitalAccountId) {
        ExternalCapitalAccount externalCapitalAccount = externalCapitalAccountService.findOne(externalCapitalAccountId);
        ExternalCapitalAccountResource externalCapitalAccountResource = new ExternalCapitalAccountResourceAssembler()
                .toResource(externalCapitalAccountService.findOne(externalCapitalAccountId));

        List<ExternalTradeAccount> externalTradeAccounts = externalCapitalAccount.getExternalTradeAccounts();
        if (externalTradeAccounts.size() != 0)
            externalCapitalAccountResource.add(
                    ExternalTradeAccount.class.getAnnotation(Relation.class).collectionRelation(),
                    new ExternalTradeAccountResourceAssembler().toResources(externalTradeAccounts));

        return externalCapitalAccountResource;
    }

    @PostMapping
    public ResponseEntity<?> crreateExternalCapitalAccount(
            @RequestBody CreateExternalCapitalAccountCommand createExternalCapitalAccountCommand) {

        ExternalCapitalAccount externalCapitalAccount = externalCapitalAccountService
                .addExternalCapitalAccount(createExternalCapitalAccountCommand);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(linkTo(methodOn(ExternalCapitalAccountController.class)
                .readExternalCapitalAccount(externalCapitalAccount.getId())).toUri());
        return new ResponseEntity<Object>(externalCapitalAccount, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{externalCapitalAccountId}")
    public ExternalCapitalAccountResource updateExternalCapitalAccount(@PathVariable Long externalCapitalAccountId,
            @RequestBody UpdateExternalCapitalAccountCommand externalCapitalAccountCommand) {
        return new ExternalCapitalAccountResourceAssembler().toResource(externalCapitalAccountService
                .updateExternalCapitalAccount(externalCapitalAccountId, externalCapitalAccountCommand));
    }

    @DeleteMapping("/{externalCapitalAccountId}")
    public ResponseEntity<?> deleteExternalCapitalAccount(@PathVariable Long externalCapitalAccountId) {
        externalCapitalAccountService.deleteExternalCapitalAccount(externalCapitalAccountId);
        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }
}
