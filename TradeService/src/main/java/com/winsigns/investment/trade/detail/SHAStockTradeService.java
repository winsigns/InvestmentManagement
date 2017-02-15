package com.winsigns.investment.trade.detail;

import com.winsigns.investment.grpc.CapitalOuterClass.Capital;
import com.winsigns.investment.grpc.CaptialService.MutilCapital;
import com.winsigns.investment.grpc.CaptialService.OneCapital;
import com.winsigns.investment.grpc.capitalGrpc;

import java.util.ArrayList;
import java.util.List;

import com.winsigns.investment.grpc.capitalGrpc.capitalBlockingStub;
import com.winsigns.investment.trade.base.Invest;
import com.winsigns.investment.trade.base.TradeServiceBase;
import com.winsigns.investment.trade.model.*;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@Invest("stock")
public class SHAStockTradeService extends TradeServiceBase {
		
	final String[] quotaType = {
			"td_buy_amout",              //当日买入成交金额
			"td_buy_quantity",           //当日买入成交量
			"td_sell_quantity",          //当日卖出成交量
			"td_change_quantity",        //当日持仓变动量
			"td_change_quantity",        //当日权益持仓变动量
			"td_change_quantity",        //当日可卖持仓变动量

			};

	public SHAStockTradeService() {
		super();
	}

	@Override
	public void calcQuota() {
		ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 30002).usePlaintext(true).build();
		capitalBlockingStub stub = capitalGrpc.newBlockingStub(channel);
		
		if(metadata.investDirection.equals("buy"))
		{
			//1.计算需要资金
			double needCapital = 0;
			double costPrice = 0;
			double restNeedCapital = 0;
			
			costPrice = (metadata.costPrice != null) ?  metadata.costPrice :  10; //TODO 不限价需要获取价格

			switch (metadata.volumeType)
			{
			case AmountType:
				needCapital = metadata.amount;
				break;
			case FixedType:
				needCapital = costPrice * metadata.quantity;
				break;
			default:
				needCapital = costPrice * metadata.quantity;
				break;		
			}
			
			//2.查询资金
			OneCapital.Builder oneCapital = OneCapital.newBuilder()
					.setBody(Capital.newBuilder()
							.setCapitalSvc("normal")
							.setFundaccId("123124")
							.setCurrency("cny")
							.addCapitalQuotaName("float_available_capital"));
			
			MutilCapital reply_capital = stub.queryCapital(oneCapital.build());
			
			List<Capital> detail = new ArrayList<Capital>(reply_capital.getBodyList());
			
			//从大到小排列
			detail.sort((Capital x, Capital y)->
			{
				return y.getCapitalQuotaMap().get("float_available_capital").compareTo
					(x.getCapitalQuotaMap().get("float_available_capital"));
			});
			
			restNeedCapital = needCapital;
			
			boolean success;
			
			for(Capital x : detail)
			{
				if (restNeedCapital >= x.getCapitalQuotaMap().get("float_available_capital"))
				{
					Double thisCapital = x.getCapitalQuotaMap().get("float_available_capital");
					System.out.println(x.getExtCapitalaccId());
					
					Quota quota = repository.quotaRepository.findByPortfolioIdAndSecurityIdAndExtCapitalAccountIdAndExtTradeAccountId(metadata.portfolioId, 
							metadata.securityId, x.getExtCapitalaccId(), null);
					
					if(quota == null) //没有找到
					{
						Quota newQuota = new Quota();
						QuotaDetail newDetail = new QuotaDetail();
						
						newQuota.setPortfolioId(metadata.portfolioId);
						newQuota.setSecurityId(metadata.securityId);
						newQuota.setExtCapitalAccountId(x.getExtCapitalaccId());
						//newQuota.setExtTradeAccountId("");
						
						newDetail.setName("float_td_buy_amout");
						newDetail.setValue(thisCapital);
						newDetail.setQuota(newQuota);
						newQuota.getDetails().add(newDetail);
	
						repository.quotaRepository.save(newQuota);
					}
					
					
					restNeedCapital -= thisCapital;
				}
				else
				{
					success = true;
					break;
				}
			}
			
//			while(restNeedCapital > 0 )
//			{
//				
//			}
			
			
			//quota_.put("td_buy_amout", value)
		}
		
		channel.shutdown();
	}

	@Override
	public boolean checkInstruction() {
		
		// TODO Auto-generated method stub
		
		
		return false;
	}	
}
