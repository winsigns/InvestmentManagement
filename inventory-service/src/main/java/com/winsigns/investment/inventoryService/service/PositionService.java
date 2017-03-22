package com.winsigns.investment.inventoryService.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsigns.investment.inventoryService.command.CreatePositionCommand;
import com.winsigns.investment.inventoryService.model.Position;
import com.winsigns.investment.inventoryService.repository.PositionRepository;

@Service
public class PositionService {

  @Autowired
  PositionRepository positionRepository;

  public Collection<Position> findAll() {
    return positionRepository.findAll();
  }

  public Position addPosition(CreatePositionCommand createPositionCommand) {
    Position position = positionRepository.findByPortfolioIdAndExternalTradeAccountIdAndSecurityId(
        createPositionCommand.getPortfolioId(), createPositionCommand.getExternalTradeAccountId(),
        createPositionCommand.getSecurityId());

    if (position == null) {
      position = new Position();

      position.setPortfolioId(createPositionCommand.getPortfolioId());
      position.setExternalTradeAccountId(createPositionCommand.getExternalTradeAccountId());
      position.setSecurityId(createPositionCommand.getSecurityId());
      position = positionRepository.save(position);
    }
    return position;
  }

  public Position findOne(Long positionId) {
    return positionRepository.findOne(positionId);
  }

}
