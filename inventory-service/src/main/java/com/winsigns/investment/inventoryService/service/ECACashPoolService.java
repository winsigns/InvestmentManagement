package com.winsigns.investment.inventoryService.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winsigns.investment.inventoryService.command.CashChangeCommand;
import com.winsigns.investment.inventoryService.model.ECACashPool;
import com.winsigns.investment.inventoryService.repository.ECACashPoolRepository;

@Service
public class ECACashPoolService {

    @Autowired
    private ECACashPoolRepository eCACashPoolRepository;

    public ECACashPool transferToExternalCapitalAccount(Long fundId, Long externalCapitalAccountId,
            CashChangeCommand capitalChangeCommand) {

        ECACashPool externalCapitalAccountCapital = eCACashPoolRepository
                .findByFundIdAndExternalCapitalAccountIdAndCurrency(fundId, externalCapitalAccountId,
                        capitalChangeCommand.getCurrency());

        if (externalCapitalAccountCapital == null) {
            externalCapitalAccountCapital = new ECACashPool();

            externalCapitalAccountCapital.setExternalCapitalAccountId(externalCapitalAccountId);
            externalCapitalAccountCapital.setCurrency(capitalChangeCommand.getCurrency());
            externalCapitalAccountCapital.setUnassignedCapital(capitalChangeCommand.getChangedCapital());
            externalCapitalAccountCapital.setFundId(fundId);

        } else {
            externalCapitalAccountCapital.setUnassignedCapital(
                    externalCapitalAccountCapital.getUnassignedCapital() + capitalChangeCommand.getChangedCapital());
        }
        return eCACashPoolRepository.save(externalCapitalAccountCapital);

    }

    public ECACashPool transferFromExternalCapitalAccount(Long fundId, Long externalCapitalAccountId,
            CashChangeCommand capitalChangeCommand) {

        ECACashPool externalCapitalAccountCapital = eCACashPoolRepository
                .findByFundIdAndExternalCapitalAccountIdAndCurrency(fundId, externalCapitalAccountId,
                        capitalChangeCommand.getCurrency());

        if (externalCapitalAccountCapital != null) {
            externalCapitalAccountCapital.setUnassignedCapital(
                    externalCapitalAccountCapital.getUnassignedCapital() - capitalChangeCommand.getChangedCapital());
        }
        return eCACashPoolRepository.save(externalCapitalAccountCapital);

    }

    @Transactional
    public Collection<ECACashPool> allot(Long fundId, Long dstExternalCapitalAccountId,
            Long srcEexternalCapitalAccountId, CashChangeCommand capitalChangeCommand) {

        List<ECACashPool> result = new ArrayList<ECACashPool>();

        ECACashPool dstExternalCapitalAccountCapital = eCACashPoolRepository
                .findByFundIdAndExternalCapitalAccountIdAndCurrency(fundId, dstExternalCapitalAccountId,
                        capitalChangeCommand.getCurrency());

        if (dstExternalCapitalAccountCapital == null) {
            dstExternalCapitalAccountCapital = new ECACashPool();

            dstExternalCapitalAccountCapital.setExternalCapitalAccountId(dstExternalCapitalAccountId);
            dstExternalCapitalAccountCapital.setCurrency(capitalChangeCommand.getCurrency());
            dstExternalCapitalAccountCapital.setUnassignedCapital(Math.abs(capitalChangeCommand.getChangedCapital()));
            dstExternalCapitalAccountCapital.setFundId(fundId);

        } else {
            dstExternalCapitalAccountCapital
                    .setUnassignedCapital(dstExternalCapitalAccountCapital.getUnassignedCapital()
                            + Math.abs(capitalChangeCommand.getChangedCapital()));
        }
        result.add(eCACashPoolRepository.save(dstExternalCapitalAccountCapital));

        ECACashPool srcExternalCapitalAccountCapital = eCACashPoolRepository
                .findByFundIdAndExternalCapitalAccountIdAndCurrency(fundId, srcEexternalCapitalAccountId,
                        capitalChangeCommand.getCurrency());
        if (srcExternalCapitalAccountCapital != null) {
            srcExternalCapitalAccountCapital
                    .setUnassignedCapital(srcExternalCapitalAccountCapital.getUnassignedCapital()
                            - Math.abs(capitalChangeCommand.getChangedCapital()));
        }

        result.add(eCACashPoolRepository.save(srcExternalCapitalAccountCapital));

        return result;
    }

    public Collection<ECACashPool> readExternalCapitalAccountCapitals(Long fundId, Long externalCapitalAccountId) {

        return eCACashPoolRepository.findByFundIdAndExternalCapitalAccountId(fundId, externalCapitalAccountId);
    }

    public ECACashPool addECACashPool(Long fundId, Long externalCapitalAccountId, CashChangeCommand cashChangeCommand) {
        ECACashPool eCACashPool = eCACashPoolRepository.findByFundIdAndExternalCapitalAccountIdAndCurrency(fundId,
                externalCapitalAccountId, cashChangeCommand.getCurrency());

        if (eCACashPool == null) {
            eCACashPool = new ECACashPool();

            eCACashPool.setExternalCapitalAccountId(externalCapitalAccountId);
            eCACashPool.setCurrency(cashChangeCommand.getCurrency());
            eCACashPool.setFundId(fundId);
            eCACashPool.setUnassignedCapital(0.0);
            eCACashPool = eCACashPoolRepository.save(eCACashPool);
        }
        return eCACashPool;
    }

    public ECACashPool findOne(Long eCACashPoolId) {

        return eCACashPoolRepository.findOne(eCACashPoolId);
    }
}
