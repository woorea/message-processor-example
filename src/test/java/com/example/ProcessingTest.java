package com.example;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

/**
 * Created by woorea on 30/03/2017.
 */
public class ProcessingTest {

  private MessageRepository messages;

  private SaleRepository sales;

  private MessageProcessor processor;

  private Logger mockLogger;

  @Before
  public void before() {

    this.messages = new MessageRepository();

    this.sales = new SaleRepository();

    this.mockLogger = Mockito.mock(Logger.class);

    this.processor = new MessageProcessor(messages, sales);
    this.processor.setLogger(this.mockLogger);


  }

  /**
   *
   * All messages must be processed
   *
   */
  @Test
  public void allMessagesMustBeProcessed() {

    Assert.assertEquals(messages.count(), 0L);

    Sale sale = new Sale();
    sale.setType("type.1");
    sale.setAmount(new BigDecimal("1.0"));
    sale.setCurrency("GBP");

    SaleMessage message = new SaleMessage();
    message.setSale(sale);

    processor.process(message);

    Assert.assertEquals(1, messages.count());

  }

  /**
   *
   * All sales must be recorded
   *
   */
  @Test
  public void allSalesMustBeRecorded() {

    Assert.assertEquals(0, sales.count());

    Sale sale = new Sale();
    sale.setType("type.1");
    sale.setAmount(new BigDecimal("1.0"));
    sale.setCurrency("GBP");

    SaleMessage message = new SaleMessage();
    message.setSale(sale);

    processor.process(message);

    Assert.assertEquals(1, sales.count());

  }



  /**
   *
   * After every 10th message received your application should log a report detailing the number
   * of sales of each product and their total value.
   *
   */
  @Test
  public void statsEvery10Sales() {

    for(int i = 0; i < 15; i++) {

      Sale sale = new Sale();
      sale.setType("type.1");
      sale.setAmount(new BigDecimal("1.0"));
      sale.setCurrency("GBP");

      SaleMessage message = new SaleMessage();
      message.setSale(sale);

      processor.process(message);

    }

    Mockito.verify(mockLogger, Mockito.times(1)).logStats("{type.1=10.0}");

  }

  /**
   *
   * After 50 messages your application should log that it is pausing, stop accepting new
   * messages and log a report of the adjustments that have been made to each sale type while
   * the application was running.
   *
   */
  @Test
  public void globalStatAfter50Messages() {

    Sale adjustmentSale = new Sale();
    adjustmentSale.setType("apples");
    adjustmentSale.setAmount(new BigDecimal("20"));
    adjustmentSale.setCurrency("GBP");

    AdjustmentMessage adjustment = new AdjustmentMessage();
    adjustment.setSale(adjustmentSale);
    adjustment.setOperation("add");

    processor.process(adjustment);


    for(int i = 0; i < 55; i++) {

      Sale sale = new Sale();
      sale.setType("type.1");
      sale.setAmount(new BigDecimal("1.0"));
      sale.setCurrency("GBP");

      SaleMessage message = new SaleMessage();
      message.setSale(sale);

      processor.process(message);

    }

    Mockito.verify(mockLogger, Mockito.times(1)).logGlobalStats("operation:add sale -> type:apples amount:20");

  }

  /**
   *
   */
  @Test
  public void adjustmentCanAdd() {

    Sale sale = new Sale();
    sale.setType("apples");
    sale.setAmount(new BigDecimal("1.0"));
    sale.setCurrency("GBP");

    sales.add(sale);

    Sale adjustmentSale = new Sale();
    adjustmentSale.setType("apples");
    adjustmentSale.setAmount(new BigDecimal("20"));
    adjustmentSale.setCurrency("GBP");

    AdjustmentMessage adjustment = new AdjustmentMessage();
    adjustment.setSale(adjustmentSale);
    adjustment.setOperation("add");

    processor.process(adjustment);

    Assert.assertEquals(new BigDecimal("21.0"), sale.getAmount());

  }

  /**
   *
   */
  @Test
  public void adjustmentCanMultiply() {

    Sale sale = new Sale();
    sale.setType("apples");
    sale.setAmount(new BigDecimal("100.0"));
    sale.setCurrency("GBP");

    sales.add(sale);

    Sale adjustmentSale = new Sale();
    adjustmentSale.setType("apples");
    adjustmentSale.setAmount(new BigDecimal("2"));
    adjustmentSale.setCurrency("GBP");

    AdjustmentMessage adjustment = new AdjustmentMessage();
    adjustment.setSale(adjustmentSale);
    adjustment.setOperation("multiply");

    processor.process(adjustment);

    Assert.assertEquals(new BigDecimal("200.0"), sale.getAmount());

  }

  /**
   *
   */
  @Test
  public void adjustmentCanSubstract() {

    Sale sale = new Sale();
    sale.setType("apples");
    sale.setAmount(new BigDecimal("100.0"));
    sale.setCurrency("GBP");

    sales.add(sale);

    Sale adjustmentSale = new Sale();
    adjustmentSale.setType("apples");
    adjustmentSale.setAmount(new BigDecimal("50.0"));
    adjustmentSale.setCurrency("GBP");

    AdjustmentMessage adjustment = new AdjustmentMessage();
    adjustment.setSale(adjustmentSale);
    adjustment.setOperation("substract");

    processor.process(adjustment);

    Assert.assertEquals(new BigDecimal("50.0"), sale.getAmount());

  }

}
