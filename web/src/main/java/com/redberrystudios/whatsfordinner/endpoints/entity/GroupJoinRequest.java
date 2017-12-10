package com.redberrystudios.whatsfordinner.endpoints.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupJoinRequest {

  private String joinToken;

  public GroupJoinRequest() {
  }

  public String getJoinToken() {
    return joinToken;
  }

  public void setJoinToken(String joinToken) {
    this.joinToken = joinToken;
  }
}
