package com.example;

/**
 * Created by woorea on 30/03/2017.
 */
public class AdjustmentMessage extends AbstractMessage {

  private String operation;

  public AdjustmentMessage() {
    super("adjustment");
  }

  public String getOperation() {
    return operation;
  }

  public void setOperation(String operation) {
    this.operation = operation;
  }

  @Override
  public String toString() {
    return new StringBuilder().append("operation:").append(operation).append(" sale -> ").append(getSale()).toString();
  }

}
