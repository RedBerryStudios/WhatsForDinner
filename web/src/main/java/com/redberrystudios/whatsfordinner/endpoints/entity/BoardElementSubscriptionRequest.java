package com.redberrystudios.whatsfordinner.endpoints.entity;

public class BoardElementSubscriptionRequest {

  private Long boardElementId;

  public BoardElementSubscriptionRequest() {
  }

  public Long getBoardElementId() {
    return boardElementId;
  }

  public void setBoardElementId(Long boardElementId) {
    this.boardElementId = boardElementId;
  }
}
