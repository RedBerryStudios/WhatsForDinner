package com.redberrystudios.whatsfordinner.checklist;

public class ChecklistElementEntity {

  private Long id;

  private boolean isComplete;

  private String text;

  public ChecklistElementEntity() {
  }

  public ChecklistElementEntity(Long id, boolean isComplete, String text) {
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

  public boolean isComplete() {
    return isComplete;
  }

  public void setComplete(boolean complete) {
    this.isComplete = complete;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return "ChecklistElementEntity{" +
        "id=" + id +
        ", isComplete=" + isComplete +
        ", text='" + text + '\'' +
        '}';
  }
}
