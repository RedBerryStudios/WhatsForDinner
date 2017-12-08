package com.redberrystudios.whatsfordinner.group;

import com.redberrystudios.whatsfordinner.board.Board;

import java.util.Date;
import java.util.List;

public class DayElement {

  private Date date;

  private List<Board> boards;

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
}
