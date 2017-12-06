package com.redberrystudios.whatsfordinner.endpoints.authentication;

import com.redberrystudios.whatsfordinner.jwt.JwtTokenUtil;
import com.redberrystudios.whatsfordinner.member.Member;
import com.redberrystudios.whatsfordinner.member.MemberService;
import com.redberrystudios.whatsfordinner.security.authenticators.ThirdPartyAuthentication;
import com.redberrystudios.whatsfordinner.security.authenticators.ThirdPartyAuthenticatorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private MemberService memberService;

  @Autowired
  private ThirdPartyAuthenticatorStore authenticatorStore;

  @RequestMapping(path = "/${jwt.route.authentication.path}", method = RequestMethod.POST)
  public AuthResponse authenticate(@RequestBody AuthenticationRequest authRequest) {

    ThirdPartyAuthentication thirdPartyAuthentication =
        authenticatorStore.authenticate(authRequest.getProvider(), authRequest.getCode());

    Member member = memberService.findByEmail(thirdPartyAuthentication.getEmail());

    if (member == null) {
      member = new Member();
      member.setEmail(thirdPartyAuthentication.getEmail());
      member.setName(thirdPartyAuthentication.getName());
      member.setPictureLink(thirdPartyAuthentication.getProfileImage());
      Long newMemberId = memberService.save(member);

      member = memberService.find(newMemberId);
    }

    String jwt = jwtTokenUtil.generateToken(member);

    return new AuthResponse(jwt);
  }

  private class AuthResponse {

    private String jwt;

    AuthResponse(String jwt) {
      this.jwt = jwt;
    }

    public String getJwt() {
      return jwt;
    }
  }

}
