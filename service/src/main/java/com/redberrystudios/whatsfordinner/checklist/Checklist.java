package com.redberrystudios.whatsfordinner.checklist;

import java.util.List;

public class Checklist {

  private Long id;

  private String name;

  private List<ChecklistElement> elements;

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

  public List<ChecklistElement> getElements() {
    return elements;
  }

  public void setElements(List<ChecklistElement> elements) {
    this.elements = elements;
  }
}
