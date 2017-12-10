package com.redberrystudios.whatsfordinner.security.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

  private Object principal;

  private Object credentials;

  private boolean isAuthenticated;

  public JwtAuthenticationToken(Object principal, Object credentials, boolean isAuthenticated) {
    super(null);
    this.principal = principal;
    this.credentials = credentials;
    this.isAuthenticated = isAuthenticated;
  }

  @Override
  public Object getCredentials() {
    return credentials;
  }

  @Override
  public Object getPrincipal() {
    return principal;
  }

  @Override
  public boolean isAuthenticated() {
    return isAuthenticated;
  }

  @Override
  public void setAuthenticated(boolean isAuthenticated) {
    if (isAuthenticated) {
      throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
    } else {
      this.isAuthenticated = false;
    }
  }
}
