package com.redberrystudios.whatsfordinner.group;

import com.redberrystudios.whatsfordinner.checklist.Checklist;
import com.redberrystudios.whatsfordinner.member.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Group {

  private Long id;

  private String name;

  private String joinToken;

  private List<Member> members;

  private List<DayElement> days;

  private List<Checklist> checklists;

  public Group() {
    this.members = new ArrayList<>();
    this.days = new ArrayList<>();
    this.checklists = new ArrayList<>();
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Group group = (Group) o;
    return Objects.equals(id, group.id) &&
        Objects.equals(name, group.name) &&
        Objects.equals(joinToken, group.joinToken) &&
        Objects.equals(members, group.members) &&
        Objects.equals(days, group.days) &&
        Objects.equals(checklists, group.checklists);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, name, joinToken, members, days, checklists);
  }

  @Override
  public String toString() {
    return "Group{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", joinToken='" + joinToken + '\'' +
        '}';
  }
}
