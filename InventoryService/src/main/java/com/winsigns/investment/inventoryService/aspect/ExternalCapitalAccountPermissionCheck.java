package com.winsigns.investment.inventoryService.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winsigns.investment.frame.exception.EntityNotFoundException;
import com.winsigns.investment.inventoryService.model.ExternalCapitalAccount;
import com.winsigns.investment.inventoryService.repository.ExternalCapitalAccountRepository;

@Component
@Aspect
public class ExternalCapitalAccountPermissionCheck {

	@Autowired
	private ExternalCapitalAccountRepository externalCapitalAccountRepository;

	@Before("execution(public * com.winsigns.investment.inventoryService.service.ExternalCapitalAccountService.*(Long))")
	public void checkExternalCapitalAccountAccess(JoinPoint joinPoint) {
		Long id = (Long) joinPoint.getArgs()[0];
		ExternalCapitalAccount externalCapitalAccount = externalCapitalAccountRepository.findOne(id);
		checkExternalCapitalAccountAccess(externalCapitalAccount);
	}

	private void checkExternalCapitalAccountAccess(ExternalCapitalAccount externalCapitalAccount) {
		if (externalCapitalAccount == null) {
			throw new EntityNotFoundException();
		}
	}
}
