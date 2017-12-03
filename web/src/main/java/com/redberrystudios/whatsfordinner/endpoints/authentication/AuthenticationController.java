package com.redberrystudios.whatsfordinner.endpoints.authentication;

import com.redberrystudios.whatsfordinner.jwt.JwtTokenUtil;
import com.redberrystudios.whatsfordinner.member.Member;
import com.redberrystudios.whatsfordinner.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private MemberService memberService;

  @RequestMapping(path = "/${jwt.route.authentication.path}", method = RequestMethod.POST)
  public AuthResponse authenticate() {

    //TODO
    String email = "newEmail@email.com";

    Member member = memberService.findByEmail(email);

    if (member == null) {
      member = new Member();
      member.setEmail(email);
      member.setName(email);
      member.setPictureLink("");
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
