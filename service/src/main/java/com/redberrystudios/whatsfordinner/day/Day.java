package com.redberrystudios.whatsfordinner.day;

import com.redberrystudios.whatsfordinner.board.Board;

import java.util.Date;
import java.util.List;

public class Day {

  private Long id;

  private Date date;

  private List<Board> boards;

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

  public List<Board> getBoards() {
    return boards;
  }

  public void setBoards(List<Board> boards) {
    this.boards = boards;
  }
}
