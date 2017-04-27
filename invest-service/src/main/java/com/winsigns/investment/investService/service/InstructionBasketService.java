package com.winsigns.investment.investService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.winsigns.investment.framework.i18n.i18nHelper;
import com.winsigns.investment.investService.command.CreateInstructionBasketCommand;
import com.winsigns.investment.investService.command.CreateInstructionCommand;
import com.winsigns.investment.investService.command.UpdateInstructionBasketCommand;
import com.winsigns.investment.investService.constant.InstructionOperatorType;
import com.winsigns.investment.investService.constant.InstructionStatus;
import com.winsigns.investment.investService.model.Instruction;
import com.winsigns.investment.investService.model.InstructionBasket;
import com.winsigns.investment.investService.repository.InstructionBasketRepository;

/**
 * 指令篮子服务
 * 
 * @author yimingjin
 *
 */
@Service
public class InstructionBasketService {

  @Autowired
  InstructionService instructionService;

  @Autowired
  InstructionBasketRepository basketRepository;

  final static String DEFAULT_BASKET_NAME = "Instruction.DEFAULT_BASKET_NAME";

  /**
   * 查询一个篮子
   * 
   * @param instructionBasketId
   * @return
   */
  public InstructionBasket readInstructionBasket(Long instructionBasketId) {

    Assert.notNull(instructionBasketId);

    InstructionBasket thisBasket = basketRepository.findOne(instructionBasketId);

    Assert.notNull(thisBasket);

    return thisBasket;
  }

  /**
   * 增加一个篮子
   * <p>
   * 如果没有传入篮子名，则带有默认篮子名<br>
   * 带有一条空的篮子指令
   * 
   * @param command
   * @return
   */
  public InstructionBasket addInstructionBasket(CreateInstructionBasketCommand command) {

    // 投资经理必须输入，以后可能在controller中通过session赋值
    Assert.notNull(command.getInvestManagerId());

    InstructionBasket newBasket = new InstructionBasket();

    newBasket.setInvestManagerId(command.getInvestManagerId());
    newBasket.setExecutionStatus(InstructionStatus.DRAFT);
    if (command.getBasketName() != null) {
      newBasket.setBasketName(command.getBasketName());
    } else {
      newBasket.setBasketName(i18nHelper.i18n(DEFAULT_BASKET_NAME));
    }

    return basketRepository.save(newBasket);
  }

  /**
   * 在篮子下增加一条指令
   * 
   * @param thisBasket
   * @param command
   * @return
   */
  protected Instruction addInstructionOfBasket(InstructionBasket thisBasket,
      CreateInstructionCommand command) {
    Assert.notNull(thisBasket);
    Instruction newInstruction = instructionService.addInstruction(command);
    thisBasket.addInstruction(newInstruction);
    basketRepository.save(thisBasket);
    return newInstruction;
  }

  /**
   * 在篮子下增加一条指令
   * 
   * @param basketId 篮子号
   * @param instructionCommand
   * @return 返回这条新增加的指令
   */
  public Instruction addInstructionOfBasket(Long basketId,
      CreateInstructionCommand instructionCommand) {

    Assert.notNull(basketId);

    InstructionBasket thisBasket = basketRepository.findOne(basketId);

    return addInstructionOfBasket(thisBasket, instructionCommand);
  }

  /**
   * 修改指令篮子
   * 
   * @param instructionId 指令id
   * @param instructionCommand
   * @return
   */
  @Transactional
  public InstructionBasket updateInstructionBasket(Long instructionId,
      UpdateInstructionBasketCommand instructionCommand) {
    Assert.notNull(instructionId);

    InstructionBasket thisBasket = basketRepository.findOne(instructionId);
    Assert.notNull(thisBasket);

    thisBasket.setBasketName(instructionCommand.getBasketName());
    // TODO 暂时只有修改名字
    return basketRepository.save(thisBasket);
  }

  /**
   * 删除指令篮子
   * <p>
   * 逻辑删除篮子
   * 
   * @param instructionId
   */
  @Transactional
  public void deleteInstructionBasket(Long instructionId) {

    Assert.notNull(instructionId);

    InstructionBasket thisBasket = basketRepository.findOne(instructionId);
    Assert.notNull(thisBasket);

    if (!thisBasket.getExecutionStatus().isSupportedOperator(InstructionOperatorType.DELETE)) {
      return;
    }

    for (Instruction instruction : thisBasket.getInstructions()) {
      instruction.setExecutionStatus(InstructionStatus.DELETED);
    }
    thisBasket.setExecutionStatus(InstructionStatus.DELETED);
    basketRepository.save(thisBasket);
  }
}
