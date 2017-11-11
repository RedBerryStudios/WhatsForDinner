package com.redberrystudios.whatsfordinner.group;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DayEntity {

  private Date date;

  private List<BoardEntity> boards;

  public DayEntity(Date date) {
    this.date = date;

    boards = new ArrayList<>();
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public List<BoardEntity> getBoards() {
    return boards;
  }

  public void setBoards(List<BoardEntity> boards) {
    this.boards = boards;
  }

  @Override
  public String toString() {
    return "DayEntity{" +
        "date=" + date +
        ", boards=" + boards +
        '}';
  }
}
