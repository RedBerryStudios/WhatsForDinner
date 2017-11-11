package com.redberrystudios.whatsfordinner.group;

import com.redberrystudios.whatsfordinner.member.MemberEntity;
import java.util.ArrayList;
import java.util.List;

public class GroupEntity {

  private Long id;

  private String name;

  private String token;

  private List<DayEntity> days;

  private List<CheckListEntity> checkLists;

  private List<MemberEntity> members;

  public GroupEntity(Long id, String name, String token) {
    this.id = id;
    this.name = name;
    this.token = token;

    days = new ArrayList<>();
    checkLists = new ArrayList<>();
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

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
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

  public List<MemberEntity> getMembers() {
    return members;
  }

  public void setMembers(List<MemberEntity> members) {
    this.members = members;
  }

  @Override
  public String toString() {
    return "GroupEntity{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", token='" + token + '\'' +
        ", days=" + days +
        ", checkLists=" + checkLists +
        ", members=" + members +
        '}';
  }
}
