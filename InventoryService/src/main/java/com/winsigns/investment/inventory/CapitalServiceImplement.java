package com.winsigns.investment.inventory;

import org.lognet.springboot.grpc.GRpcService;

import com.winsigns.investment.grpc.CaptialService.MutilCapital;
import com.winsigns.investment.grpc.CaptialService.OneCapital;
import com.winsigns.investment.grpc.HeaderOuterClass.Header;
import com.winsigns.investment.grpc.capitalGrpc;
import com.winsigns.investment.grpc.CapitalOuterClass.Capital;

import io.grpc.stub.StreamObserver;

@GRpcService
public class CapitalServiceImplement extends capitalGrpc.capitalImplBase{
	
	@Override
	public void queryCapital(OneCapital request, StreamObserver<MutilCapital> responseObserver) {
		
		System.out.println("query");
		
		//创建应答
		MutilCapital.Builder reply = MutilCapital.newBuilder();
		Header.Builder headerReply = Header.newBuilder();		
		Capital.Builder bodyReply = Capital.newBuilder();
		
		Capital bodyRequest = request.getBody();

		bodyReply.setCapitalSvc(bodyRequest.getCapitalSvc()); //TODO 暂时写死
		bodyReply.setExtCapitalaccId("123456");
		bodyReply.setFundaccId(bodyRequest.getFundaccId());
		bodyReply.setCurrency(bodyRequest.getCurrency());
		
		for(String name :bodyRequest.getCapitalQuotaNameList())
		{
			bodyReply.putCapitalQuota(name, 5.0);
		}
			
		headerReply.setStatus(Header.Status.SUCCESS);
		reply.setHeader(headerReply);
		reply.addBody(bodyReply);
			
		responseObserver.onNext(reply.build());
		responseObserver.onCompleted();
	}
	
}
