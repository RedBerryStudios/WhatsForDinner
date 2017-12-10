package com.redberrystudios.whatsfordinner.member;

import java.util.Objects;

public class Member {

  private Long id;

  private String name;

  private String email;

  private String pictureLink;

  public Member() {
  }

  public Member(Long id) {
    this.id = id;
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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPictureLink() {
    return pictureLink;
  }

  public void setPictureLink(String pictureLink) {
    this.pictureLink = pictureLink;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Member member = (Member) o;
    return Objects.equals(id, member.id) &&
        Objects.equals(name, member.name) &&
        Objects.equals(email, member.email) &&
        Objects.equals(pictureLink, member.pictureLink);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, name, email, pictureLink);
  }

  @Override
  public String toString() {
    return "Member{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", email='" + email + '\'' +
        '}';
  }
}
