package com.redberrystudios.whatsfordinner.board;

import com.redberrystudios.whatsfordinner.member.Member;

import java.util.List;
import java.util.Objects;

public class BoardElement {

  private Long id;

  private String name;

  private String category;

  private Member producer;

  private List<Member> subscribers;

  public BoardElement() {
  }

  public BoardElement(Long id, String name, String category, Member producer, List<Member> subscribers) {
    this.id = id;
    this.name = name;
    this.category = category;
    this.producer = producer;
    this.subscribers = subscribers;
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

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public Member getProducer() {
    return producer;
  }

  public void setProducer(Member producer) {
    this.producer = producer;
  }

  public List<Member> getSubscribers() {
    return subscribers;
  }

  public void setSubscribers(List<Member> subscribers) {
    this.subscribers = subscribers;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BoardElement that = (BoardElement) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(name, that.name) &&
        Objects.equals(category, that.category) &&
        Objects.equals(producer, that.producer) &&
        Objects.equals(subscribers, that.subscribers);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, name, category, producer, subscribers);
  }

  @Override
  public String toString() {
    return "BoardElement{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", category='" + category + '\'' +
        '}';
  }
}
