package com.redberrystudios.whatsfordinner.checklist;

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

  public Boolean getComplete() {
    return isComplete;
  }

  public void setComplete(Boolean complete) {
    isComplete = complete;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}
