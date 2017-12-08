package com.redberrystudios.whatsfordinner.group;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DayElementEntity {

  private Date date;

  private List<Long> boards;

  public DayElementEntity() {
  }

  public DayElementEntity(Date date) {
    this.date = date;
    this.boards = new ArrayList<>();
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public List<Long> getBoards() {
    return boards;
  }

  public void setBoards(List<Long> boards) {
    this.boards = boards;
  }

  @Override
  public String toString() {
    return "DayElementEntity{" +
        "date=" + date +
        ", boards=" + boards +
        '}';
  }
}
