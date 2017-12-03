package com.redberrystudios.whatsfordinner.group;

import org.bson.codecs.pojo.annotations.BsonId;

import java.util.ArrayList;
import java.util.List;

public class GroupEntity {

  @BsonId
  private Long id;

  private String name;

  private List<Long> days;

  private List<Long> checklists;

  private List<Long> members;

  private String joinToken;

  public GroupEntity() {
  }

  public GroupEntity(Long id, String name) {
    this.id = id;
    this.name = name;

    days = new ArrayList<>();
    checklists = new ArrayList<>();
    members = new ArrayList<>();
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

  public List<Long> getDays() {
    return days;
  }

  public void setDays(List<Long> days) {
    this.days = days;
  }

  public List<Long> getChecklists() {
    return checklists;
  }

  public void setChecklists(List<Long> checklists) {
    this.checklists = checklists;
  }

  public List<Long> getMembers() {
    return members;
  }

  public void setMembers(List<Long> members) {
    this.members = members;
  }

  public String getJoinToken() {
    return joinToken;
  }

  public void setJoinToken(String joinToken) {
    this.joinToken = joinToken;
  }

  @Override
  public String toString() {
    return "GroupEntity{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", joinToken='" + joinToken + '\'' +
        '}';
  }
}
