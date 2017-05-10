package com.winsigns.investment.investService.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.hateoas.core.Relation;

import com.winsigns.investment.framework.constant.CurrencyCode;
import com.winsigns.investment.framework.hal.HALResponse;
import com.winsigns.investment.framework.model.Item;
import com.winsigns.investment.investService.constant.InstructionOperatorType;
import com.winsigns.investment.investService.model.Instruction;
import com.winsigns.investment.investService.model.InstructionMessage;
import com.winsigns.investment.investService.service.common.IInvestService;
import com.winsigns.investment.investService.service.common.IInvestType;
import com.winsigns.investment.investService.service.common.InvestServiceManager;

import lombok.Getter;

/**
 * 指令的资源
 * 
 * @author yimingjin
 *
 */
@Relation(value = "instruction", collectionRelation = "instructions")
public class InstructionResource extends HALResponse<Instruction> {

  @Getter
  protected final String investServiceLabel;

  @Getter
  protected final String investTypeLabel;

  @Getter
  protected final String executionStatusLabel;

  @Getter
  protected final String currencyLabel;

  @Getter
  protected final List<Item> supportedOperator = new ArrayList<Item>();

  @Getter
  protected final List<Item> supportedInvestService = new ArrayList<Item>();

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
          errors.put(instructionMessage.getFieldName(), instructionMessage.getMessage());
          break;
        case WARNING:
          warnings.put(instructionMessage.getFieldName(), instructionMessage.getMessage());
          break;
        default:
          break;

      }
    }
    // 1.字段的国际化
    this.executionStatusLabel = instruction.getExecutionStatus().i18n();
    IInvestService service =
        InvestServiceManager.getInvestServiceManager().getService(instruction.getInvestService());
    this.investServiceLabel = service.getSimpleName();
    this.investTypeLabel = service.getInvestType(instruction.getInvestType()).i18n();

    if (instruction.getCurrency() != null) {
      this.currencyLabel = instruction.getCurrency().i18n();
    } else {
      this.currencyLabel = null;
    }
    // 2.状态支持的操作
    for (InstructionOperatorType type : instruction.getExecutionStatus().getSupportedOperator()) {
      this.supportedOperator.add(new Item(type.name(), type.i18n()));
    }

    // 3.支持的投资服务
    for (IInvestService svc : InvestServiceManager.getInvestServiceManager().getServices()) {
      IInvestType[] investTypes = svc.getInvestType();
      for (int i = 0; i < investTypes.length; ++i) {
        this.supportedInvestService.add(new Item(svc.getName() + "." + investTypes[i].name(),
            svc.getSimpleName() + "-" + investTypes[i].i18n()));
      }
    }

    // 4.支持的币种
    for (CurrencyCode code : CurrencyCode.values()) {
      this.supportedCurrencies.add(new Item(code.name(), code.i18n()));
    }
  }

}
