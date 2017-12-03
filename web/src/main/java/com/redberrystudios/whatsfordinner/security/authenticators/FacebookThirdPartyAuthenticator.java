package com.redberrystudios.whatsfordinner.security.authenticators;

import org.springframework.stereotype.Component;

@Component
public class FacebookThirdPartyAuthenticator implements ThirdPartyAuthenticator {

  @Override
  public ThirdPartyAuthentication authenticate(String authCode) {
    return new ThirdPartyAuthentication("facebook@email.com", "");
  }

  @Override
  public String getProvider() {
    return "facebook";
  }
}
