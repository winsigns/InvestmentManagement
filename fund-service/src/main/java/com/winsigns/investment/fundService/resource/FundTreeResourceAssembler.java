package com.winsigns.investment.fundService.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.winsigns.investment.fundService.controller.FundAccountController;
import com.winsigns.investment.fundService.controller.FundController;
import com.winsigns.investment.fundService.controller.PortfolioController;
import com.winsigns.investment.fundService.model.Fund;
import com.winsigns.investment.fundService.model.FundAccount;
import com.winsigns.investment.fundService.model.InvestManager;
import com.winsigns.investment.fundService.model.Portfolio;
import com.winsigns.investment.fundService.resource.FundTreeResource.FundAccountItemResource;
import com.winsigns.investment.fundService.resource.FundTreeResource.PortfolioItemResource;

public class FundTreeResourceAssembler extends ResourceAssemblerSupport<Fund, FundTreeResource> {

  InvestManager investManager;

  public FundTreeResourceAssembler(InvestManager investManager) {
    super(FundController.class, FundTreeResource.class);
    this.investManager = investManager;
  }

  @Override
  public FundTreeResource toResource(Fund fund) {
    FundTreeResource resource = createResourceWithId(fund.getId(), fund);

    List<FundAccount> fundAccounts = null;

    if (investManager != null) {

      fundAccounts = new ArrayList<FundAccount>();

      for (FundAccount fundAccount : fund.getFundAccounts()) {
        if (fundAccount.getInvestManager() != null
            && fundAccount.getInvestManager().equals(investManager)) {
          fundAccounts.add(fundAccount);
        }
      }
    } else {
      fundAccounts = fund.getFundAccounts();
    }

    resource.add("fundAccounts", new FundAccountItemResourceAssembler().toResources(fundAccounts));

    return resource;
  }

  @Override
  protected FundTreeResource instantiateResource(Fund entity) {

    return new FundTreeResource(entity);

  }

  public class FundAccountItemResourceAssembler
      extends ResourceAssemblerSupport<FundAccount, FundAccountItemResource> {

    public FundAccountItemResourceAssembler() {
      super(FundAccountController.class, FundAccountItemResource.class);
    }

    @Override
    public FundAccountItemResource toResource(FundAccount fundAccount) {
      FundAccountItemResource resource = createResourceWithId(fundAccount.getId(), fundAccount);

      resource.add("portfolios",
          new PortfolioItemResourceAssembler().toResources(fundAccount.getPortfolios()));

      return resource;
    }

    @Override
    protected FundAccountItemResource instantiateResource(FundAccount entity) {

      return new FundAccountItemResource(entity);

    }
  }

  public class PortfolioItemResourceAssembler
      extends ResourceAssemblerSupport<Portfolio, PortfolioItemResource> {

    public PortfolioItemResourceAssembler() {
      super(PortfolioController.class, PortfolioItemResource.class);
    }

    @Override
    public PortfolioItemResource toResource(Portfolio portfolio) {
      PortfolioItemResource resource = createResourceWithId(portfolio.getId(), portfolio);

      return resource;
    }

    @Override
    protected PortfolioItemResource instantiateResource(Portfolio entity) {

      return new PortfolioItemResource(entity);

    }
  }

}
