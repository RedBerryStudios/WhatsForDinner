package com.redberrystudios.whatsfordinner.security.authenticators.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.redberrystudios.whatsfordinner.security.authenticators.ThirdPartyAuthentication;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleThirdPartyAuthentication implements ThirdPartyAuthentication {

  private String displayName;

  private List<Email> emails;

  private Image image;

  public GoogleThirdPartyAuthentication() {
  }

  @Override
  public String getName() {
    return displayName;
  }

  @Override
  public String getEmail() {
    return emails.stream()
        .filter(e -> e.getType().equals("account"))
        .findFirst()
        .get()
        .getValue();
  }

  @Override
  public String getProfileImage() {
    return image.getUrl();
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public List<Email> getEmails() {
    return emails;
  }

  public void setEmails(List<Email> emails) {
    this.emails = emails;
  }

  public Image getImage() {
    return image;
  }

  public void setImage(Image image) {
    this.image = image;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Email {

    private String value;

    private String type;

    public Email() {
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Image {

    private String url;

    public Image() {
    }

    public String getUrl() {
      return url;
    }

    public void setUrl(String url) {
      this.url = url;
    }
  }
}
