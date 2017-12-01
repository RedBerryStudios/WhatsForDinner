package com.redberrystudios.whatsfordinner.board;

import java.util.List;

public class Board {

  private Long id;

  private String name;

  private List<BoardElement> elements;

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
}
