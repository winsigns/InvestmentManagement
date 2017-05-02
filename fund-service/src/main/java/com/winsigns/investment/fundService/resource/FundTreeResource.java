package com.winsigns.investment.fundService.resource;

import org.springframework.hateoas.core.Relation;

import com.winsigns.investment.framework.hal.HALResponse;
import com.winsigns.investment.framework.model.Item;
import com.winsigns.investment.fundService.model.Fund;
import com.winsigns.investment.fundService.model.FundAccount;
import com.winsigns.investment.fundService.model.Portfolio;
import com.winsigns.investment.fundService.resource.FundTreeResource.FundItem;

@Relation(value = "fund", collectionRelation = "funds")
public class FundTreeResource extends HALResponse<FundItem> {
  public FundTreeResource(FundItem content) {
    super(content);
  }

  public FundTreeResource(Fund fund) {
    this(new FundItem(fund));
  }

  static protected class FundItem extends Item {

    public FundItem(Fund fund) {
      this.value = fund.getId();
      this.label = fund.getShortName();
    }

  }

  static protected class FundAccountItem extends Item {

    public FundAccountItem(FundAccount fundAccount) {
      this.value = fundAccount.getId();
      this.label = fundAccount.getName();
    }

  }

  static protected class FundAccountItemResource extends HALResponse<FundAccountItem> {

    public FundAccountItemResource(FundAccountItem content) {
      super(content);
    }

    public FundAccountItemResource(FundAccount fundAccount) {
      this(new FundAccountItem(fundAccount));
    }

  }

  static protected class PortfolioItem extends Item {

    public PortfolioItem(Portfolio portfolio) {
      this.value = portfolio.getId();
      this.label = portfolio.getName();
    }

  }

  static protected class PortfolioItemResource extends HALResponse<PortfolioItem> {

    public PortfolioItemResource(PortfolioItem content) {
      super(content);
    }

    public PortfolioItemResource(Portfolio portfolio) {
      this(new PortfolioItem(portfolio));
    }

  }

}
