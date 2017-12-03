package com.redberrystudios.whatsfordinner.security.authenticators;

public interface ThirdPartyAuthenticator {

  ThirdPartyAuthentication authenticate(String authCode);

  String getProvider();

}
