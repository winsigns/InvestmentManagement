package com.winsigns.investment.investService.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.winsigns.investment.framework.model.AbstractEntity;
import com.winsigns.investment.investService.constant.CurrencyCode;
import com.winsigns.investment.investService.constant.InstructionStatus;
import com.winsigns.investment.investService.constant.InstructionVolumeType;

import lombok.Getter;
import lombok.Setter;

/**
 * 指令的实体
 * 
 * @author yimingjin
 *
 */
@Entity
@Relation(value = "instruction", collectionRelation = "instructions")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@DiscriminatorValue("instruction")
public class Instruction extends AbstractEntity {
  // 投资组合
  @Getter
  @Setter
  private Long portfolioId;

  // 投资标的
  @Getter
  @Setter
  private Long securityId;

  // 投资服务
  @Getter
  @Setter
  private String investService;

  // 投资类型
  @Getter
  @Setter
  private String investType;

  // 币种
  @Getter
  @Setter
  @Enumerated(EnumType.STRING)
  private CurrencyCode currency;

  // 成本价
  @Getter
  @Setter
  private Double costPrice;

  // 数量类型
  @Getter
  @Setter
  @Enumerated(EnumType.STRING)
  private InstructionVolumeType volumeType = InstructionVolumeType.FixedType;

  // 指令数量
  @Getter
  @Setter
  private Long quantity;

  // 指令金额
  @Getter
  @Setter
  private Double amount;

  // 投资经理
  @Getter
  @Setter
  private Long investManagerId;

  // 指令状态
  @Getter
  @Setter
  private InstructionStatus executionStatus;

  // 创建时间
  @Getter
  @Setter
  @Temporal(TemporalType.DATE)
  private Date createDate = new Date();

  // 创建时间
  @Getter
  @Setter
  @Temporal(TemporalType.TIMESTAMP)
  private Date createTime = new Timestamp(System.currentTimeMillis());

  // 指令信息
  @OneToMany(mappedBy = "instruction", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnore
  @Setter
  @Getter
  private List<InstructionMessage> messages = new ArrayList<InstructionMessage>();

  // 指令篮子
  @ManyToOne
  @JsonIgnore
  @Getter
  @Setter
  private InstructionBasket instructionBasket;

  // 交易员
  @Getter
  @Setter
  // TODO 暂时写死，随后增加分配交易员
  private Long traderId = 123456L;

  public void addInstructionMessage(InstructionMessage message) {
    this.messages.add(message);
  }

  /**
   * 判断这条指令是否为篮子指令
   * 
   * @return
   */
  public boolean isBasket() {
    if (this instanceof InstructionBasket) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * 判断是否为篮子下的指令
   * 
   * @return
   */
  public boolean isOfBasket() {
    if (this.getInstructionBasket() != null) {
      return true;
    } else {
      return false;
    }
  }

}
