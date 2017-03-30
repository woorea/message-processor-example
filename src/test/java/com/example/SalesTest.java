package com.example;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * Created by woorea on 30/03/2017.
 */
public class SalesTest {

  /**
   * A sale has a product type field and a value – you should choose sensible types for these.
   */
  @Test
  public void aSaleShouldHaveProductTypeAndValue() {

    Sale sale = new Sale();
    sale.setType("type.1");
    sale.setAmount(new BigDecimal("1.0"));
    sale.setCurrency("GBP");

    Assert.assertEquals("type.1", sale.getType());
    Assert.assertEquals(new BigDecimal("1.0"), sale.getAmount());
    Assert.assertEquals("GBP", sale.getCurrency());

  }

  /**
   * A message notifying you of a sale could be one of the following types:
   *
   * Message Type 1 – contains the details of 1 sale E.g apple at 10p
   *
   * Message Type 2 – contains the details of a sale and the number of occurrences of
   * that sale. E.g 20 sales of apples at 10p each.
   *
   * Message Type 3 – contains the details of a sale and an adjustment operation to be
   * applied to all stored sales of this product type. Operations can be add, subtract, or
   * multiply e.g Add 20p apples would instruct your application to add 20p to each sale
   * of apples you have recorded.
   *
   */
    @Test
    public void differentSalesType() {

      Sale sale = new Sale();
      sale.setType("apples");
      sale.setAmount(new BigDecimal("10.0"));
      sale.setCurrency("GBP");

      SaleMessage message = new SaleMessage();
      message.setSale(sale);

      OccurrenciesMessage ocurrencies = new OccurrenciesMessage();
      ocurrencies.setCount(20);

      AdjustmentMessage adjustment = new AdjustmentMessage();
      adjustment.setSale(sale);
      adjustment.setOperation("add");

    }

  /**
   * For these requirements only GBP is supported, but prepared to be extensible
   */
  @Test(expected = CurrencyNotSupportedException.class)
  public void onlyGBPIsSupported() {

    Sale sale = new Sale();
    sale.setType("type.1");
    sale.setAmount(new BigDecimal("1.0"));
    sale.setCurrency("EUR");

  }

}
