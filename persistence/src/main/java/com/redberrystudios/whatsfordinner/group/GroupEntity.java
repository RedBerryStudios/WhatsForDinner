package com.redberrystudios.whatsfordinner.group;

import java.util.ArrayList;
import java.util.List;
import org.bson.codecs.pojo.annotations.BsonId;

public class GroupEntity {

  @BsonId
  private Long id;

  private String name;

  private List<DayElementEntity> days;

  private List<Long> checklists;

  private List<Long> members;

  private String joinToken;

  public GroupEntity() {
    days = new ArrayList<>();
    checklists = new ArrayList<>();
    members = new ArrayList<>();
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

  public List<DayElementEntity> getDays() {
    return days;
  }

  public void setDays(List<DayElementEntity> days) {
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
