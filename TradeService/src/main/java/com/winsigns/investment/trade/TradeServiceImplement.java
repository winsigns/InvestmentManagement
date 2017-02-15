package com.winsigns.investment.trade;

import java.util.List;

import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import com.winsigns.investment.grpc.InstructionOuterClass.Instruction;
import com.winsigns.investment.grpc.tradeGrpc;
import com.winsigns.investment.trade.base.InstructionType;
import com.winsigns.investment.trade.base.TradeMetadata;
import com.winsigns.investment.trade.base.TradeServiceBase;
import com.winsigns.investment.trade.base.TradeServiceManager;

import io.grpc.stub.StreamObserver;

@GRpcService
public class TradeServiceImplement extends tradeGrpc.tradeImplBase{
	
	@Autowired
	TradeServiceManager serviceManager;
	
	//虚拟成交
	@Override
	public void virtualDone(Instruction request, StreamObserver<Instruction> responseObserver) {
				
		TradeMetadata metadata = new TradeMetadata();
		
		metadata.instructId = request.getInstructId();
		metadata.portfolioId = request.getPortfolioId();
		metadata.investSvc = request.getInvestSvc();
		metadata.investDirection = request.getInvestDirection();
		metadata.securityId = request.getSecurityId();
		//metadata.counterparty = request.getCounterparty();
		metadata.currency = request.getCurrency();
		metadata.costPrice = request.getLimitPrice() ? request.getCostPrice() : null;
		metadata.volumeType = InstructionType.values()[(request.getVolumeTypeValue())];
		metadata.quantity = request.getQuantity();
		metadata.amount = request.getAmount();
		
		List<TradeServiceBase> services = serviceManager.getAvailTradeService(metadata.investSvc, metadata.securityId);
		
		
		//TODO 这里能找到多个的时候逻辑需要重新处理
		for(TradeServiceBase service : services){
			
			service.init(metadata);
			
			service.calcQuota();
			
			service.checkInstruction();			
			
		}	
		
		responseObserver.onNext(Instruction.newBuilder().setInstructId("").build());
		responseObserver.onCompleted();
	}
}
