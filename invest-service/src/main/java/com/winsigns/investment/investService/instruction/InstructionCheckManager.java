package com.winsigns.investment.investService.instruction;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.winsigns.investment.framework.spring.SpringManager;
import com.winsigns.investment.investService.constant.InstructionMessageCode;
import com.winsigns.investment.investService.constant.InstructionMessageType;
import com.winsigns.investment.investService.constant.InstructionOperatorType;
import com.winsigns.investment.investService.model.Instruction;
import com.winsigns.investment.investService.model.InstructionMessage;
import com.winsigns.investment.investService.repository.InstructionMessageRepository;

/**
 * 指令检查的管理者
 * 
 * @author yimingjin
 *
 */
@Component
public class InstructionCheckManager {

  @Autowired
  InstructionMessageRepository instructionMessageRepository;

  static public InstructionCheckManager getInstructionCheckManager() {
    return SpringManager.getApplicationContext().getBean(InstructionCheckManager.class);
  }

  private List<IInstructionCheck> checks = new ArrayList<IInstructionCheck>();

  public void register(IInstructionCheck check) {
    checks.add(check);
  }

  /**
   * 检查一条指令，返回相关信息
   * 
   * @param thisInstruction
   * @return
   */
  public List<InstructionMessage> check(Instruction thisInstruction) {
    List<InstructionMessage> messages = new ArrayList<InstructionMessage>();
    for (IInstructionCheck check : checks) {
      IInstructionCheckResult result = check.check(thisInstruction);
      if (result != null) {
        messages.add(new InstructionMessage(thisInstruction, check.getField(),
            result.getMessageType(), result.getMessageCode()));
      }
    }
    return messages;
  }

  /**
   * 检查并且更新一条指令
   * 
   * @param thisInstruction
   */
  @Transactional
  public void checkAndUpdate(Instruction thisInstruction) {
    instructionMessageRepository.delete(thisInstruction.getMessages());
    thisInstruction.getMessages().clear();
    List<InstructionMessage> messages = check(thisInstruction);
    thisInstruction.setMessages(messages);
    // instructionMessageRepository.save(messages);
  }

  /**
   * 提交的检查
   * 
   * @param thisInstruction
   * @return
   */
  @Transactional
  public boolean commitCheck(Instruction thisInstruction) {

    if (!thisInstruction.getExecutionStatus().isSupportedOperator(InstructionOperatorType.COMMIT)) {
      thisInstruction.addInstructionMessage(
          new InstructionMessage(thisInstruction, "executionStatus", InstructionMessageType.ERROR,
              InstructionMessageCode.INSTRUCTION_OPERATOR_NOT_SUPPORT));
      return false;
    }

    checkAndUpdate(thisInstruction);
    for (InstructionMessage message : thisInstruction.getMessages()) {
      if (message.getMessageType().equals(InstructionMessageType.ERROR)) {
        return false;
      }
    }
    return true;
  }

}
