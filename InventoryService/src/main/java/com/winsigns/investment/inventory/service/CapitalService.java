package com.winsigns.investment.inventory.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winsigns.investment.frame.BusinessException;
import com.winsigns.investment.inventory.model.capital.CapitalRepository;
import com.winsigns.investment.inventory.model.capital.ExtCapitalAccountCapital;
import com.winsigns.investment.inventory.model.capital.ExtCapitalAccountCapitalId;
import com.winsigns.investment.inventory.model.capital.FundAccountCapitalDetail;
import com.winsigns.investment.inventory.model.capital.FundAccountCapitalDetailId;
import com.winsigns.investment.inventory.model.capital.FundAccountCapitalDetailQuota;

@Service
public class CapitalService {
	
	@Autowired
	CapitalRepository capitalRepository;
	
	public CapitalService() {
		// TODO Auto-generated constructor stub
	}
	
	//将资金从源账户转到目标账户
	@Transactional
	public String operatorCapital(CapitalOperatorType capitalOperatorType,
			String dstCapitalAccount,
			String dstFundAccount,
			String srcCapitalAccount,
			String srcFundAccount,
			String currency,
			Double capitalChange) throws BusinessException {
		
		//TODO field check
		
		switch(capitalOperatorType){
		case IN:  {
			inCapitalAccount(dstCapitalAccount,currency,capitalChange);	
		}
			break;
		case OUT:{
			outCapitalAccount(srcCapitalAccount,currency,capitalChange);
		}
			break;
		case ALLOCATE:{
			allocateCapitalAccountFromCapitalAccount(dstCapitalAccount, srcCapitalAccount, currency, capitalChange);
		}
			break;
		case ASSIGN_IN:{
			assignInFundAccountFromCapitalAccount(dstCapitalAccount, dstFundAccount, srcCapitalAccount, currency, capitalChange);
		}
			break;
		case ASSIGN_OUT:{
			assignOutCapitalAccountFromFundAccount(dstCapitalAccount, srcCapitalAccount, srcFundAccount, currency, capitalChange);
		}
			break;
		case TRANSFER:{
			transferFundAccountFromFundAccount(dstCapitalAccount, dstFundAccount, srcCapitalAccount, srcFundAccount, currency, capitalChange);
		}
			break;
		default:
			break;
		}
		return "";
	}
		
	
	//从资金封闭圈外向指定资金账户转入资金
	private void inCapitalAccount(String capitalAccount,
			String currency,
			Double capitalChange){
		ExtCapitalAccountCapitalId sourceExtCapitalAccountCapitalId = new ExtCapitalAccountCapitalId();
		sourceExtCapitalAccountCapitalId.setExtCapitalAccountId(capitalAccount);
		sourceExtCapitalAccountCapitalId.setCurrency(currency);
		
		ExtCapitalAccountCapital sourceExtCapitalAccount = 
				capitalRepository.extCapitalAccountRepository.findOne(sourceExtCapitalAccountCapitalId);
		if(sourceExtCapitalAccount != null){
			sourceExtCapitalAccount.setUnassignCapital(sourceExtCapitalAccount.getUnassignCapital() + capitalChange);
		}
		else{
			sourceExtCapitalAccount = new ExtCapitalAccountCapital();
			
			sourceExtCapitalAccount.setExtCapitalAccountCapitalId(sourceExtCapitalAccountCapitalId);
			sourceExtCapitalAccount.setUnassignCapital(capitalChange);
			
		}
		capitalRepository.extCapitalAccountRepository.save(sourceExtCapitalAccount);
	}
	
	private void outCapitalAccount(String capitalAccount,
			String currency,
			Double capitalChange){
		ExtCapitalAccountCapitalId sourceExtCapitalAccountCapitalId = new ExtCapitalAccountCapitalId();
		sourceExtCapitalAccountCapitalId.setExtCapitalAccountId(capitalAccount);
		sourceExtCapitalAccountCapitalId.setCurrency(currency);
		
		ExtCapitalAccountCapital sourceExtCapitalAccount = 
			capitalRepository.extCapitalAccountRepository.findOne(sourceExtCapitalAccountCapitalId);
		if(sourceExtCapitalAccount != null){	
			if(sourceExtCapitalAccount.getUnassignCapital() < Math.abs(capitalChange)){
				//throw new BusinessException("未分配资金不足，不可转出");
			}				
			sourceExtCapitalAccount.setUnassignCapital(sourceExtCapitalAccount.getUnassignCapital() - Math.abs(capitalChange));
			capitalRepository.extCapitalAccountRepository.save(sourceExtCapitalAccount);
		}
		else{
			throw new BusinessException("未找到该账户");		
		}
	}
	
	//从源资金账户转到目标资金账户
	private void allocateCapitalAccountFromCapitalAccount(String dstCapitalAccount,
			String srcCapitalAccount,
			String currency,
			Double capitalChange){
		ExtCapitalAccountCapitalId sourceExtCapitalAccountCapitalId = new ExtCapitalAccountCapitalId();
		sourceExtCapitalAccountCapitalId.setExtCapitalAccountId(srcCapitalAccount);
		sourceExtCapitalAccountCapitalId.setCurrency(currency);
		ExtCapitalAccountCapital sourceExtCapitalAccount = 
				capitalRepository.extCapitalAccountRepository.findOne(sourceExtCapitalAccountCapitalId);
		if(sourceExtCapitalAccount == null){
			throw new BusinessException("源资金账户不存在");
		}
		
		if(sourceExtCapitalAccount.getUnassignCapital() < Math.abs(capitalChange)){
			throw new BusinessException("未分配资金不足，不可转出");
		}
		sourceExtCapitalAccount.setUnassignCapital(sourceExtCapitalAccount.getUnassignCapital() - Math.abs(capitalChange));
		capitalRepository.extCapitalAccountRepository.save(sourceExtCapitalAccount);
		
		ExtCapitalAccountCapitalId matchExtCapitalAccountCapitalId = new ExtCapitalAccountCapitalId();
		matchExtCapitalAccountCapitalId.setExtCapitalAccountId(dstCapitalAccount);
		matchExtCapitalAccountCapitalId.setCurrency(currency);
		ExtCapitalAccountCapital matchExtCapitalAccount = 
				capitalRepository.extCapitalAccountRepository.findOne(matchExtCapitalAccountCapitalId);				
		if(matchExtCapitalAccount == null){
			inCapitalAccount(dstCapitalAccount, currency, capitalChange);
		}
		else{				
			matchExtCapitalAccount.setUnassignCapital(matchExtCapitalAccount.getUnassignCapital() + Math.abs(capitalChange));		
			capitalRepository.extCapitalAccountRepository.save(matchExtCapitalAccount);			
		}
	}
	
	private List<String> quotaNameList = new ArrayList<String>(){/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
		add("cash");
		add("avail_capital");
		add("desirable_capital");
		add("float_cash");
		add("float_avail_capital");
		add("float_desirable_capital");
	}};
	
	//从资金账户转入到产品账户
	private void assignInFundAccountFromCapitalAccount(String capitalAccount,
			String fundAccount,		
			String srcCapitalAccount,
			String currency,
			Double capitalChange){
			
		ExtCapitalAccountCapitalId sourceExtCapitalAccountCapitalId = new ExtCapitalAccountCapitalId();
		sourceExtCapitalAccountCapitalId.setExtCapitalAccountId(srcCapitalAccount);
		sourceExtCapitalAccountCapitalId.setCurrency(currency);
		ExtCapitalAccountCapital sourceExtCapitalAccount = 
				capitalRepository.extCapitalAccountRepository.findOne(sourceExtCapitalAccountCapitalId);
		if(sourceExtCapitalAccount == null){
			throw new BusinessException("源资金账户不存在");
		}
		else{
			if(sourceExtCapitalAccount.getUnassignCapital() < Math.abs(capitalChange)){
				throw new BusinessException("未分配资金不足，不可分配");
			}	
		}
		sourceExtCapitalAccount.setUnassignCapital(sourceExtCapitalAccount.getUnassignCapital() - Math.abs(capitalChange));
		capitalRepository.extCapitalAccountRepository.save(sourceExtCapitalAccount);
		
		FundAccountCapitalDetailId dstFundAccountCapitalDetailId = new FundAccountCapitalDetailId();
		dstFundAccountCapitalDetailId.setFundAccountId(fundAccount);
		dstFundAccountCapitalDetailId.setExtCapitalAccountId(capitalAccount);
		dstFundAccountCapitalDetailId.setCurrency(currency);
		
		FundAccountCapitalDetail dstFundAccountCapitalDetail = 
				capitalRepository.fundAccountCapitalDetailRepository.findOne(dstFundAccountCapitalDetailId);				
		
		if(dstFundAccountCapitalDetail != null){
			
			List<FundAccountCapitalDetailQuota> fundAccountCapitalDetailQuotas = 
					capitalRepository.fundAccountCapitalDetailQuotaRepository.findByFundAccountCapitalDetailAndNameIn(dstFundAccountCapitalDetail, quotaNameList);
			
			if(fundAccountCapitalDetailQuotas.size() != 0){ //当sourceFundAccountCapitalDetail存在的时候，一定不为0
				for(FundAccountCapitalDetailQuota fundAccountCapitalDetailQuota : fundAccountCapitalDetailQuotas){
					fundAccountCapitalDetailQuota.setValue(fundAccountCapitalDetailQuota.getValue() + Math.abs(capitalChange));
				}
				capitalRepository.fundAccountCapitalDetailQuotaRepository.save(fundAccountCapitalDetailQuotas);
			}
			else{
				
			}
			
		}
		else{
			dstFundAccountCapitalDetail = new FundAccountCapitalDetail();
			dstFundAccountCapitalDetail.setFundAccountCapitalDetailId(dstFundAccountCapitalDetailId);
			
			for(String quota :quotaNameList){										
				FundAccountCapitalDetailQuota fundAccountCapitalDetailQuota = new FundAccountCapitalDetailQuota();
				fundAccountCapitalDetailQuota.setQuota(dstFundAccountCapitalDetail);
				fundAccountCapitalDetailQuota.setName(quota);
				fundAccountCapitalDetailQuota.setValue(Math.abs(capitalChange));
				dstFundAccountCapitalDetail.getQuotas().add(fundAccountCapitalDetailQuota);
			}

			capitalRepository.fundAccountCapitalDetailRepository.save(dstFundAccountCapitalDetail);
		}					
	}
	
	private void assignOutCapitalAccountFromFundAccount(String dstCapitalAccount,
			String srcCapitalAccount,
			String srcFundAccount,			
			String currency,
			Double capitalChange){
			
		ExtCapitalAccountCapitalId sourceExtCapitalAccountCapitalId = new ExtCapitalAccountCapitalId();
		sourceExtCapitalAccountCapitalId.setExtCapitalAccountId(dstCapitalAccount);
		sourceExtCapitalAccountCapitalId.setCurrency(currency);
		ExtCapitalAccountCapital sourceExtCapitalAccount = 
				capitalRepository.extCapitalAccountRepository.findOne(sourceExtCapitalAccountCapitalId);
		if(sourceExtCapitalAccount == null){
			throw new BusinessException("对手资金账户不存在");
		}
		sourceExtCapitalAccount.setUnassignCapital(sourceExtCapitalAccount.getUnassignCapital() + Math.abs(capitalChange));
		capitalRepository.extCapitalAccountRepository.save(sourceExtCapitalAccount);
		
		FundAccountCapitalDetailId sourceFundAccountCapitalDetailId = new FundAccountCapitalDetailId();
		sourceFundAccountCapitalDetailId.setFundAccountId(srcFundAccount);
		sourceFundAccountCapitalDetailId.setExtCapitalAccountId(srcCapitalAccount);
		sourceFundAccountCapitalDetailId.setCurrency(currency);
		
		FundAccountCapitalDetail sourceFundAccountCapitalDetail = 
				capitalRepository.fundAccountCapitalDetailRepository.findOne(sourceFundAccountCapitalDetailId);				
		
		if(sourceFundAccountCapitalDetail != null){		
			FundAccountCapitalDetailQuota qFundAccountCapitalDetailQuota = 
					capitalRepository.fundAccountCapitalDetailQuotaRepository.findByFundAccountCapitalDetailAndName(sourceFundAccountCapitalDetail, "cash");
			if(qFundAccountCapitalDetailQuota != null){
				if(qFundAccountCapitalDetailQuota.getValue() < Math.abs(capitalChange)){
					throw new BusinessException("现金不足，不可归还");
				}
			}
			else{
				throw new BusinessException("源产品账户不存在");
			}
			
			List<FundAccountCapitalDetailQuota> fundAccountCapitalDetailQuotas = 
					capitalRepository.fundAccountCapitalDetailQuotaRepository.findByFundAccountCapitalDetailAndNameIn(sourceFundAccountCapitalDetail, quotaNameList);
			
			if(fundAccountCapitalDetailQuotas.size() != 0){ //当sourceFundAccountCapitalDetail存在的时候，一定不为0
				for(FundAccountCapitalDetailQuota fundAccountCapitalDetailQuota : fundAccountCapitalDetailQuotas){
					fundAccountCapitalDetailQuota.setValue(fundAccountCapitalDetailQuota.getValue() - Math.abs(capitalChange));
				}
				capitalRepository.fundAccountCapitalDetailQuotaRepository.save(fundAccountCapitalDetailQuotas);
			}
			else{
				
			}
			
		}
		else{
			throw new BusinessException("源产品账户不存在");
		}			
	}
	
	//从源资金账户转到目标资金账户
	private void transferFundAccountFromFundAccount(String dstCapitalAccount,
			String dstFundAccount,
			String srcCapitalAccount,
			String srcFundAccount,
			String currency,
			Double capitalChange){
		FundAccountCapitalDetailId sourceFundAccountCapitalDetailId = new FundAccountCapitalDetailId();
		sourceFundAccountCapitalDetailId.setFundAccountId(srcFundAccount);
		sourceFundAccountCapitalDetailId.setExtCapitalAccountId(srcCapitalAccount);
		sourceFundAccountCapitalDetailId.setCurrency(currency);
		
		FundAccountCapitalDetail sourceFundAccountCapitalDetail = 
				capitalRepository.fundAccountCapitalDetailRepository.findOne(sourceFundAccountCapitalDetailId);				
		
		if(sourceFundAccountCapitalDetail == null){	
			throw new BusinessException("源产品账户不存在");
		}
		
		FundAccountCapitalDetailQuota qFundAccountCapitalDetailQuota = 
				capitalRepository.fundAccountCapitalDetailQuotaRepository.findByFundAccountCapitalDetailAndName(sourceFundAccountCapitalDetail, "cash");
		if(qFundAccountCapitalDetailQuota == null){
			throw new BusinessException("源产品账户不存在");
		}
		if(qFundAccountCapitalDetailQuota.getValue() < Math.abs(capitalChange)){
			throw new BusinessException("现金不足，不可让渡");
		}
		
		List<FundAccountCapitalDetailQuota> fundAccountCapitalDetailQuotas = 
				capitalRepository.fundAccountCapitalDetailQuotaRepository.findByFundAccountCapitalDetailAndNameIn(sourceFundAccountCapitalDetail, quotaNameList);
		
		if(fundAccountCapitalDetailQuotas.size() != 0){ //当sourceFundAccountCapitalDetail存在的时候，一定不为0
			for(FundAccountCapitalDetailQuota fundAccountCapitalDetailQuota : fundAccountCapitalDetailQuotas){
				fundAccountCapitalDetailQuota.setValue(fundAccountCapitalDetailQuota.getValue() - Math.abs(capitalChange));
			}
			capitalRepository.fundAccountCapitalDetailQuotaRepository.save(fundAccountCapitalDetailQuotas);
		}
		else{
			
		}
		
		FundAccountCapitalDetailId dstFundAccountCapitalDetailId = new FundAccountCapitalDetailId();
		dstFundAccountCapitalDetailId.setFundAccountId(dstFundAccount);
		dstFundAccountCapitalDetailId.setExtCapitalAccountId(dstCapitalAccount);
		dstFundAccountCapitalDetailId.setCurrency(currency);
		
		FundAccountCapitalDetail dstFundAccountCapitalDetail = 
				capitalRepository.fundAccountCapitalDetailRepository.findOne(dstFundAccountCapitalDetailId);				
		
		if(dstFundAccountCapitalDetail != null){		
			fundAccountCapitalDetailQuotas = 
					capitalRepository.fundAccountCapitalDetailQuotaRepository.findByFundAccountCapitalDetailAndNameIn(dstFundAccountCapitalDetail, quotaNameList);
			
			if(fundAccountCapitalDetailQuotas.size() != 0){ //当sourceFundAccountCapitalDetail存在的时候，一定不为0
				for(FundAccountCapitalDetailQuota fundAccountCapitalDetailQuota : fundAccountCapitalDetailQuotas){
					fundAccountCapitalDetailQuota.setValue(fundAccountCapitalDetailQuota.getValue() + Math.abs(capitalChange));
				}
				capitalRepository.fundAccountCapitalDetailQuotaRepository.save(fundAccountCapitalDetailQuotas);
			}
			else{
				
			}		
		}
		else{
			dstFundAccountCapitalDetail = new FundAccountCapitalDetail();
			dstFundAccountCapitalDetail.setFundAccountCapitalDetailId(dstFundAccountCapitalDetailId);
			
			for(String quota :quotaNameList){										
				FundAccountCapitalDetailQuota fundAccountCapitalDetailQuota = new FundAccountCapitalDetailQuota();
				fundAccountCapitalDetailQuota.setQuota(dstFundAccountCapitalDetail);
				fundAccountCapitalDetailQuota.setName(quota);
				fundAccountCapitalDetailQuota.setValue(Math.abs(capitalChange));
				dstFundAccountCapitalDetail.getQuotas().add(fundAccountCapitalDetailQuota);
			}

			capitalRepository.fundAccountCapitalDetailRepository.save(dstFundAccountCapitalDetail);
		}
		
						
	}
}
