package com.redberrystudios.whatsfordinner.security.authenticators;

import org.springframework.stereotype.Component;

@Component
public class GoogleThirdPartyAuthenticator implements ThirdPartyAuthenticator {

  @Override
  public ThirdPartyAuthentication authenticate(String authCode) {
    return new ThirdPartyAuthentication("google@email.com", "");
  }

  @Override
  public String getProvider() {
    return "google";
  }
}
