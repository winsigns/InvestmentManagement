package com.winsigns.investment.inventory.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.winsigns.investment.frame.BusinessException;
import com.winsigns.investment.frame.Header;
import com.winsigns.investment.inventory.service.CapitalOperatorType;
import com.winsigns.investment.inventory.service.CapitalService;


@RestController
@RequestMapping("/Capital")
public class CapitalServiceControl {
	
	@Autowired
	CapitalService capitalService;
	
	@RequestMapping("/InCapitalAccount")
	public Header inCapitalAccount(
			@RequestParam(value="capital_account", required=true) String capitalAccount,
			@RequestParam(value="currency", required=true) String currency,
			@RequestParam(value="capital_change", required=true) Double capitalChange){
		try{
			capitalService.operatorCapital(CapitalOperatorType.IN, capitalAccount, null, null, null, currency, capitalChange);
		}
		catch(BusinessException e){
			return new Header().setHeader(-1L, e.getMessage());
		}
		return new Header();
	}
	
	@RequestMapping("/OutCapitalAccount")
	public Header outCapitalAccount(
			@RequestParam(value="capital_account", required=true) String capitalAccount,
			@RequestParam(value="currency", required=true) String currency,
			@RequestParam(value="capital_change", required=true) Double capitalChange){
		try{
			capitalService.operatorCapital(CapitalOperatorType.OUT, null, null, capitalAccount, null, currency, capitalChange);
		}
		catch(BusinessException e){
			return new Header().setHeader(-1L, e.getMessage());
		}
		return new Header();
				
	}
	
	@RequestMapping("/AllocateCapitalAccountFromCapitalAccount")
	public Header allocateCapitalAccountFromCapitalAccount(
			@RequestParam(value="dst_capital_account", required=true) String dstCapitalAccount,
			@RequestParam(value="src_capital_account", required=true) String srcCapitalAccount,
			@RequestParam(value="currency", required=true) String currency,
			@RequestParam(value="capital_change", required=true) Double capitalChange){
		try{
			capitalService.operatorCapital(CapitalOperatorType.ALLOCATE, dstCapitalAccount, null, srcCapitalAccount, null, currency, capitalChange);
		}
		catch(BusinessException e){
			return new Header().setHeader(-1L, e.getMessage());
		}
		return new Header();
	}
	
	@RequestMapping("/AssignInFundAccountFromCapitalAccount")
	public Header assignInFundAccountFromCapitalAccount(
			@RequestParam(value="dst_capital_account", required=true) String dstCapitalAccount,
			@RequestParam(value="dst_fund_account", required=true) String dstFundAccount,		
			@RequestParam(value="src_capital_account", required=true) String srcCapitalAccount,
			@RequestParam(value="currency", required=true) String currency,
			@RequestParam(value="capital_change", required=true) Double capitalChange){
		try{
			capitalService.operatorCapital(CapitalOperatorType.ASSIGN_IN, dstCapitalAccount, dstFundAccount, srcCapitalAccount, null, currency, capitalChange);
		}
		catch(BusinessException e){
			return new Header().setHeader(-1L, e.getMessage());
		}
		return new Header();
	}
	
	@RequestMapping("/AssignOutFundAccountFromCapitalAccount")
	public Header assignOutFundAccountFromCapitalAccount(
			@RequestParam(value="dst_capital_account", required=true) String dstCapitalAccount,
			@RequestParam(value="src_capital_account", required=true) String srcCapitalAccount,
			@RequestParam(value="src_fund_account", required=true) String srcFundAccount,	
			@RequestParam(value="currency", required=true) String currency,
			@RequestParam(value="capital_change", required=true) Double capitalChange){
		try{
			capitalService.operatorCapital(CapitalOperatorType.ASSIGN_OUT, dstCapitalAccount, null, srcCapitalAccount, srcFundAccount, currency, capitalChange);
		}
		catch(BusinessException e){
			return new Header().setHeader(-1L, e.getMessage());
		}
		return new Header();
	}
	
	@RequestMapping("/TransferFundAccountFromFundAccount")
	public Header transferFundAccountFromFundAccount(
			@RequestParam(value="dst_capital_account", required=true) String dstCapitalAccount,
			@RequestParam(value="dst_fund_account", required=true) String dstFundAccount,	
			@RequestParam(value="src_capital_account", required=true) String srcCapitalAccount,
			@RequestParam(value="src_fund_account", required=true) String srcFundAccount,	
			@RequestParam(value="currency", required=true) String currency,
			@RequestParam(value="capital_change", required=true) Double capitalChange){
		try{
			capitalService.operatorCapital(CapitalOperatorType.TRANSFER, dstCapitalAccount, dstFundAccount, srcCapitalAccount, srcFundAccount, currency, capitalChange);
		}
		catch(BusinessException e){
			return new Header().setHeader(-1L, e.getMessage());
		}
		return new Header();
	}
		
}
