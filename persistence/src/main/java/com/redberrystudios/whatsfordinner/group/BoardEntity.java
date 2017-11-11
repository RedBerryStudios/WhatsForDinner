package com.redberrystudios.whatsfordinner.group;

import java.util.ArrayList;
import java.util.List;

public class BoardEntity {

  private Long id;

  private String name;

  private List<BoardElementEntity> elements;

  private List<String> labels;

  public BoardEntity(Long id, String name) {
    this.id = id;
    this.name = name;

    elements = new ArrayList<>();
    labels = new ArrayList<>();
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

  public List<BoardElementEntity> getElements() {
    return elements;
  }

  public void setElements(List<BoardElementEntity> elements) {
    this.elements = elements;
  }

  public List<String> getLabels() {
    return labels;
  }

  public void setLabels(List<String> labels) {
    this.labels = labels;
  }

  @Override
  public String toString() {
    return "BoardEntity{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", elements=" + elements +
        ", labels=" + labels +
        '}';
  }
}