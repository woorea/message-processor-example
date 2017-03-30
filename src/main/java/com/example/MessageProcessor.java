package com.example;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by woorea on 30/03/2017.
 */
public class MessageProcessor {

  private int salesCounter = 0;

  private final MessageRepository messages;

  private final SaleRepository sales;

  private Logger logger = new Logger();

  public MessageProcessor(MessageRepository messages, SaleRepository sales) {

    this.messages = messages;
    this.sales = sales;

  }

  public void setLogger(Logger logger) {
    this.logger = logger;
  }

  public void process(AbstractMessage message) {

    messages.add(message);

    switch (message.getType()) {
      case "sale":
        sales.add(message.getSale());
        processSale(((SaleMessage) message));
        break;
      case "ocurrencies":
        processOccurrencies(((OccurrenciesMessage) message));
        break;
      case "adjustment":
        processAdjustment(((AdjustmentMessage) message));
        break;
    }

  }

  private void processAdjustment(AdjustmentMessage message) {
    Stream<Sale> stream = sales.find(it -> it.getType().equals(message.getSale().getType()));
    switch (message.getOperation()) {
      case "add":
        stream.forEach(it -> it.setAmount(it.getAmount().add(message.getSale().getAmount())));
        break;
      case "substract":
        stream.forEach(it -> it.setAmount(it.getAmount().subtract(message.getSale().getAmount())));
        break;
      case "multiply":
        stream.forEach(it -> it.setAmount(it.getAmount().multiply(message.getSale().getAmount())));
        break;
    }
  }

  private void processOccurrencies(OccurrenciesMessage message) {
    //what to do with this kind of message?
  }

  private void processSale(SaleMessage message) {
    salesCounter++;
    if(salesCounter % 10 == 0) {
      Map<String, BigDecimal> groupingByType = sales.list().stream().collect(
        Collectors.groupingBy(s ->
          s.getType(),
          Collectors.mapping(
            Sale::getAmount,
            Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
          )
        )
      );
      logger.logStats(groupingByType.toString());
    }
    if(salesCounter % 50 == 0) {
      String text = messages.find(m -> m.getType().equals("adjustment")).map(it -> it.toString()).collect(Collectors.joining("\n"));
      logger.logGlobalStats(text);
    }
  }

}
