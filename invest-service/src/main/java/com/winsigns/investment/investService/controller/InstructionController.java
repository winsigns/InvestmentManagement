package com.winsigns.investment.investService.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.winsigns.investment.investService.command.CreateInstructionCommand;
import com.winsigns.investment.investService.command.UpdateInstructionCommand;
import com.winsigns.investment.investService.model.Instruction;
import com.winsigns.investment.investService.resource.InstructionResource;
import com.winsigns.investment.investService.resource.InstructionResourceAssembler;
import com.winsigns.investment.investService.service.InstructionService;

@RestController
@RequestMapping("/instructions")
public class InstructionController {

    @Autowired
    InstructionService instructionService;

    @GetMapping("/findByDate/{date}")
    public Resources<InstructionResource> readInstructions(@PathVariable Date date) {
        Link link = linkTo(InstructionController.class).withSelfRel();
        Link dateLink = linkTo(methodOn(InstructionController.class).readInstructions(date)).withRel("find");
        return new Resources<InstructionResource>(
                new InstructionResourceAssembler().toResources(instructionService.findByCreateDate(date)), link,
                dateLink);
    }

    @GetMapping
    public Resources<InstructionResource> readInstructions() {
        Link link = linkTo(InstructionController.class).withSelfRel();
        return new Resources<InstructionResource>(
                new InstructionResourceAssembler().toResources(instructionService.findAll()), link);
    }

    @GetMapping("/{instructionId}")
    public InstructionResource readInstruction(@PathVariable Long instructionId) {
        return new InstructionResourceAssembler().toResource(instructionService.findOne(instructionId));
    }

    @PostMapping
    public ResponseEntity<?> createInstruction(@RequestBody CreateInstructionCommand instructionCommand) {

        Instruction instruction = instructionService.addInstruction(instructionCommand);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(
                linkTo(methodOn(InstructionController.class).readInstruction(instruction.getId())).toUri());
        return new ResponseEntity<Object>(instruction, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{instructionId}")
    public InstructionResource updateInstruction(@PathVariable Long instructionId,
            @RequestBody UpdateInstructionCommand instructionCommand) {
        return new InstructionResourceAssembler()
                .toResource(instructionService.updateInstruction(instructionId, instructionCommand));
    }

    @DeleteMapping("/{instructionId}")
    public ResponseEntity<?> deleteInstruction(@PathVariable Long instructionId) {
        instructionService.deleteInstruction(instructionId);
        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{instructionId}/commit")
    public ResponseEntity<?> commitInstruction(@PathVariable Long instructionId) {

        instructionService.commitInstruction(instructionId);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders
                .setLocation(linkTo(methodOn(InstructionController.class).readInstruction(instructionId)).toUri());
        return new ResponseEntity<Object>("提交成功", responseHeaders, HttpStatus.OK);
    }
}
