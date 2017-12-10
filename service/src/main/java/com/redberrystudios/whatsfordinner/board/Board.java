package com.redberrystudios.whatsfordinner.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Board {

  private Long id;

  private String name;

  private List<BoardElement> elements;

  public Board() {
    this.elements = new ArrayList<>();
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

  public List<BoardElement> getElements() {
    return elements;
  }

  public void setElements(List<BoardElement> elements) {
    this.elements = elements;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Board board = (Board) o;
    return Objects.equals(id, board.id) &&
        Objects.equals(name, board.name) &&
        Objects.equals(elements, board.elements);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, name, elements);
  }

  @Override
  public String toString() {
    return "Board{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}
