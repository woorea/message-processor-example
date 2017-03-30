package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created by woorea on 30/03/2017.
 */
public abstract class AbstractRepository<T> {

  private List<T> entities = new ArrayList<>();

  public void add(T type) {
    entities.add(type);
  }

  public int count() {
    return entities.size();
  }

  public Stream<T> find(Predicate<T> predicate) {
    return entities.stream().filter(predicate);
  }

  public List<T> list() {
    return entities;
  }
}
