package com.redberrystudios.whatsfordinner.endpoints.authentication;

public class AuthenticationRequest {

  private String provider;

  private String code;

  public String getProvider() {
    return provider;
  }

  public void setProvider(String provider) {
    this.provider = provider;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }
}
