package com.redberrystudios.whatsfordinner.group;

import com.redberrystudios.whatsfordinner.board.Board;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class DayElement {

  private Date date;

  private List<Board> boards;

  public DayElement() {
    this.boards = new ArrayList<>();
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public List<Board> getBoards() {
    return boards;
  }

  public void setBoards(List<Board> boards) {
    this.boards = boards;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DayElement that = (DayElement) o;
    return Objects.equals(date, that.date) &&
        Objects.equals(boards, that.boards);
  }

  @Override
  public int hashCode() {

    return Objects.hash(date, boards);
  }

  @Override
  public String toString() {
    return "DayElement{" +
        "date=" + date +
        '}';
  }
}
