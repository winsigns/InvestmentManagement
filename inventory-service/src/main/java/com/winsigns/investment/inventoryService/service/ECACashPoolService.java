package com.winsigns.investment.inventoryService.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winsigns.investment.inventoryService.command.CreateCashPoolCommand;
import com.winsigns.investment.inventoryService.command.TransferAccountCommand;
import com.winsigns.investment.inventoryService.model.ECACashPool;
import com.winsigns.investment.inventoryService.repository.ECACashPoolRepository;

@Service
public class ECACashPoolService {

  @Autowired
  private ECACashPoolRepository ecaCashPoolRepository;

  public Collection<ECACashPool> findByExternalCapitalAccountId(Long externalCapitalAccountId) {

    return ecaCashPoolRepository.findByExternalCapitalAccountId(externalCapitalAccountId);
  }

  public Collection<ECACashPool> findAll() {
    return ecaCashPoolRepository.findAll();
  }

  public ECACashPool findOne(Long eCACashPoolId) {

    return ecaCashPoolRepository.findOne(eCACashPoolId);
  }

  public ECACashPool addECACashPool(CreateCashPoolCommand createCashPoolCommand) {
    ECACashPool ecaCashPool = ecaCashPoolRepository.findByExternalCapitalAccountIdAndCurrency(
        createCashPoolCommand.getExternalCapitalAccountId(), createCashPoolCommand.getCurrency());

    if (ecaCashPool == null) {
      ecaCashPool = new ECACashPool();

      ecaCashPool.setExternalCapitalAccountId(createCashPoolCommand.getExternalCapitalAccountId());
      ecaCashPool.setCurrency(createCashPoolCommand.getCurrency());
      ecaCashPool.setUnassignedCapital(0.0);
      ecaCashPool = ecaCashPoolRepository.save(ecaCashPool);
    }
    return ecaCashPool;
  }

  public ECACashPool transferTo(Long ecaCashPoolId, TransferAccountCommand transferAccountCommand) {

    ECACashPool ecaCashPool = ecaCashPoolRepository.findOne(ecaCashPoolId);

    if (ecaCashPool == null)
      return null;

    ecaCashPool.setUnassignedCapital(
        ecaCashPool.getUnassignedCapital() + transferAccountCommand.getChangedCapital());
    return ecaCashPoolRepository.save(ecaCashPool);
  }

  public ECACashPool transferFrom(Long ecaCashPoolId,
      TransferAccountCommand transferAccountCommand) {

    ECACashPool ecaCashPool = ecaCashPoolRepository.findOne(ecaCashPoolId);

    if (ecaCashPool == null)
      return null;

    ecaCashPool.setUnassignedCapital(
        ecaCashPool.getUnassignedCapital() - transferAccountCommand.getChangedCapital());
    return ecaCashPoolRepository.save(ecaCashPool);

  }

  @Transactional
  public Collection<ECACashPool> allot(Long dstEcaCashPoolId, Long srcEcaCashPoolId,
      Double changedCapital) {

    List<ECACashPool> result = new ArrayList<ECACashPool>();

    ECACashPool dstEcaCashPool = ecaCashPoolRepository.findOne(dstEcaCashPoolId);

    if (dstEcaCashPool == null)
      return null;

    dstEcaCashPool
        .setUnassignedCapital(dstEcaCashPool.getUnassignedCapital() + Math.abs(changedCapital));
    result.add(ecaCashPoolRepository.save(dstEcaCashPool));

    ECACashPool srcEcaCashPool = ecaCashPoolRepository.findOne(srcEcaCashPoolId);
    if (srcEcaCashPool == null)
      return null;
    srcEcaCashPool
        .setUnassignedCapital(srcEcaCashPool.getUnassignedCapital() - Math.abs(changedCapital));

    result.add(ecaCashPoolRepository.save(srcEcaCashPool));

    return result;
  }

}
