package com.redberrystudios.whatsfordinner.group;

import java.util.ArrayList;
import java.util.List;

public class GroupEntity {

  private String id;

  private String name;

  private List<DayEntity> days;

  private List<CheckListEntity> checkLists;

  private List<Long> members;

  public GroupEntity(String id, String name) {
    this.id = id;
    this.name = name;

    days = new ArrayList<>();
    checkLists = new ArrayList<>();
    members = new ArrayList<>();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<DayEntity> getDays() {
    return days;
  }

  public void setDays(List<DayEntity> days) {
    this.days = days;
  }

  public List<CheckListEntity> getCheckLists() {
    return checkLists;
  }

  public void setCheckLists(List<CheckListEntity> checkLists) {
    this.checkLists = checkLists;
  }

  public List<Long> getMembers() {
    return members;
  }

  public void setMembers(List<Long> members) {
    this.members = members;
  }

  @Override
  public String toString() {
    return "GroupEntity{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", days=" + days +
        ", checkLists=" + checkLists +
        ", members=" + members +
        '}';
  }
}
