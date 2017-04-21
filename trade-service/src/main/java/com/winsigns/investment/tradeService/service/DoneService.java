package com.winsigns.investment.tradeService.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.winsigns.investment.tradeService.command.CreateDoneCommand;
import com.winsigns.investment.tradeService.model.Done;
import com.winsigns.investment.tradeService.model.Entrust;
import com.winsigns.investment.tradeService.repository.DoneRepository;
import com.winsigns.investment.tradeService.repository.EntrustRepository;
import com.winsigns.investment.tradeService.service.common.TradeServiceManager;

/**
 * 成交服务
 * <p>
 * 查询一条委托<br>
 * 增加一条委托<br>
 * 修改一条委托<br>
 * 删除一条委托<br>
 * 
 * @author yimingjin
 *
 */
@Service
public class DoneService {

  @Autowired
  DoneRepository doneRepository;

  @Autowired
  EntrustRepository entrustRepository;

  @Autowired
  TradeServiceManager tradeServiceManager;

  /**
   * 查询一条成交
   * 
   * @param entrustId
   * @return 指定的成交
   */
  public Done readDone(Long doneId) {

    Assert.notNull(doneId);

    Done thisDone = doneRepository.findOne(doneId);

    Assert.notNull(doneId);

    return thisDone;
  }

  /**
   * 增加一条成交
   * 
   * @param command
   * @return
   */
  public Done addDone(CreateDoneCommand command) {

    Long entrustId = command.getEntrustId();
    Assert.notNull(entrustId);
    Entrust entrust = entrustRepository.findOne(entrustId);
    Assert.notNull(entrust);

    Done newDone = new Done();

    newDone.setEntrust(entrust);
    newDone.setDoneQuantity(command.getDoneQuantity());
    newDone.setDonePrice(command.getDonePrice());

    return doneRepository.save(newDone);
  }

  public List<Done> findByEntrust(Entrust entrust) {
    Assert.notNull(entrust);
    doneRepository.findByEntrustOrderByDoneTimeDesc(entrust);
    return entrust.getDones();
  }

}
