package com.redberrystudios.whatsfordinner.security.authenticators.facebook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.redberrystudios.whatsfordinner.security.authenticators.ThirdPartyAuthentication;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FacebookThirdPartyAuhentication implements ThirdPartyAuthentication {

  private String name;

  private String email;

  private ProfilePicture picture;

  public FacebookThirdPartyAuhentication() {
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getEmail() {
    return email;
  }

  @Override
  public String getProfileImage() {
    return picture.getData().getUrl();
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public ProfilePicture getPicture() {
    return picture;
  }

  public void setPicture(ProfilePicture picture) {
    this.picture = picture;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class ProfilePicture {

    private Data data;

    public ProfilePicture() {
    }

    public Data getData() {
      return data;
    }

    public void setData(Data data) {
      this.data = data;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {

      private String url;

      public Data() {
      }

      public String getUrl() {
        return url;
      }

      public void setUrl(String url) {
        this.url = url;
      }
    }

  }

}
