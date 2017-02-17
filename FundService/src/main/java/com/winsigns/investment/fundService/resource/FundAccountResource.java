package com.winsigns.investment.fundService.resource;

import org.springframework.hateoas.Resource;

import com.winsigns.investment.fundService.controller.FundController;
import com.winsigns.investment.fundService.model.FundAccount;

import java.time.LocalDateTime;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class FundAccountResource extends Resource<FundAccount> {
	
	//名称
	private final String name;
    
    //投资经理
	private final String investmentManager;
    

    public FundAccountResource(FundAccount fundAccount) {
        super(fundAccount);
        this.name = fundAccount.getName();
        this.investmentManager = fundAccount.getInvestmentManager();

        Long fundAccountId = fundAccount.getId();
        Long fundId = fundAccount.getFund().getId();
//        if (item.isCompleted()) {
//            add(linkTo(methodOn(ItemRestController.class).markAsUncompleted(listId, itemId)).withRel("mark-as-uncompleted"));
//        }
//        else {
//            add(linkTo(methodOn(ItemRestController.class).markAsCompleted(listId, itemId)).withRel("mark-as-completed"));
//        }
        add(linkTo(methodOn(FundController.class).readFund(fundId)).withRel("collection"));
    }

    public String getName() {
        return name;
    }
    
    public String getInvestmentManager() {
        return investmentManager;
    }
}
