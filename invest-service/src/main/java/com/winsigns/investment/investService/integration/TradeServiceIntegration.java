package com.winsigns.investment.investService.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import com.winsigns.investment.framework.integration.AbstractIntegration;
import com.winsigns.investment.investService.command.CommitInstructionCommand;
import com.winsigns.investment.investService.model.Instruction;

/**
 * 与trade-service的交互
 * 
 * @author yimingjin
 *
 */
@Component
public class TradeServiceIntegration extends AbstractIntegration {

  private Logger log = LoggerFactory.getLogger(TradeServiceIntegration.class);

  private String tradeURL = "/trades";

  @Override
  public String getIntegrationName() {
    return "trade-service";
  }

  public boolean commitInstruction(Instruction instruction) {

    CommitInstructionCommand command = new CommitInstructionCommand();
    command.setInstructionId(instruction.getId());
    command.setPortfolioId(instruction.getPortfolioId());
    command.setSecurityId(instruction.getSecurityId());
    command.setInvestService(instruction.getInvestService());
    command.setInvestType(instruction.getInvestType());
    command.setCurrency(instruction.getCurrency());
    command.setCostPrice(instruction.getCostPrice());
    command.setVolumeType(instruction.getVolumeType());
    command.setQuantity(instruction.getQuantity());
    command.setAmount(instruction.getAmount());

    HttpEntity<CommitInstructionCommand> requestEntity =
        new HttpEntity<CommitInstructionCommand>(command);

    try {
      ResponseEntity<String> resultStr = restTemplate
          .postForEntity(this.getIntegrationURI() + tradeURL, requestEntity, String.class);

      if (resultStr.getStatusCode().compareTo(HttpStatus.OK) == 0) {
        return true;
      }
      return false;
    } catch (RestClientException e) {
      log.warn(e.getMessage());
      return false;
    }
  }
}
