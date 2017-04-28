package com.winsigns.investment.inventoryService.capital.generalCapital;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsigns.investment.framework.constant.CurrencyCode;
import com.winsigns.investment.framework.i18n.i18nHelper;
import com.winsigns.investment.inventoryService.capital.common.AbstractCapitalService;
import com.winsigns.investment.inventoryService.command.CreateFundAccountCapitalPoolCommand;
import com.winsigns.investment.inventoryService.constant.ExternalCapitalAccountType;
import com.winsigns.investment.inventoryService.exception.ResourceApplicationExcepiton;
import com.winsigns.investment.inventoryService.model.CapitalDetail;
import com.winsigns.investment.inventoryService.model.CapitalSerial;
import com.winsigns.investment.inventoryService.model.FloatCapitalSerial;
import com.winsigns.investment.inventoryService.model.FundAccountCapitalPool;
import com.winsigns.investment.inventoryService.repository.CapitalDetailRepository;
import com.winsigns.investment.inventoryService.service.CapitalSerialService;

@Service
public class GeneralCapitalService extends AbstractCapitalService {

  public enum GeneralCapitalErrorCode {
    // 未找到资金
    NOT_FIND_CAPITAL_RESOURCE,
    // 可用资金不足
    AVAILABLE_CAPITAL_NOT_ENOUGH;

    /**
     * 国际化
     * 
     * @return
     */
    public String i18n() {
      return i18nHelper.i18n(this);
    }
  }

  @Autowired
  GeneralCapitalRepository generalCapitalRepository;

  @Autowired
  CapitalDetailRepository capitalDetailRepository;

  @Autowired
  CapitalSerialService capitalSerialService;

  @Override
  public ExternalCapitalAccountType getAccountType() {
    return ExternalCapitalAccountType.CHINA_GENERAL_CAPITAL_ACCOUNT;
  }

  @Override
  public FundAccountCapitalPool createFundAccountCapitalPool(
      CreateFundAccountCapitalPoolCommand command) {
    GeneralCapitalPool capitalPool = generalCapitalRepository
        .findByFundAccountIdAndCurrency(command.getFundAccountId(), command.getCurrency());
    if (capitalPool == null) {
      capitalPool = new GeneralCapitalPool();
      capitalPool.setFundAccountId(command.getFundAccountId());
      capitalPool.setCurrency(command.getCurrency());
      capitalPool.setAccountType(this.getAccountType());
      capitalPool = generalCapitalRepository.save(capitalPool);
    }
    return capitalPool;
  }

  @Override
  public List<CapitalSerial> apply(Long fundAccountId, CurrencyCode currency, Double appliedCapital)
      throws ResourceApplicationExcepiton {

    List<CapitalSerial> serials = new ArrayList<CapitalSerial>();

    GeneralCapitalPool capital =
        generalCapitalRepository.findByFundAccountIdAndCurrency(fundAccountId, currency);
    if (capital == null) {
      throw new ResourceApplicationExcepiton(
          GeneralCapitalErrorCode.NOT_FIND_CAPITAL_RESOURCE.i18n());
    }

    List<CapitalDetail> capitalDetails =
        capitalDetailRepository.findByCapitalPoolOrderByAvailableCapitalDesc(capital);

    if (capitalDetails == null || capitalDetails.isEmpty()) {
      throw new ResourceApplicationExcepiton(
          GeneralCapitalErrorCode.NOT_FIND_CAPITAL_RESOURCE.i18n());
    }

    if (appliedCapital.doubleValue() >= 0) { // 卖出
      // 增加最少的资金池的资金
      CapitalDetail capitalDetail = new CapitalDetail();
      capitalDetail.changeFloatAvailableCapital(appliedCapital);
      capitalDetail.changeFloatCash(appliedCapital);
      capitalDetailRepository.save(capitalDetail);

      // 添加流水
      CapitalSerial serial = capitalSerialService.addCapitalSerial(FloatCapitalSerial.class,
          capital.getId(), null, capital.getCurrency(), appliedCapital);
      serials.add(serial);
    } else { // 买入
      appliedCapital = Math.abs(appliedCapital);
      boolean isEnded = false;
      for (CapitalDetail capitalDetail : capitalDetails) {
        Double thisAmount = 0.0;
        Double currRemain = capitalDetail.getFloatAvailableCapital() - appliedCapital;
        if (currRemain.doubleValue() >= 0) { // 当前资金记录有剩余，则表示已经分配完
          thisAmount = appliedCapital;
          isEnded = true;
        } else {
          thisAmount = capitalDetail.getFloatAvailableCapital();
        }
        if (thisAmount.doubleValue() > 0) {
          appliedCapital -= thisAmount;
          capitalDetail.changeFloatAvailableCapital(-thisAmount);
          capitalDetail.changeFloatCash(-thisAmount);
          capitalDetailRepository.save(capitalDetail);

          // 添加流水
          CapitalSerial serial = capitalSerialService.addCapitalSerial(FloatCapitalSerial.class,
              capital.getId(), null, capital.getCurrency(), -thisAmount);
          serials.add(serial);
        }
        if (isEnded) {
          break;
        }
      }

      // 如果最后还有剩余，则抛异常
      if (appliedCapital.doubleValue() > 0) {
        throw new ResourceApplicationExcepiton(
            GeneralCapitalErrorCode.AVAILABLE_CAPITAL_NOT_ENOUGH.i18n());
      }
    }
    return serials;
  }
}
