package com.winsigns.investment.invest;

import org.lognet.springboot.grpc.GRpcService;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import com.winsigns.investment.grpc.HeaderOuterClass.Header;
import com.winsigns.investment.grpc.InstructionCommitOuterClass.InstructionCommit;
import com.winsigns.investment.grpc.InstructionOuterClass.Instruction;
import com.winsigns.investment.grpc.tradeGrpc;
import com.winsigns.investment.grpc.tradeGrpc.tradeBlockingStub;
import com.winsigns.investment.invest.model.InstructionRepository;
import com.winsigns.investment.invest.model.InstructionType;
import com.winsigns.investment.grpc.investGrpc;

import javax.annotation.Resource;

@GRpcService
public class InvestServiceImplement extends investGrpc.investImplBase{
	
	@Resource
    private InstructionRepository demoRepository;
	
	//创建指令
	@Override
	public void createInstruction(Instruction request, StreamObserver<Instruction> responseObserver) {
		
		com.winsigns.investment.invest.model.Instruction instruction = new com.winsigns.investment.invest.model.Instruction();
		//instruction.setInstructId(String.valueOf(i++) );
		instruction.setPortfolioId(request.getPortfolioId());
		instruction.setSecurityId(request.getSecurityId());
		instruction.setInvestSvc(request.getInvestSvc());
		instruction.setInvestDirection(request.getInvestDirection());
		instruction.setCurrency(request.getCurrency());
		instruction.setLimitPrice(request.getLimitPrice());
		instruction.setCostPrice(request.getCostPrice());
		instruction.setVolumeType(InstructionType.values()[request.getVolumeTypeValue()]);
		instruction.setQuantity(request.getQuantity());
		instruction.setAmount(request.getAmount());
		instruction.setCounterparty(request.getCounterparty());
		com.winsigns.investment.invest.model.Instruction instruction_res = demoRepository.save(instruction);
			
		responseObserver.onNext(Instruction.newBuilder().setInstructId(instruction_res.getInstructId()).build());
		responseObserver.onCompleted();
	}
	
	//提交指令
	@Override
	public void commitInstruction(InstructionCommit request, StreamObserver<InstructionCommit> responseObserver) {
		
		com.winsigns.investment.invest.model.Instruction one_instruction = demoRepository.findByInstructId(request.getInstructId());
		
		Header.Builder header_rsp = Header.newBuilder();
		
		if(one_instruction == null)
		{
			header_rsp.setStatus(Header.Status.FAIL);
		}
		else
		{	
			Instruction.Builder instruction = Instruction.newBuilder();
			instruction.setPortfolioId(one_instruction.getPortfolioId());
			instruction.setSecurityId(one_instruction.getSecurityId());
			instruction.setInvestSvc(one_instruction.getInvestSvc());
			instruction.setInvestDirection(one_instruction.getInvestDirection());
			instruction.setCurrency(one_instruction.getCurrency());
			instruction.setLimitPrice(one_instruction.getLimitPrice());
			instruction.setCostPrice(one_instruction.getCostPrice());
			instruction.setVolumeTypeValue(one_instruction.getVolumeType().ordinal());
			instruction.setQuantity(one_instruction.getQuantity());
			instruction.setAmount(one_instruction.getAmount());
			instruction.setCounterparty(one_instruction.getCounterparty());
			
			System.out.println(instruction.getAmount());
			System.out.println(instruction.getInvestSvc());
			
			ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 30001).usePlaintext(true).build();
			tradeBlockingStub stub = tradeGrpc.newBlockingStub(channel);
			Instruction reply = stub.virtualDone(instruction.build());
			channel.shutdownNow();
			
			if (reply.getHeader().getStatus() == Header.Status.SUCCESS)
			{
				
				header_rsp.setStatus(Header.Status.SUCCESS);
				
				
			}
			else
			{
				header_rsp.setStatus(Header.Status.FAIL);
			}
			
			
		
		}
		//创建应答
		InstructionCommit.Builder instruction_rsp = InstructionCommit.newBuilder();
		instruction_rsp.setHeader(header_rsp);
		
		responseObserver.onNext(instruction_rsp.build());
		responseObserver.onCompleted();	
	}
	
}
