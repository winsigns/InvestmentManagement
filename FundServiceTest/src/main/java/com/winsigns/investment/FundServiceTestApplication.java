package com.winsigns.investment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.winsigns.investment.grpc.FundGrpc;
import com.winsigns.investment.grpc.FundGrpc.FundBlockingStub;
import com.winsigns.investment.grpc.FundOuterClass.GetFundsRequest;
import com.winsigns.investment.grpc.FundOuterClass.GetFundsResponse;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@RestController
class FundServiceTester{
	@Autowired
	private LoadBalancerClient loadBalancer;
	
	@RequestMapping("/funds")
	public String run(String[] args){
		ServiceInstance fundServiceTarget = loadBalancer.choose("fund-service-6565");

		ManagedChannel channel = ManagedChannelBuilder
				.forAddress(fundServiceTarget.getHost(), fundServiceTarget.getPort()).usePlaintext(true)
				.build();
		FundBlockingStub stub = FundGrpc.newBlockingStub(channel);
		GetFundsResponse response = stub.getFunds(GetFundsRequest.newBuilder()
				.setCode("")
				.build());
		//System.out.println(response.toString());
		return response.toString();
	}
}

@SpringBootApplication
public class FundServiceTestApplication {
	
	

	public static void main(String[] args) {
		SpringApplication.run(FundServiceTestApplication.class, args);
	}
}
