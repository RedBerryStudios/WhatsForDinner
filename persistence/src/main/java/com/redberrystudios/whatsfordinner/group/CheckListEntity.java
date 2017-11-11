package com.redberrystudios.whatsfordinner.group;

import java.util.ArrayList;
import java.util.List;

public class CheckListEntity {

  private Long id;

  private String name;

  private List<ListElement> elements;

  public CheckListEntity(Long id, String name) {
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

  public List<ListElement> getElements() {
    return elements;
  }

  public void setElements(List<ListElement> elements) {
    this.elements = elements;
  }

  @Override
  public String toString() {
    return "CheckListEntity{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", elements=" + elements +
        '}';
  }

  private class ListElement {

    private Long id;

    private boolean ready;

    private String text;

    public ListElement(Long id, boolean ready, String text) {
      this.id = id;
      this.ready = ready;
      this.text = text;
    }

    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }

    public boolean isReady() {
      return ready;
    }

    public void setReady(boolean ready) {
      this.ready = ready;
    }

    public String getText() {
      return text;
    }

    public void setText(String text) {
      this.text = text;
    }

    @Override
    public String toString() {
      return "ListElement{" +
          "id=" + id +
          ", ready=" + ready +
          ", text='" + text + '\'' +
          '}';
    }
  }
}
