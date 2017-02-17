package com.winsigns.investment.fundService.resource;

import org.springframework.hateoas.Resource;

import com.winsigns.investment.fundService.controller.PortfolioController;
import com.winsigns.investment.fundService.model.Portfolio;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class PortfolioResource extends Resource<Portfolio> {
	
	//名称
	private final String name;

    public PortfolioResource(Portfolio portfolio) {
        super(portfolio);
        this.name = portfolio.getName();

        //Long fundAccountId = fundAccount.getId();
        Long fundAccountId = portfolio.getFundAccount().getId();
//        if (item.isCompleted()) {
//            add(linkTo(methodOn(ItemRestController.class).markAsUncompleted(listId, itemId)).withRel("mark-as-uncompleted"));
//        }
//        else {
//            add(linkTo(methodOn(ItemRestController.class).markAsCompleted(listId, itemId)).withRel("mark-as-completed"));
//        }
        add(linkTo(methodOn(PortfolioController.class).readFundAccount(fundAccountId)).withRel("fundAccount"));
    }

    public String getName() {
        return name;
    }
    
}
