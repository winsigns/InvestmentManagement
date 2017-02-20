package com.winsigns.investment.inventoryService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winsigns.investment.inventoryService.command.CapitalChangeCommand;
import com.winsigns.investment.inventoryService.model.ExternalCapitalAccountCapital;
import com.winsigns.investment.inventoryService.model.FundAccountCapital;
import com.winsigns.investment.inventoryService.model.FundAccountCapitalDetail;
import com.winsigns.investment.inventoryService.repository.ExternalCapitalAccountCapitalRepository;
import com.winsigns.investment.inventoryService.repository.FundAccountCapitalDetailRepository;
import com.winsigns.investment.inventoryService.repository.FundAccountCapitalRepository;

@Service
public class FundAccountCapitalService {

	@Autowired
	private ExternalCapitalAccountCapitalRepository externalCapitalAccountCapitalRepository;

	@Autowired
	private FundAccountCapitalRepository fundAccountCapitalRepository;

	@Autowired
	private FundAccountCapitalDetailRepository fundAccountCapitalDetailRepository;

	@Transactional
	public FundAccountCapital assignToFundAccountFromCapitalAccount(Long fundAccountId, Long externalCapitalAccountId,
			CapitalChangeCommand capitalChangeCommand) {

		ExternalCapitalAccountCapital externalCapitalAccountCapital = externalCapitalAccountCapitalRepository
				.findByExternalCapitalAccountIdAndCurrencyId(externalCapitalAccountId,
						capitalChangeCommand.getCurrencyId());

		externalCapitalAccountCapital.setUnassignedCapital(externalCapitalAccountCapital.getUnassignedCapital()
				- Math.abs(capitalChangeCommand.getChangedCapital()));

		externalCapitalAccountCapitalRepository.save(externalCapitalAccountCapital);

		FundAccountCapital fundAccountCapital = fundAccountCapitalRepository
				.findByFundAccountIdAndExternalCapitalAccountTypeIdAndCurrencyId(fundAccountId,
						capitalChangeCommand.getExternalCapitalAccountTypeId(), capitalChangeCommand.getCurrencyId());

		if (fundAccountCapital == null) {
			fundAccountCapital = new FundAccountCapital();

			FundAccountCapitalDetail fundAccountCapitalDetail = new FundAccountCapitalDetail();
			fundAccountCapitalDetail.setExternalCapitalAccountId(externalCapitalAccountId);
			fundAccountCapitalDetail.setFundAccountCapital(fundAccountCapital);
			fundAccountCapitalDetail.setCash(Math.abs(capitalChangeCommand.getChangedCapital()));

			fundAccountCapital.setCurrencyId(capitalChangeCommand.getCurrencyId());
			fundAccountCapital.setExternalCapitalAccountTypeId(capitalChangeCommand.getExternalCapitalAccountTypeId());
			fundAccountCapital.setFundAccountId(fundAccountId);
			fundAccountCapital.getFundAccountCapitalDetails().add(fundAccountCapitalDetail);

			return fundAccountCapitalRepository.save(fundAccountCapital);
		} else {
			FundAccountCapitalDetail fundAccountCapitalDetail = fundAccountCapitalDetailRepository
					.findByFundAccountCapitalAndExternalCapitalAccountId(fundAccountCapital, externalCapitalAccountId);

			fundAccountCapitalDetail
					.setCash(fundAccountCapitalDetail.getCash() + Math.abs(capitalChangeCommand.getChangedCapital()));

			fundAccountCapitalDetailRepository.save(fundAccountCapitalDetail);
			return fundAccountCapitalRepository.findOne(fundAccountCapital.getId());
		}
	}

	@Transactional
	public FundAccountCapital assignToCapitalAccountFromFundAccount(Long externalCapitalAccountId, Long fundAccountId,
			CapitalChangeCommand capitalChangeCommand) {

		ExternalCapitalAccountCapital externalCapitalAccountCapital = externalCapitalAccountCapitalRepository
				.findByExternalCapitalAccountIdAndCurrencyId(capitalChangeCommand.getExternalCapitalAccountTypeId(),
						capitalChangeCommand.getCurrencyId());

		externalCapitalAccountCapital.setUnassignedCapital(externalCapitalAccountCapital.getUnassignedCapital()
				+ Math.abs(capitalChangeCommand.getChangedCapital()));

		externalCapitalAccountCapitalRepository.save(externalCapitalAccountCapital);

		FundAccountCapital fundAccountCapital = fundAccountCapitalRepository
				.findByFundAccountIdAndExternalCapitalAccountTypeIdAndCurrencyId(fundAccountId,
						capitalChangeCommand.getExternalCapitalAccountTypeId(), capitalChangeCommand.getCurrencyId());

		FundAccountCapitalDetail fundAccountCapitalDetail = fundAccountCapitalDetailRepository
				.findByFundAccountCapitalAndExternalCapitalAccountId(fundAccountCapital, externalCapitalAccountId);
		fundAccountCapitalDetail
				.setCash(fundAccountCapitalDetail.getCash() - Math.abs(capitalChangeCommand.getChangedCapital()));
		fundAccountCapitalDetailRepository.save(fundAccountCapitalDetail);
		return fundAccountCapitalRepository.findOne(fundAccountCapital.getId());
	}

	@Transactional
	public FundAccountCapital transfer(Long fundAccountId, Long matchfundAccountId,
			CapitalChangeCommand capitalChangeCommand) {

		// FundAccountCapital fundAccountCapital = fundAccountCapitalRepository
		// .findByFundAccountIdAndExternalCapitalAccountTypeIdAndCurrencyId(fundAccountId,
		// capitalChangeCommand.getExternalCapitalAccountTypeId(),
		// capitalChangeCommand.getCurrencyId());
		// FundAccountCapital matchFundAccountCapital =
		// fundAccountCapitalRepository
		// .findByFundAccountIdAndExternalCapitalAccountTypeIdAndCurrencyId(fundAccountId,
		// capitalChangeCommand.getExternalCapitalAccountTypeId(),
		// capitalChangeCommand.getCurrencyId());

		return null;
	}
}
