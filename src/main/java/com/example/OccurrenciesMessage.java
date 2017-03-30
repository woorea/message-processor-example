package com.example;

/**
 * Created by woorea on 30/03/2017.
 */
public class OccurrenciesMessage extends AbstractMessage {

  private int count;

  public OccurrenciesMessage() {
    super("occurrencies");
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

}
