package com.winsigns.investment.inventory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.winsigns.investment.inventory.capital.model.ExtCapitalAccountCapital;
import com.winsigns.investment.inventory.capital.model.ExtCapitalAccountCapitalId;
import com.winsigns.investment.inventory.capital.model.ExtCapitalAccountRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InventoryServiceApplicationTests {

	@Autowired
	ExtCapitalAccountRepository extCapitalAccountRepository;
	
	@Test
	@Transactional
	public void contextLoads() {
		
		ExtCapitalAccountCapitalId sourceExtCapitalAccountCapitalId = new ExtCapitalAccountCapitalId();
		sourceExtCapitalAccountCapitalId.setExtCapitalAccountId("10000");
		sourceExtCapitalAccountCapitalId.setCurrency("cny");
		
		ExtCapitalAccountCapital sourceExtCapitalAccount = 
			extCapitalAccountRepository.findOne(sourceExtCapitalAccountCapitalId);
		if(sourceExtCapitalAccount != null){
			sourceExtCapitalAccount.setUnassignCapital(sourceExtCapitalAccount.getUnassignCapital() + 1000);
		}
		else{
			sourceExtCapitalAccount = new ExtCapitalAccountCapital();
			
			sourceExtCapitalAccount.setExtCapitalAccountCapitalId(sourceExtCapitalAccountCapitalId);
			sourceExtCapitalAccount.setUnassignCapital(1000.0);
			
		}
		extCapitalAccountRepository.save(sourceExtCapitalAccount);
		
		sourceExtCapitalAccount = new ExtCapitalAccountCapital();
		sourceExtCapitalAccountCapitalId.setExtCapitalAccountId("sfdsdfsdfsdfsdfsdfsdfddddddddddddddddddddddddddddsdfsdfdsf");
		sourceExtCapitalAccount.setExtCapitalAccountCapitalId(sourceExtCapitalAccountCapitalId);
		
		extCapitalAccountRepository.save(sourceExtCapitalAccount);
	}

}
