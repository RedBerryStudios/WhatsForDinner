package com.redberrystudios.whatsfordinner.group;

import com.redberrystudios.whatsfordinner.checklist.Checklist;
import com.redberrystudios.whatsfordinner.member.Member;

import java.util.List;

public class Group {

  private Long id;

  private String name;

  private String joinToken;

  private List<Member> members;

  private List<DayElement> days;

  private List<Checklist> checklists;

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

  public String getJoinToken() {
    return joinToken;
  }

  public void setJoinToken(String joinToken) {
    this.joinToken = joinToken;
  }

  public List<Member> getMembers() {
    return members;
  }

  public void setMembers(List<Member> members) {
    this.members = members;
  }

  public List<DayElement> getDays() {
    return days;
  }

  public void setDays(List<DayElement> days) {
    this.days = days;
  }

  public List<Checklist> getChecklists() {
    return checklists;
  }

  public void setChecklists(List<Checklist> checklists) {
    this.checklists = checklists;
  }

}
