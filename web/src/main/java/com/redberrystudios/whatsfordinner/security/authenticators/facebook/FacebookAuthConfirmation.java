package com.redberrystudios.whatsfordinner.security.authenticators.facebook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FacebookAuthConfirmation {

  private String access_token;

  public FacebookAuthConfirmation() {
  }

  public String getAccess_token() {
    return access_token;
  }

  public void setAccess_token(String access_token) {
    this.access_token = access_token;
  }
}
