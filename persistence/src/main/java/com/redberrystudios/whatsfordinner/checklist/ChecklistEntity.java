package com.redberrystudios.whatsfordinner.checklist;

import java.util.ArrayList;
import java.util.List;

public class ChecklistEntity {

  private Long id;

  private String name;

  private List<ChecklistElementEntity> elements;

  public ChecklistEntity() {
  }

  public ChecklistEntity(Long id, String name) {
    this.id = id;
    this.name = name;

    elements = new ArrayList<>();
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

  public List<ChecklistElementEntity> getElements() {
    return elements;
  }

  public void setElements(List<ChecklistElementEntity> elements) {
    this.elements = elements;
  }

  @Override
  public String toString() {
    return "ChecklistEntity{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", elements=" + elements +
        '}';
  }
}
