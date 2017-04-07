package com.winsigns.investment.investService.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.winsigns.investment.framework.hal.HALResponse;
import com.winsigns.investment.framework.i18n.i18nHelper;
import com.winsigns.investment.framework.model.Item;
import com.winsigns.investment.investService.constant.CurrencyCode;
import com.winsigns.investment.investService.constant.InstructionOperatorType;
import com.winsigns.investment.investService.model.Instruction;
import com.winsigns.investment.investService.model.InstructionMessage;
import com.winsigns.investment.investService.service.common.IInvestService;
import com.winsigns.investment.investService.service.common.InvestServiceManager;

import lombok.Getter;

/**
 * 指令的资源
 * 
 * @author yimingjin
 *
 */
public class InstructionResource extends HALResponse<Instruction> {

  @Getter
  protected final String executionStatusLabel;

  @Getter
  protected final List<Item> supportedOperator = new ArrayList<Item>();

  @Getter
  protected final List<Item> supprotedInvestService = new ArrayList<Item>();

  @Getter
  protected final List<Item> supportedCurrencies = new ArrayList<Item>();

  @Getter
  protected final HashMap<String, String> errors = new HashMap<String, String>();

  @Getter
  protected final HashMap<String, String> warnings = new HashMap<String, String>();

  public InstructionResource(Instruction instruction) {
    super(instruction);

    // 2.成交价/成交均价
    // 3.数量/总金额

    // 0.字段信息
    for (InstructionMessage instructionMessage : instruction.getMessages()) {
      switch (instructionMessage.getMessageType()) {
        case ERROR:
          errors.put(instructionMessage.getFieldName(), instructionMessage.getMessageCode().i18n());
          break;
        case WARNING:
          warnings.put(instructionMessage.getFieldName(),
              instructionMessage.getMessageCode().i18n());
          break;
        default:
          break;

      }
    }
    // 1.状态的国际化
    this.executionStatusLabel = instruction.getExecutionStatus().i18n();
    // 2.状态支持的操作
    for (InstructionOperatorType type : instruction.getExecutionStatus().getSupportedOperator()) {
      this.supportedOperator.add(new Item(type.name(), type.i18n()));
    }
    // 3.支持的投资服务
    for (Map.Entry<IInvestService, Enum<?>[]> info : InvestServiceManager.getInstance()
        .getServicesInfo().entrySet()) {
      Enum<?>[] enums = info.getValue();
      for (int i = 0; i < enums.length; ++i) {
        this.supprotedInvestService.add(
            new Item(info.getKey().getName() + "." + enums[i].name(), i18nHelper.i18n(enums[i])));
      }
    }
    // 4.支持的币种
    for (CurrencyCode code : CurrencyCode.values()) {
      this.supportedCurrencies.add(new Item(code.name(), code.i18n()));
    }
  }

}
