package com.winsigns.investment.tradeService.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsigns.investment.tradeService.command.InstructionCommand;

@Service
public class TradeService {

    @Autowired
    SSEAStockTradeService sseAStockTradeService;

    List<TradeServiceBase> services = new ArrayList<TradeServiceBase>();

    TradeService() {
        services.add(sseAStockTradeService);
    }

    public void acceptInstruction(InstructionCommand instructionCommand) {

    }
}
