package com.example;

/**
 * Created by woorea on 30/03/2017.
 */
public abstract class AbstractMessage {

  protected String type;

  private Sale sale;

  public AbstractMessage(String type) {
    this.type = type;
  }

  public String getType() {
    return this.type;
  }

  public Sale getSale() {
    return sale;
  }

  public void setSale(Sale sale) {
    this.sale = sale;
  }
}
