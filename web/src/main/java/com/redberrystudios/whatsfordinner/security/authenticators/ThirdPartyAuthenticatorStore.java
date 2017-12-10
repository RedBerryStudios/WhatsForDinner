package com.redberrystudios.whatsfordinner.security.authenticators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ThirdPartyAuthenticatorStore {

  @Autowired
  private List<ThirdPartyAuthenticator> authenticators;

  public ThirdPartyAuthentication authenticate(String provider, String authCode) {
    ThirdPartyAuthenticator authenticator = getAuthenticatorForProvider(provider);

    if (authenticator == null) {
      throw new IllegalArgumentException("No authenticator found for provider:" + provider);
    }

    return authenticator.authenticate(authCode);
  }

  private ThirdPartyAuthenticator getAuthenticatorForProvider(String provider) {
    return authenticators.stream().filter(a -> a.getProvider().contentEquals(provider)).findFirst().orElse(null);
  }

}
