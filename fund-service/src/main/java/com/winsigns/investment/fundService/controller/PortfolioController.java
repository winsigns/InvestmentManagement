package com.winsigns.investment.fundService.controller;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.winsigns.investment.framework.hal.Resources;
import com.winsigns.investment.fundService.command.UpdatePortfolioCommand;
import com.winsigns.investment.fundService.resource.PortfolioResource;
import com.winsigns.investment.fundService.resource.PortfolioResourceAssembler;
import com.winsigns.investment.fundService.service.PortfolioService;

@RestController
@RequestMapping(path = "/portfolios",
    produces = {HAL_JSON_VALUE, APPLICATION_JSON_VALUE, APPLICATION_JSON_UTF8_VALUE})
public class PortfolioController {

  @Autowired
  PortfolioService portfolioService;

  // 获取投资组合
  @GetMapping
  public Resources<PortfolioResource> readPortfolios() {
    Link link = linkTo(PortfolioController.class).withSelfRel();
    return new Resources<PortfolioResource>(
        new PortfolioResourceAssembler().toResources(portfolioService.findAll()), link);
  }

  // 获取指定投资组合
  @GetMapping("/{portfolioId}")
  public PortfolioResource readPortfolio(@PathVariable Long portfolioId) {
    return new PortfolioResourceAssembler().toResource(portfolioService.findOne(portfolioId));
  }

  // 修改指定投资组合
  @PutMapping("/{portfolioId}")
  public PortfolioResource updatePortfolio(@PathVariable("portfolioId") Long portfolioId,
      @RequestBody UpdatePortfolioCommand updatePortfolioCommand) {
    return new PortfolioResourceAssembler()
        .toResource(portfolioService.updatePortfolio(portfolioId, updatePortfolioCommand));
  }

  // 删除指定投资组合
  @DeleteMapping("/{portfolioId}")
  public ResponseEntity<?> deletePortfolio(@PathVariable("portfolioId") Long portfolioId) {
    portfolioService.deletePortfolio(portfolioId);
    return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
  }

}
