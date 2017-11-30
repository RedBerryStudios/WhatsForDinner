package com.redberrystudios.whatsfordinner.member;

public class MemberEntity {

  private Long id;

  private String name;

  private String email;

  private String pictureLink;

  private Long groupId;

  public MemberEntity() {
  }

  public MemberEntity(Long id, String name, String email, String pictureLink, Long groupId) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.pictureLink = pictureLink;
    this.groupId = groupId;
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

  public Long getGroupId() {
    return groupId;
  }

  public void setGroupId(Long groupId) {
    this.groupId = groupId;
  }

  @Override
  public String toString() {
    return "MemberEntity{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}
