package com.winsigns.investment.fundService.service;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.lognet.springboot.grpc.GRpcService;

import com.winsigns.investment.fundService.model.Fund;
import com.winsigns.investment.fundService.model.FundRepository;
import com.winsigns.investment.grpc.FundGrpc;
import com.winsigns.investment.grpc.FundOuterClass.AddFundResponse;
import com.winsigns.investment.grpc.FundOuterClass.FundMessage;
import com.winsigns.investment.grpc.FundOuterClass.GetFundsRequest;
import com.winsigns.investment.grpc.FundOuterClass.GetFundsResponse;
import com.winsigns.investment.grpc.FundOuterClass.ResponseHeader;

import io.grpc.stub.StreamObserver;

@GRpcService
public class FundService extends FundGrpc.FundImplBase{
	@Resource
	FundRepository fundRepository;
	
	@Override
	public void addFund(FundMessage request, StreamObserver<AddFundResponse> responseObserver) {
		Fund temp = new Fund();
		temp.setCode(request.getCode());
		temp.setName(request.getName());
		temp.setShortName(request.getShortName());
		
		Fund fund = fundRepository.save(temp);
		
		AddFundResponse.Builder response = AddFundResponse.newBuilder()
				.setHeader(ResponseHeader.newBuilder()
						.setErrorId(0)
						.setErrorMsg("")
						.build())
				.setFund(FundMessage.newBuilder()
						.setCode(fund.getCode())
						.setName(fund.getName())
						.setShortName(fund.getShortName())
						.build());

		
		responseObserver.onNext(response.build());
		responseObserver.onCompleted();
	}

	@Override
	public void getFunds(GetFundsRequest request, StreamObserver<GetFundsResponse> responseObserver) {
		List<Fund> funds;
		if (request.getCode().isEmpty()){
			funds = fundRepository.findAll();
		}else{
			funds = (List<Fund>) fundRepository.findByCode(request.getCode());
		}
		
		GetFundsResponse.Builder response = GetFundsResponse.newBuilder();
		
		response.setHeader(ResponseHeader.newBuilder()
				.setErrorId(0)
				.setErrorMsg("")
				.build());
		for(Fund fund : funds){
			response.addFunds(FundMessage.newBuilder()
					.setCode(fund.getCode())
					.setName(fund.getName())
					.setShortName(fund.getShortName())
					.build());
		}
		
		responseObserver.onNext(response.build());
		responseObserver.onCompleted();
	}
}
