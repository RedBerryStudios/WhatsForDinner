package com.redberrystudios.whatsfordinner.member;

import org.bson.codecs.pojo.annotations.BsonId;

public class MemberEntity {

  @BsonId
  private Long id;

  private String name;

  private String email;

  private String pictureLink;

  public MemberEntity() {
  }

  public MemberEntity(Long id, String name, String email, String pictureLink) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.pictureLink = pictureLink;
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
  public String toString() {
    return "MemberEntity{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}
