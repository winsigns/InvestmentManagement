package com.winsigns.investment.investService.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.winsigns.investment.investService.command.CreateInstructionCommand;
import com.winsigns.investment.investService.command.UpdateInstructionCommand;
import com.winsigns.investment.investService.constant.InstructionMessageCode;
import com.winsigns.investment.investService.constant.InstructionMessageType;
import com.winsigns.investment.investService.constant.InstructionOperatorType;
import com.winsigns.investment.investService.constant.InstructionStatus;
import com.winsigns.investment.investService.integration.FundServiceIntegration;
import com.winsigns.investment.investService.model.Instruction;
import com.winsigns.investment.investService.model.InstructionMessage;
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
 * 
 * @author yimingjin
 *
 */
@Service
public class InstructionService {

  @Autowired
  InstructionRepository instructionRepository;

  @Autowired
  InstructionMessageRepository instructionMessageRepository;

  @Autowired
  FundServiceIntegration fundService;

  @Autowired
  InstructionBasketService basketService;

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
    thisInstruction.setInvestType(instructionCommand.getInvestType());
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

    if (thisInstruction.getSecurityId() == null) {
      thisInstruction.addInstructionMessage(new InstructionMessage(thisInstruction, "securityId",
          InstructionMessageType.ERROR, InstructionMessageCode.INVEST_SECURITY_CANNOT_NULL));
    }

    if (thisInstruction.getInvestService() == null) {
      thisInstruction.addInstructionMessage(new InstructionMessage(thisInstruction, "investService",
          InstructionMessageType.ERROR, InstructionMessageCode.INVEST_SERVICE_CANNOT_NULL));
    }

    if (thisInstruction.getInvestType() == null) {
      thisInstruction.addInstructionMessage(new InstructionMessage(thisInstruction, "investType",
          InstructionMessageType.ERROR, InstructionMessageCode.INVEST_TYPE_CANNOT_NULL));
    }
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
      basketService.deleteInstructionBasket(instructionId);
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
  public Instruction commitInstruction(Long instructionId) {

    Instruction thisInstruction = instructionRepository.findOne(instructionId);

    Assert.notNull(thisInstruction);

    if (!thisInstruction.getExecutionStatus().isSupportedOperator(InstructionOperatorType.COMMIT)) {
      thisInstruction.addInstructionMessage(
          new InstructionMessage(thisInstruction, "executionStatus", InstructionMessageType.ERROR,
              InstructionMessageCode.INSTRUCTION_OPERATOR_NOT_SUPPORT));
      return thisInstruction;
    }

    if (!thisInstruction.isBasket()) {
      if (!commitCheck(thisInstruction)) {
        return thisInstruction;
      }

      if (InvestServiceManager.getInstance().commitInstruction(thisInstruction)) {
        thisInstruction.setExecutionStatus(InstructionStatus.COMMITING);
        instructionRepository.save(thisInstruction);
      } else {
        thisInstruction
            .addInstructionMessage(new InstructionMessage(thisInstruction, "executionStatus",
                InstructionMessageType.ERROR, InstructionMessageCode.INSTRUCTION_COMMIT_FAIL));
        thisInstruction = instructionRepository.save(thisInstruction);
      }
    } else {
      // TODO 篮子的提交待处理
    }

    return thisInstruction;
  }

  protected boolean commitCheck(Instruction thisInstruction) {
    check(thisInstruction);
    for (InstructionMessage message : thisInstruction.getMessages()) {
      if (message.getMessageType().equals(InstructionMessageType.ERROR)) {
        return false;
      }
    }
    return true;
  }
}
