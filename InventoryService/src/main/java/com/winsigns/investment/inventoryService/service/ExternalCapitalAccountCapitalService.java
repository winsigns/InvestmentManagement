package com.winsigns.investment.inventoryService.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winsigns.investment.inventoryService.command.CapitalChangeCommand;
import com.winsigns.investment.inventoryService.model.ExternalCapitalAccountCapital;
import com.winsigns.investment.inventoryService.repository.ExternalCapitalAccountCapitalRepository;

@Service
public class ExternalCapitalAccountCapitalService {

	@Autowired
	private ExternalCapitalAccountCapitalRepository externalCapitalAccountCapitalRepository;

	public ExternalCapitalAccountCapital transferToExternalCapitalAccount(Long externalCapitalAccountId,
			CapitalChangeCommand capitalChangeCommand) {

		ExternalCapitalAccountCapital externalCapitalAccountCapital = externalCapitalAccountCapitalRepository
				.findByExternalCapitalAccountIdAndCurrencyId(externalCapitalAccountId,
						capitalChangeCommand.getCurrencyId());

		if (externalCapitalAccountCapital == null) {
			externalCapitalAccountCapital = new ExternalCapitalAccountCapital();

			externalCapitalAccountCapital.setExternalCapitalAccountId(externalCapitalAccountId);
			externalCapitalAccountCapital.setCurrencyId(capitalChangeCommand.getCurrencyId());
			externalCapitalAccountCapital.setUnassignedCapital(capitalChangeCommand.getChangedCapital());

		} else {
			externalCapitalAccountCapital
					.setUnassignedCapital(externalCapitalAccountCapital.getUnassignedCapital() + capitalChangeCommand.getChangedCapital());
		}
		return externalCapitalAccountCapitalRepository.save(externalCapitalAccountCapital);

	}

	public ExternalCapitalAccountCapital transferFromExternalCapitalAccount(Long externalCapitalAccountId,
			CapitalChangeCommand capitalChangeCommand) {

		ExternalCapitalAccountCapital externalCapitalAccountCapital = externalCapitalAccountCapitalRepository
				.findByExternalCapitalAccountIdAndCurrencyId(externalCapitalAccountId,
						capitalChangeCommand.getCurrencyId());

		if (externalCapitalAccountCapital != null) {
			externalCapitalAccountCapital
					.setUnassignedCapital(externalCapitalAccountCapital.getUnassignedCapital() - capitalChangeCommand.getChangedCapital());
		}
		return externalCapitalAccountCapitalRepository.save(externalCapitalAccountCapital);

	}

	@Transactional
	public Collection<ExternalCapitalAccountCapital> allot(Long dstExternalCapitalAccountId,
			Long srcEexternalCapitalAccountId, CapitalChangeCommand capitalChangeCommand) {

		List<ExternalCapitalAccountCapital> result = new ArrayList<ExternalCapitalAccountCapital>();

		ExternalCapitalAccountCapital dstExternalCapitalAccountCapital = externalCapitalAccountCapitalRepository
				.findByExternalCapitalAccountIdAndCurrencyId(dstExternalCapitalAccountId,
						capitalChangeCommand.getCurrencyId());

		if (dstExternalCapitalAccountCapital == null) {
			dstExternalCapitalAccountCapital = new ExternalCapitalAccountCapital();

			dstExternalCapitalAccountCapital.setExternalCapitalAccountId(dstExternalCapitalAccountId);
			dstExternalCapitalAccountCapital.setCurrencyId(capitalChangeCommand.getCurrencyId());
			dstExternalCapitalAccountCapital.setUnassignedCapital(Math.abs(capitalChangeCommand.getChangedCapital()));

		} else {
			dstExternalCapitalAccountCapital.setUnassignedCapital(
					dstExternalCapitalAccountCapital.getUnassignedCapital() + Math.abs(capitalChangeCommand.getChangedCapital()));
		}
		result.add(externalCapitalAccountCapitalRepository.save(dstExternalCapitalAccountCapital));

		ExternalCapitalAccountCapital srcExternalCapitalAccountCapital = externalCapitalAccountCapitalRepository
				.findByExternalCapitalAccountIdAndCurrencyId(srcEexternalCapitalAccountId,
						capitalChangeCommand.getCurrencyId());
		if (srcExternalCapitalAccountCapital != null) {
			srcExternalCapitalAccountCapital.setUnassignedCapital(
					srcExternalCapitalAccountCapital.getUnassignedCapital() - Math.abs(capitalChangeCommand.getChangedCapital()));
		}

		result.add(externalCapitalAccountCapitalRepository.save(srcExternalCapitalAccountCapital));

		return result;
	}

	public Collection<ExternalCapitalAccountCapital> readExternalCapitalAccountCapitals(Long externalCapitalAccountId) {

		return externalCapitalAccountCapitalRepository.findByExternalCapitalAccountId(externalCapitalAccountId);
	}
}
