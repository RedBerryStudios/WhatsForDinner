package com.redberrystudios.whatsfordinner.security.authenticators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public abstract class ThirdPartyAuthenticator {

  protected RestTemplate restTemplate;

  @Autowired
  public ThirdPartyAuthenticator(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder.build();
  }

  public abstract ThirdPartyAuthentication authenticate(String authCode);

  public abstract String getProvider();

}
