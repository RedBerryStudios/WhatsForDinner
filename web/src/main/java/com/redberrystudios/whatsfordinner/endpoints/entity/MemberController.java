package com.redberrystudios.whatsfordinner.endpoints.entity;

import com.redberrystudios.whatsfordinner.jwt.JwtTokenUtil;
import com.redberrystudios.whatsfordinner.member.Member;
import com.redberrystudios.whatsfordinner.member.MemberService;
import com.redberrystudios.whatsfordinner.security.jwt.JwtAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private MemberService memberService;

  @GetMapping
  public MemberResponse getAllMembersForGroup() {
    JwtAuthenticationToken authenticationToken =
        (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

    String jwtToken = (String) authenticationToken.getCredentials();

    Long groupId = jwtTokenUtil.getGroupIdFromToken(jwtToken);

    return new MemberResponse(memberService.findAllByGroup(groupId));
  }

  private class MemberResponse {
    private List<Member> members;

    public MemberResponse(List<Member> members) {
      this.members = members;
    }

    public List<Member> getMembers() {
      return members;
    }
  }

}
