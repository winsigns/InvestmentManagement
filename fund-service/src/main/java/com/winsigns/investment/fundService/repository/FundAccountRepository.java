package com.winsigns.investment.fundService.repository;

import com.winsigns.investment.fundService.model.FundAccount;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FundAccountRepository extends JpaRepository<FundAccount, Long> {

  public List<FundAccount> findByFundId(Long fundId);
}
