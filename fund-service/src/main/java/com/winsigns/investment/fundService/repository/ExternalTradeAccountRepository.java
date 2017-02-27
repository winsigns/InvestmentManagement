package com.winsigns.investment.fundService.repository;

import com.winsigns.investment.fundService.model.ExternalTradeAccount;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface ExternalTradeAccountRepository extends JpaRepository<ExternalTradeAccount, Long> {

  public List<ExternalTradeAccount> findByExternalCapitalAccountId(Long externalCapitalAccountId);
}
