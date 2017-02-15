package com.winsigns.investment.inventory;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winsigns.investment.grpc.CaptialService.CapitalOperatorMsg;
import com.winsigns.investment.grpc.CaptialService.MutilCapitalMsg;
import com.winsigns.investment.grpc.CaptialService.OneCapitalMsg;
import com.winsigns.investment.grpc.HeaderOuterClass.Header;
import com.winsigns.investment.grpc.capitalGrpc;
import com.winsigns.investment.grpc.model.CapitalOperatorOuterClass.CapitalOperator;
import com.winsigns.investment.inventory.capital.model.CapitalRepository;
import com.winsigns.investment.inventory.capital.model.ExtCapitalAccountCapital;
import com.winsigns.investment.inventory.capital.model.ExtCapitalAccountCapitalId;
import com.winsigns.investment.inventory.capital.model.ExtCapitalAccountRepository;
import com.winsigns.investment.inventory.capital.model.FundAccountCapitalDetail;
import com.winsigns.investment.inventory.capital.model.FundAccountCapitalDetailId;
import com.winsigns.investment.inventory.capital.model.FundAccountCapitalDetailQuota;
import com.winsigns.investment.frame.DataException;
import com.winsigns.investment.grpc.CapitalOuterClass.Capital;

import io.grpc.stub.StreamObserver;

@GRpcService
@EnableTransactionManagement
public class CapitalServiceImplement extends capitalGrpc.capitalImplBase{
	
	@Autowired
	private PlatformTransactionManager transcationManager;
	@Autowired
	EntityManagerFactory entityManagerFactory;
	
	@Autowired
	ExtCapitalAccountRepository extCapitalAccountRepository;
	
	@Autowired
	CapitalRepository capitalRepository;
	
	@Override
	public void queryCapital(OneCapitalMsg request, StreamObserver<MutilCapitalMsg> responseObserver) {
		
		//创建应答
		MutilCapitalMsg.Builder reply = MutilCapitalMsg.newBuilder();
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
	
	@Override
	@Transactional
	public void operatorCapital(CapitalOperatorMsg request, StreamObserver<CapitalOperatorMsg> responseObserver) {
		
//		EntityManager entityManager = entityManagerFactory.createEntityManager();
//		
//		entityManager.getTransaction().begin();
		//创建应答
		CapitalOperatorMsg.Builder reply = CapitalOperatorMsg.newBuilder();
		CapitalOperator capitalOperator = request.getBody();
		
		switch(capitalOperator.getCapitalOperator()){
			case IN:  {
				
				ExtCapitalAccountCapitalId sourceExtCapitalAccountCapitalId = new ExtCapitalAccountCapitalId();
				sourceExtCapitalAccountCapitalId.setExtCapitalAccountId(capitalOperator.getSourceAccount());
				sourceExtCapitalAccountCapitalId.setCurrency(capitalOperator.getCurrency());
				
				ExtCapitalAccountCapital sourceExtCapitalAccount = 
					extCapitalAccountRepository.findOne(sourceExtCapitalAccountCapitalId);
				if(sourceExtCapitalAccount != null){
					sourceExtCapitalAccount.setUnassignCapital(sourceExtCapitalAccount.getUnassignCapital() + capitalOperator.getCapitalChange());
				}
				else{
					sourceExtCapitalAccount = new ExtCapitalAccountCapital();
					
					sourceExtCapitalAccount.setExtCapitalAccountCapitalId(sourceExtCapitalAccountCapitalId);
					sourceExtCapitalAccount.setUnassignCapital(capitalOperator.getCapitalChange());
					
				}
				extCapitalAccountRepository.save(sourceExtCapitalAccount);
				
				sourceExtCapitalAccount = new ExtCapitalAccountCapital();
				sourceExtCapitalAccountCapitalId.setExtCapitalAccountId("sfdsdfsdfsdfsdfsdfsdfddddddddddddddddddddddddddddsdfsdfdsf");
				sourceExtCapitalAccount.setExtCapitalAccountCapitalId(sourceExtCapitalAccountCapitalId);
				
				extCapitalAccountRepository.save(sourceExtCapitalAccount);
				reply.setHeader(Header.newBuilder().setStatus(Header.Status.SUCCESS));
			}
				break;
			case OUT: {
				ExtCapitalAccountCapitalId sourceExtCapitalAccountCapitalId = new ExtCapitalAccountCapitalId();
				sourceExtCapitalAccountCapitalId.setExtCapitalAccountId(capitalOperator.getSourceAccount());
				sourceExtCapitalAccountCapitalId.setCurrency(capitalOperator.getCurrency());
				
				ExtCapitalAccountCapital sourceExtCapitalAccount = 
					capitalRepository.extCapitalAccountRepository.findOne(sourceExtCapitalAccountCapitalId);
				if(sourceExtCapitalAccount != null){
					
					if(sourceExtCapitalAccount.getUnassignCapital() < Math.abs(capitalOperator.getCapitalChange())){
						reply.setHeader(Header.newBuilder()
								.setStatus(Header.Status.FAIL)
								.setMessage("未分配资金不足，不可转出"));
					}
					else{				
						sourceExtCapitalAccount.setUnassignCapital(sourceExtCapitalAccount.getUnassignCapital() - Math.abs(capitalOperator.getCapitalChange()));
						capitalRepository.extCapitalAccountRepository.save(sourceExtCapitalAccount);
					}
				}
				else{
					reply.setHeader(Header.newBuilder()
							.setStatus(Header.Status.FAIL)
							.setMessage("未找到该账户"));
				}
				
			}
				break;
			case ALLOCATE_IN:{ 
				ExtCapitalAccountCapitalId sourceExtCapitalAccountCapitalId = new ExtCapitalAccountCapitalId();
				sourceExtCapitalAccountCapitalId.setExtCapitalAccountId(capitalOperator.getSourceAccount());
				sourceExtCapitalAccountCapitalId.setCurrency(capitalOperator.getCurrency());
				ExtCapitalAccountCapital sourceExtCapitalAccount = 
						capitalRepository.extCapitalAccountRepository.findOne(sourceExtCapitalAccountCapitalId);
				if(sourceExtCapitalAccount == null){
					reply.setHeader(Header.newBuilder()
							.setStatus(Header.Status.FAIL)
							.setMessage("源资金账户不存在"));
					break;
				}
				
				ExtCapitalAccountCapitalId matchExtCapitalAccountCapitalId = new ExtCapitalAccountCapitalId();
				matchExtCapitalAccountCapitalId.setExtCapitalAccountId(capitalOperator.getMatchAccount());
				matchExtCapitalAccountCapitalId.setCurrency(capitalOperator.getCurrency());
				ExtCapitalAccountCapital matchExtCapitalAccount = 
						capitalRepository.extCapitalAccountRepository.findOne(matchExtCapitalAccountCapitalId);				
				if(matchExtCapitalAccount == null){
					reply.setHeader(Header.newBuilder()
							.setStatus(Header.Status.FAIL)
							.setMessage("对手资金账户不存在"));
					break;
				}
				
				//源+ 对手-需要校验对手
				if(matchExtCapitalAccount.getUnassignCapital() < Math.abs(capitalOperator.getCapitalChange())){
					reply.setHeader(Header.newBuilder()
							.setStatus(Header.Status.FAIL)
							.setMessage("未分配资金不足，不可转出"));
				}
				else{				
					matchExtCapitalAccount.setUnassignCapital(matchExtCapitalAccount.getUnassignCapital() - Math.abs(capitalOperator.getCapitalChange()));
					sourceExtCapitalAccount.setUnassignCapital(sourceExtCapitalAccount.getUnassignCapital() + Math.abs(capitalOperator.getCapitalChange()));
					capitalRepository.extCapitalAccountRepository.save(matchExtCapitalAccount);
					capitalRepository.extCapitalAccountRepository.save(sourceExtCapitalAccount);
				}
			}
				break;
			case ALLOCATE_OUT:{ 
				ExtCapitalAccountCapitalId sourceExtCapitalAccountCapitalId = new ExtCapitalAccountCapitalId();
				sourceExtCapitalAccountCapitalId.setExtCapitalAccountId(capitalOperator.getSourceAccount());
				sourceExtCapitalAccountCapitalId.setCurrency(capitalOperator.getCurrency());
				ExtCapitalAccountCapital sourceExtCapitalAccount = 
						capitalRepository.extCapitalAccountRepository.findOne(sourceExtCapitalAccountCapitalId);
				if(sourceExtCapitalAccount == null){
					reply.setHeader(Header.newBuilder()
							.setStatus(Header.Status.FAIL)
							.setMessage("源资金账户不存在"));
					break;
				}
				
				ExtCapitalAccountCapitalId matchExtCapitalAccountCapitalId = new ExtCapitalAccountCapitalId();
				matchExtCapitalAccountCapitalId.setExtCapitalAccountId(capitalOperator.getMatchAccount());
				matchExtCapitalAccountCapitalId.setCurrency(capitalOperator.getCurrency());
				ExtCapitalAccountCapital matchExtCapitalAccount = 
						capitalRepository.extCapitalAccountRepository.findOne(matchExtCapitalAccountCapitalId);
					
				if(matchExtCapitalAccount == null){
					reply.setHeader(Header.newBuilder()
							.setStatus(Header.Status.FAIL)
							.setMessage("对手资金账户不存在"));
					break;
				}
				
				//源- 对手+ 需要校验源
				if(sourceExtCapitalAccount.getUnassignCapital() < Math.abs(capitalOperator.getCapitalChange())){
					reply.setHeader(Header.newBuilder()
							.setStatus(Header.Status.FAIL)
							.setMessage("未分配资金不足，不可转出"));
				}
				else{				
					matchExtCapitalAccount.setUnassignCapital(matchExtCapitalAccount.getUnassignCapital() + Math.abs(capitalOperator.getCapitalChange()));
					sourceExtCapitalAccount.setUnassignCapital(sourceExtCapitalAccount.getUnassignCapital() - Math.abs(capitalOperator.getCapitalChange()));
					capitalRepository.extCapitalAccountRepository.save(matchExtCapitalAccount);
					capitalRepository.extCapitalAccountRepository.save(sourceExtCapitalAccount);
				}
			}
				break;
			case ASSIGN_IN:{
				List<String> quotaNameList = new ArrayList<String>();
				quotaNameList.add("cash");
				quotaNameList.add("avail_capital");
				quotaNameList.add("desirable_capital");
				quotaNameList.add("float_cash");
				quotaNameList.add("float_avail_capital");
				quotaNameList.add("float_desirable_capital");
				
				ExtCapitalAccountCapitalId matchExtCapitalAccountCapitalId = new ExtCapitalAccountCapitalId();
				matchExtCapitalAccountCapitalId.setExtCapitalAccountId(capitalOperator.getMatchAccount());
				matchExtCapitalAccountCapitalId.setCurrency(capitalOperator.getCurrency());
				ExtCapitalAccountCapital matchExtCapitalAccount = 
						capitalRepository.extCapitalAccountRepository.findOne(matchExtCapitalAccountCapitalId);
				if(matchExtCapitalAccount == null){
					reply.setHeader(Header.newBuilder()
							.setStatus(Header.Status.FAIL)
							.setMessage("对手资金账户不存在"));
					break;
				}
				else{
					if(matchExtCapitalAccount.getUnassignCapital() < Math.abs(capitalOperator.getCapitalChange())){
						reply.setHeader(Header.newBuilder()
								.setStatus(Header.Status.FAIL)
								.setMessage("未分配资金不足，不可分配"));
						break;
					}	
				}
				matchExtCapitalAccount.setUnassignCapital(matchExtCapitalAccount.getUnassignCapital() - Math.abs(capitalOperator.getCapitalChange()));
				capitalRepository.extCapitalAccountRepository.save(matchExtCapitalAccount);
				
				FundAccountCapitalDetailId sourceFundAccountCapitalDetailId = new FundAccountCapitalDetailId();
				sourceFundAccountCapitalDetailId.setFundAccountId(capitalOperator.getSourceAccount());
				sourceFundAccountCapitalDetailId.setExtCapitalAccountId(capitalOperator.getMatchAccount());
				sourceFundAccountCapitalDetailId.setCurrency(capitalOperator.getCurrency());
				
				FundAccountCapitalDetail sourceFundAccountCapitalDetail = 
						capitalRepository.fundAccountCapitalDetailRepository.findOne(sourceFundAccountCapitalDetailId);				
				
				if(sourceFundAccountCapitalDetail != null){
					
					List<FundAccountCapitalDetailQuota> fundAccountCapitalDetailQuotas = 
							capitalRepository.fundAccountCapitalDetailQuotaRepository.findByFundAccountCapitalDetailAndNameIn(sourceFundAccountCapitalDetail, quotaNameList);
					
					if(fundAccountCapitalDetailQuotas.size() != 0){ //当sourceFundAccountCapitalDetail存在的时候，一定不为0
						for(FundAccountCapitalDetailQuota fundAccountCapitalDetailQuota : fundAccountCapitalDetailQuotas){
							fundAccountCapitalDetailQuota.setValue(fundAccountCapitalDetailQuota.getValue() + Math.abs(capitalOperator.getCapitalChange()));
						}
						capitalRepository.fundAccountCapitalDetailQuotaRepository.save(fundAccountCapitalDetailQuotas);
					}
					else{
						
					}
					
				}
				else{
					sourceFundAccountCapitalDetail = new FundAccountCapitalDetail();
					sourceFundAccountCapitalDetail.setFundAccountCapitalDetailId(sourceFundAccountCapitalDetailId);
					
					for(String quota :quotaNameList){										
						FundAccountCapitalDetailQuota fundAccountCapitalDetailQuota = new FundAccountCapitalDetailQuota();
						fundAccountCapitalDetailQuota.setQuota(sourceFundAccountCapitalDetail);
						fundAccountCapitalDetailQuota.setName(quota);
						fundAccountCapitalDetailQuota.setValue(Math.abs(capitalOperator.getCapitalChange()));
						sourceFundAccountCapitalDetail.getQuotas().add(fundAccountCapitalDetailQuota);
					}

					capitalRepository.fundAccountCapitalDetailRepository.save(sourceFundAccountCapitalDetail);
				}					
			}
				break;
			case ASSIGN_OUT:{
				//事务开始
//				DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//				def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);  
//				def.setTimeout(30);
//				//事务状态
//				TransactionStatus status = transcationManager.getTransaction(def);
				try{			
					List<String> quotaNameList = new ArrayList<String>();
					quotaNameList.add("cash");
					quotaNameList.add("avail_capital");
					quotaNameList.add("desirable_capital");
					quotaNameList.add("float_cash");
					quotaNameList.add("float_avail_capital");
					quotaNameList.add("float_desirable_capital");
					
					ExtCapitalAccountCapitalId matchExtCapitalAccountCapitalId = new ExtCapitalAccountCapitalId();
					matchExtCapitalAccountCapitalId.setExtCapitalAccountId(capitalOperator.getMatchAccount());
					matchExtCapitalAccountCapitalId.setCurrency(capitalOperator.getCurrency());
					ExtCapitalAccountCapital matchExtCapitalAccount = 
							capitalRepository.extCapitalAccountRepository.findOne(matchExtCapitalAccountCapitalId);
					if(matchExtCapitalAccount == null){
						throw new DataException("对手资金账户不存在");
					}
					matchExtCapitalAccount.setUnassignCapital(matchExtCapitalAccount.getUnassignCapital() + Math.abs(capitalOperator.getCapitalChange()));
					capitalRepository.extCapitalAccountRepository.save(matchExtCapitalAccount);
					
					FundAccountCapitalDetailId sourceFundAccountCapitalDetailId = new FundAccountCapitalDetailId();
					sourceFundAccountCapitalDetailId.setFundAccountId(capitalOperator.getSourceAccount());
					sourceFundAccountCapitalDetailId.setExtCapitalAccountId(capitalOperator.getMatchAccount());
					sourceFundAccountCapitalDetailId.setCurrency(capitalOperator.getCurrency());
					
					FundAccountCapitalDetail sourceFundAccountCapitalDetail = 
							capitalRepository.fundAccountCapitalDetailRepository.findOne(sourceFundAccountCapitalDetailId);				
					
					if(sourceFundAccountCapitalDetail != null){
						
						FundAccountCapitalDetailQuota qFundAccountCapitalDetailQuota = 
								capitalRepository.fundAccountCapitalDetailQuotaRepository.findByFundAccountCapitalDetailAndName(sourceFundAccountCapitalDetail, "cash");
						if(qFundAccountCapitalDetailQuota != null){
							if(qFundAccountCapitalDetailQuota.getValue() < Math.abs(capitalOperator.getCapitalChange())){
								throw new DataException("现金不足，不可归还");
							}
						}
						else{
							throw new DataException("源产品账户不存在");
						}
						
						List<FundAccountCapitalDetailQuota> fundAccountCapitalDetailQuotas = 
								capitalRepository.fundAccountCapitalDetailQuotaRepository.findByFundAccountCapitalDetailAndNameIn(sourceFundAccountCapitalDetail, quotaNameList);
						
						if(fundAccountCapitalDetailQuotas.size() != 0){ //当sourceFundAccountCapitalDetail存在的时候，一定不为0
							for(FundAccountCapitalDetailQuota fundAccountCapitalDetailQuota : fundAccountCapitalDetailQuotas){
								fundAccountCapitalDetailQuota.setValue(fundAccountCapitalDetailQuota.getValue() - Math.abs(capitalOperator.getCapitalChange()));
							}
							capitalRepository.fundAccountCapitalDetailQuotaRepository.save(fundAccountCapitalDetailQuotas);
						}
						else{
							
						}
						
					}
					else{
						throw new DataException("源产品账户不存在");
					}	
//					transcationManager.commit(status);
				}
				catch(DataException e){
					reply.setHeader(Header.newBuilder()
							.setStatus(Header.Status.FAIL)
							.setMessage(e.getMessage()));
					//TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					throw new RuntimeException();
				}				
			}
				break;			
			case TRANSFER:
				break;
			case UNRECOGNIZED:
				break;
			default:
				break;
		}
		
		//entityManager.getTransaction().commit();
		
		responseObserver.onNext(reply.build());
		responseObserver.onCompleted();
	}	
}
