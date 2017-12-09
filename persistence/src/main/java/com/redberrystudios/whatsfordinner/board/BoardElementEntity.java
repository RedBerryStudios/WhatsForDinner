package com.redberrystudios.whatsfordinner.board;

import java.util.ArrayList;
import java.util.List;

public class BoardElementEntity {

  private Long id;

  private String name;

  private String category;

  private Long producer;

  private List<Long> subscribers;

  public BoardElementEntity() {
    subscribers = new ArrayList<>();
  }

  public BoardElementEntity(Long id, String name, String category, Long producer) {
    this.id = id;
    this.name = name;
    this.category = category;
    this.producer = producer;

    subscribers = new ArrayList<>();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public Long getProducer() {
    return producer;
  }

  public void setProducer(Long producer) {
    this.producer = producer;
  }

  public List<Long> getSubscribers() {
    return subscribers;
  }

  public void setSubscribers(List<Long> subscribers) {
    this.subscribers = subscribers;
  }

  @Override
  public String toString() {
    return "BoardElementEntity{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", category='" + category + '\'' +
        ", producer=" + producer +
        ", subscribers=" + subscribers +
        '}';
  }
}