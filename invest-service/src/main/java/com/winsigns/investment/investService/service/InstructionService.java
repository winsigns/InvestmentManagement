package com.winsigns.investment.investService.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.winsigns.investment.framework.i18n.i18nHelper;
import com.winsigns.investment.investService.command.CreateInstructionBasketCommand;
import com.winsigns.investment.investService.command.CreateInstructionCommand;
import com.winsigns.investment.investService.command.UpdateInstructionCommand;
import com.winsigns.investment.investService.constant.InstructionMessageCode;
import com.winsigns.investment.investService.constant.InstructionMessageType;
import com.winsigns.investment.investService.constant.InstructionOperatorType;
import com.winsigns.investment.investService.constant.InstructionStatus;
import com.winsigns.investment.investService.integration.FundServiceIntegration;
import com.winsigns.investment.investService.model.Instruction;
import com.winsigns.investment.investService.model.InstructionBasket;
import com.winsigns.investment.investService.model.InstructionMessage;
import com.winsigns.investment.investService.repository.InstructionBasketRepository;
import com.winsigns.investment.investService.repository.InstructionMessageRepository;
import com.winsigns.investment.investService.repository.InstructionRepository;
import com.winsigns.investment.investService.service.common.InvestServiceManager;

/**
 * 指令服务
 * <p>
 * 创建指令<br>
 * 修改指令<br>
 * 删除指令<br>
 * 提交指令<br>
 * 
 * @author yimingjin
 *
 */
@Service
public class InstructionService {

  Logger log = LoggerFactory.getLogger(InstructionService.class);

  final static String DEFAULT_BASKET_NAME = "Instruction.DEFAULT_BASKET_NAME";

  @Autowired
  InstructionRepository instructionRepository;

  @Autowired
  InstructionMessageRepository instructionMessageRepository;

  @Autowired
  FundServiceIntegration fundService;

  @Autowired
  InstructionBasketRepository basketRepository;

  /**
   * 查询一条指令
   * 
   * @param instructionId
   * @return
   */
  public Instruction readInstruction(Long instructionId) {

    Assert.notNull(instructionId);

    Instruction thisInstruction = instructionRepository.findOne(instructionId);

    Assert.notNull(thisInstruction);

    return thisInstruction;
  }

  /**
   * 增加一条指令
   * 
   * @param instructionCommand
   * @return
   */
  public Instruction addInstruction(CreateInstructionCommand instructionCommand) {

    // 投资经理必须输入，以后可能在controller中通过session赋值
    Assert.notNull(instructionCommand.getInvestManagerId());

    Instruction newInstruction = new Instruction();

    newInstruction.setInvestManagerId(instructionCommand.getInvestManagerId());
    newInstruction.setExecutionStatus(InstructionStatus.DRAFT);
    newInstruction = instructionRepository.save(newInstruction);
    check(newInstruction);
    return instructionRepository.save(newInstruction);
  }

  /**
   * 修改一条指令
   * 
   * @param instructionId 指令id
   * @param instructionCommand
   * @return
   */
  @Transactional
  public Instruction updateInstruction(Long instructionId,
      UpdateInstructionCommand instructionCommand) {
    Instruction thisInstruction = instructionRepository.findOne(instructionId);

    Assert.notNull(thisInstruction);

    if (!thisInstruction.getExecutionStatus().isSupportedOperator(InstructionOperatorType.MODIFY)) {
      return null;
    }

    thisInstruction.setPortfolioId(instructionCommand.getPortfolioId());
    thisInstruction.setSecurityId(instructionCommand.getSecurityId());
    thisInstruction.setInvestService(instructionCommand.getInvestService());
    thisInstruction.setInvestDirection(instructionCommand.getInvestDirection());
    thisInstruction.setCurrency(instructionCommand.getCurrency());
    thisInstruction.setCostPrice(instructionCommand.getCostPrice());
    thisInstruction.setVolumeType(instructionCommand.getVolumeType());
    thisInstruction.setQuantity(instructionCommand.getQuantity());
    thisInstruction.setAmount(instructionCommand.getAmount());

    check(thisInstruction);
    return instructionRepository.save(thisInstruction);
  }

  protected void check(Instruction thisInstruction) {
    instructionMessageRepository.deleteByInstruction(thisInstruction);
    checkPortfolio(thisInstruction);
    checkSecurityAndDirection(thisInstruction);
  }

  /**
   * 检查指令的投资组合信息
   * 
   * @param thisInstruction 需要检查的指令
   */
  protected void checkPortfolio(Instruction thisInstruction) {

    Long portfolioId = thisInstruction.getPortfolioId();

    if (portfolioId == null) {
      thisInstruction.addInstructionMessage(new InstructionMessage(thisInstruction, "portfolioId",
          InstructionMessageType.ERROR, InstructionMessageCode.PORTFOLIO_NOT_NULL));
    } else {
      // 检查该投资组合是否为该投资经理管理
      Long investManagerId = fundService.getPortfolioInvestManager(portfolioId);
      if (investManagerId == null
          || !investManagerId.equals(thisInstruction.getInvestManagerId())) {
        thisInstruction.addInstructionMessage(
            new InstructionMessage(thisInstruction, "portfolioId", InstructionMessageType.ERROR,
                InstructionMessageCode.PORTFOLIO_NOT_MATCHED_INVESTMANAGER));
      }
    }
  }

  /**
   * 检查指令的投资标的和方向
   * 
   * @param thisInstruction
   */
  protected void checkSecurityAndDirection(Instruction thisInstruction) {
    // TODO
  }

  /**
   * 删除指令
   * <p>
   * 逻辑删除
   * 
   * @param instructionId
   */
  @Transactional
  public void deleteInstruction(Long instructionId) {

    Assert.notNull(instructionId);

    Instruction thisInstruction = instructionRepository.findOne(instructionId);

    Assert.notNull(thisInstruction);

    if (!thisInstruction.isBasket()) {
      if (!thisInstruction.getExecutionStatus()
          .isSupportedOperator(InstructionOperatorType.DELETE)) {
        return;
      }

      thisInstruction.setExecutionStatus(InstructionStatus.DELETED);
      instructionRepository.save(thisInstruction);
    } else {
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

  /**
   * 批量删除指令
   * 
   * @param instructionIds
   */
  @Transactional
  public void deleteInstruction(List<Long> instructionIds) {
    for (Long id : instructionIds) {
      deleteInstruction(id);
    }
  }

  /**
   * 根据条件查找正常状态的指令
   * 
   * @param investManagerId
   * @return
   */
  public Collection<Instruction> findNormalInstructionByCondition(Long investManagerId,
      Date beginDate, Date endDate) {

    Assert.notNull(investManagerId);
    if (beginDate == null) {
      beginDate = new Date();
    }
    if (endDate == null) {
      endDate = new Date();
    }
    return instructionRepository
        .findByInvestManagerIdAndCreateDateBetweenAndExecutionStatusNotAndInstructionBasketIsNull(
            investManagerId, beginDate, endDate, InstructionStatus.DELETED, sortByCreateTime());
  }

  /**
   * 根据条件查找已删除的指令
   * 
   * @param investManagerId
   * @return
   */
  public Collection<Instruction> findDeletedInstructionByCondition(Long investManagerId) {
    return instructionRepository.findByInvestManagerIdAndExecutionStatusAndInstructionBasketIsNull(
        investManagerId, InstructionStatus.DELETED, sortByCreateTime());
  }

  /**
   * 默认的排序方式
   * 
   * @return
   */
  private Sort sortByCreateTime() {
    return new Sort(Sort.Direction.DESC, "createTime");
  }

  /**
   * 提交指令
   * 
   * @param instructionId
   * @return
   */
  @Transactional
  public boolean commitInstruction(Long instructionId) {

    Instruction thisInstruction = instructionRepository.findOne(instructionId);

    if (!thisInstruction.getExecutionStatus().isSupportedOperator(InstructionOperatorType.COMMIT)) {
      return false;
    }

    if (!thisInstruction.isBasket()) {
      InvestServiceManager.getInstance().commitInstruction(thisInstruction);
      thisInstruction.setExecutionStatus(InstructionStatus.COMMITING);
      instructionRepository.save(thisInstruction);
    } else {
      // TODO 篮子的提交待处理
    }

    return true;
  }

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

    return addInstructionOfBasket(newBasket, command);
  }

  /**
   * 在篮子下增加一条指令
   * 
   * @param thisBasket
   * @param command
   * @return
   */
  protected InstructionBasket addInstructionOfBasket(InstructionBasket thisBasket,
      CreateInstructionCommand command) {
    Assert.notNull(thisBasket);
    Instruction newInstruction = this.addInstruction(command);
    thisBasket.addInstruction(newInstruction);
    return basketRepository.save(thisBasket);
  }

  /**
   * 在篮子下增加一条指令
   * 
   * @param basketId 篮子号
   * @param instructionCommand
   * @return 返回这个篮子
   */
  public InstructionBasket addInstructionOfBasket(Long basketId,
      CreateInstructionCommand instructionCommand) {

    Assert.notNull(basketId);

    InstructionBasket thisBasket = basketRepository.findOne(basketId);

    return addInstructionOfBasket(thisBasket, instructionCommand);
  }
}
