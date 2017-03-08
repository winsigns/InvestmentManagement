package com.winsigns.investment.webgateway.resource;

import com.winsigns.investment.webgateway.hal.HALResponse;
import com.winsigns.investment.webgateway.model.Fund;

public class FundResource extends HALResponse<Fund> {

		  public FundResource(Fund fund) {
			  super(fund);
		  } 
	  }
