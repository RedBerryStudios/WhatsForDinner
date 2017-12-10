package com.redberrystudios.whatsfordinner.checklist;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Checklist {

  private Long id;

  private String name;

  private List<ChecklistElement> elements;

  public Checklist() {
    this.elements = new ArrayList<>();
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

  public List<ChecklistElement> getElements() {
    return elements;
  }

  public void setElements(List<ChecklistElement> elements) {
    this.elements = elements;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Checklist checklist = (Checklist) o;
    return Objects.equals(id, checklist.id) &&
        Objects.equals(name, checklist.name) &&
        Objects.equals(elements, checklist.elements);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, name, elements);
  }

  @Override
  public String toString() {
    return "Checklist{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}
