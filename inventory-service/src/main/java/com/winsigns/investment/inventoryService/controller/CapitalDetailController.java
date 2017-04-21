package com.winsigns.investment.inventoryService.controller;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.winsigns.investment.inventoryService.capital.common.CapitalServiceManager;
import com.winsigns.investment.inventoryService.command.CreateCapitalDetailCommand;
import com.winsigns.investment.inventoryService.model.CapitalDetail;
import com.winsigns.investment.inventoryService.model.FundAccountCapitalPool;
import com.winsigns.investment.inventoryService.resource.CapitalDetailResource;
import com.winsigns.investment.inventoryService.resource.CapitalDetailResourceAssembler;
import com.winsigns.investment.inventoryService.service.CapitalDetailService;

@RestController
@RequestMapping(path = "/capital-details",
    produces = {HAL_JSON_VALUE, APPLICATION_JSON_VALUE, APPLICATION_JSON_UTF8_VALUE})
public class CapitalDetailController {

  @Autowired
  CapitalServiceManager capitalServiceManager;

  @Autowired
  CapitalDetailService capitalDetailService;

  /**
   * 创建资金明细
   * 
   * @param command
   * @return
   */
  @PostMapping
  public ResponseEntity<?> createCapitalDetail(@RequestBody CreateCapitalDetailCommand command) {
    if (command.getFaCapitalPoolId() == null) { // 如果池子为空，则先创建池子
      FundAccountCapitalPool fundAccountCapitalPool =
          capitalServiceManager.createFundAccountCapitalPool(command);
      command.setFaCapitalPoolId(fundAccountCapitalPool.getId());
    }

    CapitalDetail capitalDetail = capitalDetailService.addFundAccountCapitalDetail(command);
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLocation(
        linkTo(methodOn(CapitalDetailController.class).readCapitalDetail(capitalDetail.getId()))
            .toUri());
    return new ResponseEntity<Object>(capitalDetail, responseHeaders, HttpStatus.CREATED);

  }

  /**
   * 查询一条指定的产品账户资金明细
   * 
   * @param faCapitalDetailId
   * @return
   */
  @GetMapping("/{capitalDetailId}")
  public CapitalDetailResource readCapitalDetail(@PathVariable Long capitalDetailId) {
    CapitalDetail capitalDetail = capitalDetailService.readCapitalDetail(capitalDetailId);
    CapitalDetailResource capitalDetailResource =
        new CapitalDetailResourceAssembler().toResource(capitalDetail);

    return capitalDetailResource;
  }

}
