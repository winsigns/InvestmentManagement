package com.winsigns.investment.investService.command;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class BatchDeleteInstructionCommand {

  List<Long> instructionIds = new ArrayList<Long>();
}
