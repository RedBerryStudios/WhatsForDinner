package com.redberrystudios.whatsfordinner.checklist;

import java.util.Objects;

public class ChecklistElement {

  private Long id;

  private Boolean isComplete;

  private String text;

  public ChecklistElement() {
  }

  public ChecklistElement(Long id, Boolean isComplete, String text) {
    this.id = id;
    this.isComplete = isComplete;
    this.text = text;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Boolean getIsComplete() {
    return isComplete;
  }

  public void setIsComplete(Boolean complete) {
    isComplete = complete;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ChecklistElement that = (ChecklistElement) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(isComplete, that.isComplete) &&
        Objects.equals(text, that.text);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, isComplete, text);
  }

  @Override
  public String toString() {
    return "ChecklistElement{" +
        "id=" + id +
        ", isComplete=" + isComplete +
        ", text='" + text + '\'' +
        '}';
  }
}
