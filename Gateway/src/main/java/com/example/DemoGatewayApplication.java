package com.example;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.winsigns.investment.grpc.InstructionCommitOuterClass.InstructionCommit;
import com.winsigns.investment.grpc.InstructionOuterClass.Instruction;
import com.winsigns.investment.grpc.capitalGrpc;
import com.winsigns.investment.grpc.capitalGrpc.capitalBlockingStub;
import com.winsigns.investment.grpc.investGrpc;
import com.winsigns.investment.grpc.CaptialService.CapitalOperatorMsg;
import com.winsigns.investment.grpc.investGrpc.investBlockingStub;
import com.winsigns.investment.grpc.model.CapitalOperatorOuterClass.CapitalOperator;

import io.grpc.*;

@SpringBootApplication
public class DemoGatewayApplication {
	
	@RestController
	public class Gateway{
		
		@RequestMapping("/InstructionCreate")
		public String InstructionCreate_Request(@RequestParam(value="name", defaultValue="Noname") String name){
			final String template = "Pass Request through grpc.<br/>\nResponse from demo: %s<br>\n";
			
			ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 30000).usePlaintext(true).build();
			investBlockingStub stub = investGrpc.newBlockingStub(channel);
			
			Instruction.Builder instruction = Instruction.newBuilder();
			instruction.setPortfolioId("test_portfolio");
			instruction.setSecurityId("test_security");
			instruction.setInvestSvc("stock");
			instruction.setInvestDirection("buy");
			instruction.setCurrency("cny");
			instruction.setCostPrice(10);
			instruction.setVolumeType(Instruction.InstructionVolumeType.FixedType);
			instruction.setQuantity(10);
			instruction.setAmount(10);
			//instruction.setCounterparty("sfs");
			
			
			Instruction reply = stub.createInstruction(instruction.build());
			
			//String result = String.format(template, reply.getPortfolio());
			return  reply.getInstructId();
		}
		
		@RequestMapping("/InstructionCommit")
		public String InstructionCommit_Request(@RequestParam(value="name", defaultValue="Noname") String name){
			final String template = "Pass Request through grpc.<br/>\nResponse from demo: %s<br>\n";
			
			ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 30000).usePlaintext(true).build();
			investBlockingStub stub = investGrpc.newBlockingStub(channel);
			
			InstructionCommit.Builder instruction = InstructionCommit.newBuilder();
			instruction.setInstructId("2c9f74205a17a4e2015a17a4ee850000");
			
			InstructionCommit reply = stub.commitInstruction(instruction.build());
			
			//String result = String.format(template, reply.getPortfolio());
			return  String.valueOf(reply.getHeader().getStatus());
		}
		
		@RequestMapping("/OperatorCapital")
		public String OperatorCapital_Request(
				@RequestParam(value="capital_operator", defaultValue="IN") CapitalOperator.CapitalOperatorType capital_operator,
				@RequestParam(value="source_account", required=true) String source_account,
				@RequestParam(value="match_account", required=false) String match_account,
				@RequestParam(value="currency", required=true) String currency,
				@RequestParam(value="capital_change", required=true) Double capital_change){
			
			Map<String, String> test = new HashMap<String, String>();
			test.put("capital_operator", capital_operator.toString());
			test.put("source_account", source_account);
			test.put("match_account", match_account);
			test.put("currency", currency);
			test.put("capital_change", capital_change.toString());
			ResponseEntity<String> reponse = new RestTemplate().getForEntity(
			        "http://localhost:20002/OperatorCapital?capital_operator={capital_operator}&source_account={source_account}&match_account={match_account}&currency={currency}&capital_change={capital_change}",
			        String.class, test);

			    if(reponse.getStatusCode() == HttpStatus.NOT_MODIFIED) {
			       return "FAIL";
			    }
			    
			    Double reponse2 = (Double) new RestTemplate().getForObject(
				        "http://localhost:20002/OperatorCapital?capital_operator={capital_operator}&source_account={source_account}&match_account={match_account}&currency={currency}&capital_change={capital_change}",
				        Double.class, capital_operator, source_account, match_account, currency, capital_change);
			    	
			
			return "SUCCESS";
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoGatewayApplication.class, args);
	}
}
