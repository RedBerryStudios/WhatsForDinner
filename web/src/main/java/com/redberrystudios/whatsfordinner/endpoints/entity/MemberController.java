package com.redberrystudios.whatsfordinner.endpoints.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.redberrystudios.whatsfordinner.jwt.JwtTokenUtil;
import com.redberrystudios.whatsfordinner.member.Member;
import com.redberrystudios.whatsfordinner.member.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

  private static Logger logger = LoggerFactory.getLogger(MemberController.class);

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private MemberService memberService;

  @GetMapping
  public MemberResponse getAllMembersForGroup() {
    String jwt = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
    Long groupId = jwtTokenUtil.getGroupIdFromToken(jwt);

    logger.info("GET /members | groupId:{}", groupId);

    return new MemberResponse(memberService.findAllByGroup(groupId));
  }

  @GetMapping("/{memberId}")
  public ResponseEntity<?> getMemberWithId(@PathVariable("memberId") Long memberId) {
    String jwt = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
    Long groupId = jwtTokenUtil.getGroupIdFromToken(jwt);
    Long tokenMemberId = jwtTokenUtil.getUserIdFromToken(jwt);

    logger.info("GET /members/{} | groupId:{}", memberId, groupId);

    Member member;
    if (memberId.equals(tokenMemberId)) {
      member = memberService.find(memberId);
    } else {
      member = memberService.findByGroup(groupId, memberId);
    }

    return new ResponseEntity<>(new MemberResponse(member), HttpStatus.OK);
  }

  @PutMapping("/{memberId}")
  public ResponseEntity<?> saveMember(@PathVariable("memberId") Long memberId, @RequestBody MemberResponse memberResponse) {
    String jwt = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
    Long tokenMemberId = jwtTokenUtil.getUserIdFromToken(jwt);

    if (memberId.equals(tokenMemberId)) {
      Member member = memberResponse.getMember();
      member.setId(memberId);
      logger.info("PUT /members/{} | authorized:true", memberId);

      memberService.save(member);

      return new ResponseEntity<>(new MemberResponse(memberService.find(memberId)), HttpStatus.OK);
    } else {
      logger.info("PUT /members/{} | authorized:false");

      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  private static class MemberResponse {

    private Member member;

    private List<Member> members;

    public MemberResponse() {
    }

    public MemberResponse(Member member) {
      this.member = member;
    }

    public MemberResponse(List<Member> members) {
      this.members = members;
    }

    public List<Member> getMembers() {
      return members;
    }

    public Member getMember() {
      return member;
    }

    public void setMember(Member member) {
      this.member = member;
    }

    public void setMembers(List<Member> members) {
      this.members = members;
    }
  }

}
