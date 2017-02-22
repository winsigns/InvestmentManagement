package com.winsigns.investment.fundService.hal;

import com.winsigns.investment.fundService.controller.FundController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.UriTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by colin on 2017/2/22.
 */

@RestController
public class RootController {
    @GetMapping(path = "/", produces = {HAL_JSON_VALUE, APPLICATION_JSON_VALUE, APPLICATION_JSON_UTF8_VALUE})
    public HttpEntity<HALResponse> root() {
        HALResponse halResponse = new HALResponse();
        halResponse.add(linkTo(methodOn((FundController.class)).readFunds()).withRel("funds"));
        return new ResponseEntity<>(halResponse, HttpStatus.OK);
    }
}
