package com.winsigns.investment.inventoryService.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winsigns.investment.inventoryService.command.AssignAccountCommand;
import com.winsigns.investment.inventoryService.command.CreateFundAccountCapitalDetailCommand;
import com.winsigns.investment.inventoryService.model.ECACashPool;
import com.winsigns.investment.inventoryService.model.FundAccountCapital;
import com.winsigns.investment.inventoryService.model.FundAccountCapitalDetail;
import com.winsigns.investment.inventoryService.repository.ECACashPoolRepository;
import com.winsigns.investment.inventoryService.repository.FundAccountCapitalDetailRepository;
import com.winsigns.investment.inventoryService.repository.FundAccountCapitalRepository;

@Service
public class FundAccountCapitalDetailService {

    @Autowired
    private FundAccountCapitalDetailRepository fundAccountCapitalDetailRepository;

    @Autowired
    private FundAccountCapitalRepository fundAccountCapitalRepository;

    @Autowired
    private ECACashPoolRepository ecaCashPoolRepository;

    public Collection<FundAccountCapitalDetail> findAll() {
        return fundAccountCapitalDetailRepository.findAll();
    }

    public Collection<FundAccountCapitalDetail> findByFACapitalId(Long faCapitalId) {

        FundAccountCapital fundAccountCapital = fundAccountCapitalRepository.findOne(faCapitalId);
        if (fundAccountCapital == null)
            return null;

        return fundAccountCapitalDetailRepository.findByFundAccountCapital(fundAccountCapital);
    }

    public FundAccountCapitalDetail findOne(Long fundAccountCapitalDetailId) {
        return fundAccountCapitalDetailRepository.findOne(fundAccountCapitalDetailId);
    }

    public FundAccountCapitalDetail addFundAccountCapitalDetail(
            CreateFundAccountCapitalDetailCommand crtFundAccountCapitalDetailCmd) {

        FundAccountCapital fundAccountCapital = fundAccountCapitalRepository
                .findOne(crtFundAccountCapitalDetailCmd.getFundAccountCapitalId());
        if (fundAccountCapital == null)
            return null;

        FundAccountCapitalDetail fundAccountCapitalDetail = fundAccountCapitalDetailRepository
                .findByFundAccountCapitalAndExternalCapitalAccountId(fundAccountCapital,
                        crtFundAccountCapitalDetailCmd.getExternalCapitalAccountId());

        if (fundAccountCapitalDetail == null) {
            fundAccountCapitalDetail = new FundAccountCapitalDetail();

            fundAccountCapitalDetail.setFundAccountCapital(fundAccountCapital);
            fundAccountCapitalDetail
                    .setExternalCapitalAccountId(crtFundAccountCapitalDetailCmd.getExternalCapitalAccountId());
            fundAccountCapitalDetail.setCash(0.0);

            fundAccountCapitalDetail = fundAccountCapitalDetailRepository.save(fundAccountCapitalDetail);
        }
        return fundAccountCapitalDetail;
    }

    public FundAccountCapitalDetail assignFrom(Long faCapitalDetailId, AssignAccountCommand assignAccountCommand) {
        FundAccountCapitalDetail fundAccountCapitalDetail = fundAccountCapitalDetailRepository
                .findOne(faCapitalDetailId);
        if (fundAccountCapitalDetail == null)
            return null;

        fundAccountCapitalDetail.setCash(fundAccountCapitalDetail.getCash() + assignAccountCommand.getAssignedCash());
        fundAccountCapitalDetail = fundAccountCapitalDetailRepository.save(fundAccountCapitalDetail);

        ECACashPool ecaCashPool = ecaCashPoolRepository.findOne(assignAccountCommand.getEcaCashPoolId());
        if (ecaCashPool == null)
            return null;

        ecaCashPool.setUnassignedCapital(ecaCashPool.getUnassignedCapital() - assignAccountCommand.getAssignedCash());
        ecaCashPoolRepository.save(ecaCashPool);

        return fundAccountCapitalDetail;
    }

    public FundAccountCapitalDetail assignTo(Long faCapitalDetailId, AssignAccountCommand assignAccountCommand) {
        FundAccountCapitalDetail fundAccountCapitalDetail = fundAccountCapitalDetailRepository
                .findOne(faCapitalDetailId);
        if (fundAccountCapitalDetail == null)
            return null;

        fundAccountCapitalDetail.setCash(fundAccountCapitalDetail.getCash() - assignAccountCommand.getAssignedCash());
        fundAccountCapitalDetail = fundAccountCapitalDetailRepository.save(fundAccountCapitalDetail);

        ECACashPool ecaCashPool = ecaCashPoolRepository.findOne(assignAccountCommand.getEcaCashPoolId());
        if (ecaCashPool == null)
            return null;

        ecaCashPool.setUnassignedCapital(ecaCashPool.getUnassignedCapital() + assignAccountCommand.getAssignedCash());
        ecaCashPoolRepository.save(ecaCashPool);

        return fundAccountCapitalDetail;
    }

    @Transactional
    public Collection<FundAccountCapitalDetail> enfeoff(Long dstFACapitalDetailId, Long srcFACapitalDetailId,
            Double assignedCash) {

        List<FundAccountCapitalDetail> result = new ArrayList<FundAccountCapitalDetail>();

        FundAccountCapitalDetail dstFundAccountCapitalDetail = fundAccountCapitalDetailRepository
                .findOne(dstFACapitalDetailId);
        if (dstFundAccountCapitalDetail == null)
            return null;

        FundAccountCapitalDetail srcFundAccountCapitalDetail = fundAccountCapitalDetailRepository
                .findOne(srcFACapitalDetailId);
        if (srcFundAccountCapitalDetail == null)
            return null;

        if (dstFundAccountCapitalDetail.getFundAccountCapital().getCurrency() != srcFundAccountCapitalDetail
                .getFundAccountCapital().getCurrency())
            return null;

        dstFundAccountCapitalDetail.setCash(dstFundAccountCapitalDetail.getCash() + assignedCash);
        dstFundAccountCapitalDetail = fundAccountCapitalDetailRepository.save(dstFundAccountCapitalDetail);
        result.add(dstFundAccountCapitalDetail);

        srcFundAccountCapitalDetail.setCash(srcFundAccountCapitalDetail.getCash() - assignedCash);
        srcFundAccountCapitalDetail = fundAccountCapitalDetailRepository.save(srcFundAccountCapitalDetail);
        result.add(srcFundAccountCapitalDetail);

        return result;
    }

}
