package com.redberrystudios.whatsfordinner.security.authenticators;

public class ThirdPartyAuthentication {

  private String email;

  private String profileImage;

  public ThirdPartyAuthentication(String email, String profileImage) {
    this.email = email;
    this.profileImage = profileImage;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getProfileImage() {
    return profileImage;
  }

  public void setProfileImage(String profileImage) {
    this.profileImage = profileImage;
  }
}
