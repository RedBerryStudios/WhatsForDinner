package com.redberrystudios.whatsfordinner.day;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DayEntity {

  private Long id;

  private Date date;

  private List<Long> boards;

  public DayEntity() {
  }

  public DayEntity(Long id, Date date) {
    this.id = id;
    this.date = date;

    boards = new ArrayList<>();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
    return "DayEntity{" +
        "date=" + date +
        ", boards=" + boards +
        '}';
  }
}
