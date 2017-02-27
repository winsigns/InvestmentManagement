package com.winsigns.investment.tradeService.resource;

import java.util.Currency;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.hateoas.Resource;

import com.winsigns.investment.tradeService.model.Instruction;
import com.winsigns.investment.tradeService.model.InstructionType;

public class InstructionResource extends Resource<Instruction> {

	private final Long portfolioId;

	private final Long securityId;

	private final String investSvc;

	private final String investDirection;

	private final Currency currency;

	private final Double costPrice;

	@Enumerated(EnumType.STRING)
	private final InstructionType volumeType;

	private final Double quantity;

	private final Double amount;

	public InstructionResource(Instruction instruction) {
		super(instruction);
		this.portfolioId = instruction.getPortfolioId();
		this.securityId = instruction.getSecurityId();
		this.investSvc = instruction.getInvestSvc();
		this.investDirection = instruction.getInvestDirection();
		this.currency = instruction.getCurrency();
		this.costPrice = instruction.getCostPrice();
		this.volumeType = instruction.getVolumeType();
		this.quantity = instruction.getQuantity();
		this.amount = instruction.getAmount();
	}

	public Long getPortfolioId() {
		return portfolioId;
	}

	public Long getSecurityId() {
		return securityId;
	}

	public String getInvestSvc() {
		return investSvc;
	}

	public String getInvestDirection() {
		return investDirection;
	}

	public Currency getCurrency() {
		return currency;
	}

	public Double getCostPrice() {
		return costPrice;
	}

	public InstructionType getVolumeType() {
		return volumeType;
	}

	public Double getQuantity() {
		return quantity;
	}

	public Double getAmount() {
		return amount;
	}

}
