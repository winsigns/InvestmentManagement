package com.winsigns.investment.inventoryService.measure;

import static org.junit.Assert.assertEquals;

import com.winsigns.investment.framework.measure.TradingMeasureValue;
import com.winsigns.investment.inventoryService.common.MeasureTestsBase;
import com.winsigns.investment.inventoryService.model.ECACashPool;
import com.winsigns.investment.inventoryService.model.ECACashSerial;
import com.winsigns.investment.inventoryService.repository.ECACashPoolRepository;
import com.winsigns.investment.inventoryService.repository.ECACashSerialRepository;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by jmx on 4/10/17.
 */

@RunWith(SpringRunner.class)
public class ECACashMeasureTests extends MeasureTestsBase {

  @InjectMocks
  private ECACashMeasure measure;

  @Mock
  private ECACashPoolRepository ecaCashPoolRepository;
  @Mock
  private ECACashSerialRepository ecaCashSerialRepository;
  @Mock
  private ECACashPoolMHT ecaCashPoolMHT;

  private ECACashPool pool = Mockito.spy(new ECACashPool());
  private ArrayList<ECACashSerial> ecaCashSerialArrayList = new ArrayList<>();

  @Before
  public void setUp(){
    Double value = 0.0;
    for (int i = 1; i <= 10; ++i){
      ECACashSerial serial = new ECACashSerial();
      serial.setAssignedCash((double)i);
      serial.setAssignedDate(new Date());
      serial.setEcaCashPool(pool);

      value += i;

      ecaCashSerialArrayList.add(serial);
    }

    pool.setEcaCashSerials(ecaCashSerialArrayList);
    pool.setCurrency(Currency.getInstance("CNY"));
    pool.setExternalCapitalAccountId(1L);

    Mockito.doReturn(ecaCashPoolMHT).when(pool).getType();
    Mockito.doReturn(1L).when(pool).getId();

    Mockito.when(ecaCashPoolRepository.findOne(1L)).thenReturn(pool);
    Mockito.when(ecaCashSerialRepository
          .findByECACashPoolAndAssignedDate(Mockito.eq(pool), Mockito.any(Date.class)))
        .thenReturn(value);
    Mockito.when(ecaCashPoolMHT.getName()).thenReturn("ECACashPoolMHT");
  }

  @Test
  public void normalSenario(){
    double value = 0;
    for (ECACashSerial serial : ecaCashSerialArrayList){
      value += serial.getAssignedCash();
    }

    TradingMeasureValue result = measure.calculate(1L, false, "1");

    Mockito.verify(ecaCashSerialRepository).findByECACashPoolAndAssignedDate(Mockito.eq(pool), Mockito.any(Date.class));
    assertEquals("1", result.getVersion());
    assertEquals(false, result.isFloat());
    assertEquals(value, result.getValue(), 0.00001);
  }
}
