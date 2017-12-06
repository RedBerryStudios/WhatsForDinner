package com.redberrystudios.whatsfordinner.security.authenticators.google;

import com.redberrystudios.whatsfordinner.security.authenticators.ThirdPartyAuthentication;
import com.redberrystudios.whatsfordinner.security.authenticators.ThirdPartyAuthenticator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class GoogleThirdPartyAuthenticator extends ThirdPartyAuthenticator {

  @Value("${authenticators.google.redirectUri}")
  private String redirectUri;

  @Value("${authenticators.google.clientId}")
  private String clientId;

  @Value("${authenticators.google.clientSecret}")
  private String clientSecret;

  public GoogleThirdPartyAuthenticator(RestTemplateBuilder restTemplateBuilder) {
    super(restTemplateBuilder);
  }

  @Override
  public ThirdPartyAuthentication authenticate(String authCode) {
    String googleAccessTokenUri = "https://www.googleapis.com/oauth2/v4/token";

    MultiValueMap<String, String> accessTokenBody = new LinkedMultiValueMap<>();
    accessTokenBody.add("redirect_uri", redirectUri);
    accessTokenBody.add("client_id", clientId);
    accessTokenBody.add("client_secret", clientSecret);
    accessTokenBody.add("scope", "https://www.googleapis.com/auth/userinfo.email");
    accessTokenBody.add("grant_type", "authorization_code");
    accessTokenBody.add("code", authCode);

    GoogleAuthConfirmation authConfirmation = restTemplate.postForObject(googleAccessTokenUri, accessTokenBody, GoogleAuthConfirmation.class);

    URI googleUserDataUri = null;
    try {
      googleUserDataUri = new URI("https://www.googleapis.com/plus/v1/people/me?sz=200");
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }

    System.out.println(authConfirmation.getToken_type() + " " + authConfirmation.getAccess_token());

    RequestEntity requestEntity = RequestEntity.get(googleUserDataUri)
        .header("Authorization", authConfirmation.getToken_type() + " " + authConfirmation.getAccess_token())
        .build();

    ResponseEntity<GoogleThirdPartyAuthentication> authentication =
        restTemplate.exchange(requestEntity, GoogleThirdPartyAuthentication.class);


    return authentication.getBody();
  }

  @Override
  public String getProvider() {
    return "google";
  }
}
