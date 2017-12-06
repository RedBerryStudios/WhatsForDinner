package com.redberrystudios.whatsfordinner.security.authenticators.facebook;

import com.redberrystudios.whatsfordinner.security.authenticators.ThirdPartyAuthentication;
import com.redberrystudios.whatsfordinner.security.authenticators.ThirdPartyAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class FacebookThirdPartyAuthenticator extends ThirdPartyAuthenticator {

  @Value("${authenticators.facebook.redirectUri}")
  private String redirectUri;

  @Value("${authenticators.facebook.clientId}")
  private String clientId;

  @Value("${authenticators.facebook.clientSecret}")
  private String clientSecret;

  @Autowired
  public FacebookThirdPartyAuthenticator(RestTemplateBuilder restTemplateBuilder) {
    super(restTemplateBuilder);
  }

  @Override
  public ThirdPartyAuthentication authenticate(String authCode) {
    String facebookAccessTokenUri = "https://graph.facebook.com/v2.11/oauth/access_token?" +
        "client_id={clientId}&" +
        "client_secret={clientSecret}&" +
        "redirect_uri={redirectUri}&" +
        "code={code}";

    Map<String, String> accessTokenParams = new HashMap<>();
    accessTokenParams.put("clientId", clientId);
    accessTokenParams.put("clientSecret", clientSecret);
    accessTokenParams.put("redirectUri", redirectUri);
    accessTokenParams.put("code", authCode);

    FacebookAuthConfirmation authConfirmation =
        restTemplate.getForObject(facebookAccessTokenUri, FacebookAuthConfirmation.class, accessTokenParams);

    String facebookUserDataUri = "https://graph.facebook.com/v2.11/me?" +
        "fields=email,id,name,picture.type(large)&" +
        "access_token={accessToken}";

    Map<String, String> userDataParams = new HashMap<>();
    userDataParams.put("accessToken", authConfirmation.getAccess_token());

    FacebookThirdPartyAuhentication thirdPartyAuhentication =
        restTemplate.getForObject(facebookUserDataUri, FacebookThirdPartyAuhentication.class, userDataParams);

    return thirdPartyAuhentication;
  }

  @Override
  public String getProvider() {
    return "facebook";
  }
}
