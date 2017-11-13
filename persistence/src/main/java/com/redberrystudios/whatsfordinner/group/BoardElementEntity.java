package com.redberrystudios.whatsfordinner.group;

import java.util.ArrayList;
import java.util.List;

public class BoardElementEntity {

  private Long id;

  private String name;

  private String label;

  private Long producer;

  private List<Long> subscribers;

  public BoardElementEntity(Long id, String name, String label, Long producer) {
    this.id = id;
    this.name = name;
    this.label = label;
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

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
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
}