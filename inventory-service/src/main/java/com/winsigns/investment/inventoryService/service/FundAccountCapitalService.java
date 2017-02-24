package com.winsigns.investment.inventoryService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winsigns.investment.inventoryService.command.CashChangeCommand;
import com.winsigns.investment.inventoryService.model.ECACashPool;
import com.winsigns.investment.inventoryService.model.FundAccountCapital;
import com.winsigns.investment.inventoryService.model.FundAccountCapitalDetail;
import com.winsigns.investment.inventoryService.repository.ECACashPoolRepository;
import com.winsigns.investment.inventoryService.repository.FundAccountCapitalDetailRepository;
import com.winsigns.investment.inventoryService.repository.FundAccountCapitalRepository;

@Service
public class FundAccountCapitalService {

    @Autowired
    private ECACashPoolRepository externalCapitalAccountCapitalRepository;

    @Autowired
    private FundAccountCapitalRepository fundAccountCapitalRepository;

    @Autowired
    private FundAccountCapitalDetailRepository fundAccountCapitalDetailRepository;

    @Transactional
    public FundAccountCapital assignToFundAccountFromCapitalAccount(Long fundAccountId, Long externalCapitalAccountId,
            CashChangeCommand capitalChangeCommand) {

        ECACashPool externalCapitalAccountCapital = externalCapitalAccountCapitalRepository
                .findByFundIdAndExternalCapitalAccountIdAndCurrency(fundAccountId, externalCapitalAccountId,
                        capitalChangeCommand.getCurrency());

        externalCapitalAccountCapital.setUnassignedCapital(externalCapitalAccountCapital.getUnassignedCapital()
                - Math.abs(capitalChangeCommand.getChangedCapital()));

        externalCapitalAccountCapitalRepository.save(externalCapitalAccountCapital);

        FundAccountCapital fundAccountCapital = fundAccountCapitalRepository
                .findByFundAccountIdAndExternalCapitalAccountTypeIdAndCurrency(fundAccountId,
                        capitalChangeCommand.getExternalCapitalAccountTypeId(), capitalChangeCommand.getCurrency());

        if (fundAccountCapital == null) {
            fundAccountCapital = new FundAccountCapital();

            FundAccountCapitalDetail fundAccountCapitalDetail = new FundAccountCapitalDetail();
            fundAccountCapitalDetail.setExternalCapitalAccountId(externalCapitalAccountId);
            fundAccountCapitalDetail.setFundAccountCapital(fundAccountCapital);
            fundAccountCapitalDetail.setCash(Math.abs(capitalChangeCommand.getChangedCapital()));

            fundAccountCapital.setCurrency(capitalChangeCommand.getCurrency());
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
            CashChangeCommand capitalChangeCommand) {

        ECACashPool externalCapitalAccountCapital = externalCapitalAccountCapitalRepository
                .findByFundIdAndExternalCapitalAccountIdAndCurrency(fundAccountId, externalCapitalAccountId,
                        capitalChangeCommand.getCurrency());

        externalCapitalAccountCapital.setUnassignedCapital(externalCapitalAccountCapital.getUnassignedCapital()
                + Math.abs(capitalChangeCommand.getChangedCapital()));

        externalCapitalAccountCapitalRepository.save(externalCapitalAccountCapital);

        FundAccountCapital fundAccountCapital = fundAccountCapitalRepository
                .findByFundAccountIdAndExternalCapitalAccountTypeIdAndCurrency(fundAccountId,
                        capitalChangeCommand.getExternalCapitalAccountTypeId(), capitalChangeCommand.getCurrency());

        FundAccountCapitalDetail fundAccountCapitalDetail = fundAccountCapitalDetailRepository
                .findByFundAccountCapitalAndExternalCapitalAccountId(fundAccountCapital, externalCapitalAccountId);
        fundAccountCapitalDetail
                .setCash(fundAccountCapitalDetail.getCash() - Math.abs(capitalChangeCommand.getChangedCapital()));
        fundAccountCapitalDetailRepository.save(fundAccountCapitalDetail);
        return fundAccountCapitalRepository.findOne(fundAccountCapital.getId());
    }

    @Transactional
    public FundAccountCapital transfer(Long fundAccountId, Long matchfundAccountId,
            CashChangeCommand capitalChangeCommand) {

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
