package com.winsigns.investment.tradeService.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.winsigns.investment.tradeService.command.InstructionCommand;
import com.winsigns.investment.tradeService.model.Instruction;
import com.winsigns.investment.tradeService.resource.InstructionResource;
import com.winsigns.investment.tradeService.resource.InstructionResourceAssembler;
import com.winsigns.investment.tradeService.service.InstructionService;

@RestController
@ExposesResourceFor(Instruction.class)
@RequestMapping("/instructions")
public class InstructionController {

	@Autowired
	InstructionService instructionService;

	// @RequestMapping(method = RequestMethod.GET)
	// public Resources<InstructionResource>
	// readExternalCapitalAccounts(@PathVariable Long fundId) {
	// Link link = linkTo(InstructionController.class, fundId).withSelfRel();
	// return new Resources<InstructionResource>(
	// new
	// InstructionResourceAssembler().toResources(externalCapitalAccountService.findByFundId(fundId)),
	// link);
	// }

	@RequestMapping(value = "/{instructionId}", method = RequestMethod.GET)
	public InstructionResource readInstruction(@PathVariable Long instructionId) {
		return new InstructionResourceAssembler().toResource(instructionService.findOne(instructionId));
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> crreateInstruction(@RequestBody InstructionCommand instructionCommand) {

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(linkTo(methodOn(InstructionController.class)
				.readInstruction(instructionService.addInstruction(instructionCommand).getId())).toUri());
		return new ResponseEntity<Object>(responseHeaders, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{instructionId}", method = RequestMethod.PUT)
	public InstructionResource updateInstruction(@PathVariable Long instructionId,
			@RequestBody InstructionCommand instructionCommand) {
		return new InstructionResourceAssembler()
				.toResource(instructionService.updateInstruction(instructionId, instructionCommand));
	}

	@RequestMapping(value = "/{instructionId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteInstruction(@PathVariable Long instructionId) {
		instructionService.deleteInstruction(instructionId);
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}
}
