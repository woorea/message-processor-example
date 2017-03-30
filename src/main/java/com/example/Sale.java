package com.example;

import java.math.BigDecimal;

/**
 * Created by woorea on 30/03/2017.
 */
public class Sale {

  private String type;

  private BigDecimal amount;

  private String currency;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    if(!currency.equals("GBP")) {
      throw new CurrencyNotSupportedException();
    }
    this.currency = currency;
  }

  @Override
  public String toString() {
    return new StringBuilder().append("type:").append(type).append(" amount:").append(amount).toString();
  }

}
