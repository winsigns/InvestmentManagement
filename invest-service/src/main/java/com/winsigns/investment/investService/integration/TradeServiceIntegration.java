package com.winsigns.investment.investService.integration;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import com.winsigns.investment.framework.integration.AbstractIntegration;
import com.winsigns.investment.investService.command.CommitInstructionCommand;
import com.winsigns.investment.investService.model.Instruction;

import lombok.extern.slf4j.Slf4j;

/**
 * 与trade-service的交互
 * 
 * @author yimingjin
 *
 */
@Component
@Slf4j
public class TradeServiceIntegration extends AbstractIntegration {

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

    HttpHeaders reponseHeaders = new HttpHeaders();
    String language = this.getHeaderParam("accept-language");
    reponseHeaders.set("accept-language", language);
    HttpEntity<CommitInstructionCommand> requestEntity =
        new HttpEntity<CommitInstructionCommand>(command, reponseHeaders);
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
