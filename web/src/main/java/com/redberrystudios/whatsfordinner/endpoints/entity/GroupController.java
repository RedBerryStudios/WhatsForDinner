package com.redberrystudios.whatsfordinner.endpoints.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.redberrystudios.whatsfordinner.group.Group;
import com.redberrystudios.whatsfordinner.group.GroupService;
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

import java.util.Collections;

@RestController
@RequestMapping("/groups")
public class GroupController {

  private static Logger logger = LoggerFactory.getLogger(GroupController.class);

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private GroupService groupService;

  @Autowired
  private MemberService memberService;

  @PostMapping
  public ResponseEntity<?> createNewGoup(@RequestBody GroupResponse groupResponse) {
    String jwt = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
    Long memberId = jwtTokenUtil.getUserIdFromToken(jwt);

    Group group = groupResponse.getGroup();
    group.setMembers(Collections.singletonList(memberService.find(memberId)));

    logger.info("POST /groups | memberId:{}", memberId);

    Long newGroupId = groupService.save(group);

    return new ResponseEntity<>(new GroupResponse(groupService.find(newGroupId)), HttpStatus.CREATED);
  }

  @PostMapping("/join")
  public ResponseEntity<?> joinGroup(@RequestBody GroupJoinRequest groupJoinRequest) {
    String jwt = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
    Long memberId = jwtTokenUtil.getUserIdFromToken(jwt);

    if (groupService.findByMember(memberId) != null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    Group group = groupService.findByJoinToken(groupJoinRequest.getJoinToken());

    Member member = memberService.find(memberId);

    if (!group.getMembers().contains(member)) {
      group.getMembers().add(member);

      groupService.save(group);
    }

    return new ResponseEntity<>("{}", HttpStatus.OK);
  }

  @GetMapping("/{groupId}")
  public ResponseEntity<GroupResponse> getGroupWithId(@PathVariable("groupId") Long groupId) {
    String jwt = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
    Long tokenGroupId = jwtTokenUtil.getGroupIdFromToken(jwt);

    if (groupId.equals(tokenGroupId)) {
      logger.info("GET /groups/{} | authorized:true", groupId);

      return new ResponseEntity<>(new GroupResponse(groupService.find(groupId)), HttpStatus.OK);
    } else {
      logger.info("GET /groups/{} | authorized:false", groupId);

      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
  }

  @PutMapping("/{groupId}")
  public ResponseEntity<?> saveGroup(@RequestBody GroupResponse groupResponse, @PathVariable("groupId") Long groupId) {
    String jwt = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
    Long tokenGroupId = jwtTokenUtil.getGroupIdFromToken(jwt);

    if (groupId.equals(tokenGroupId)) {
      Group group = groupResponse.getGroup();
      group.setId(groupId);

      logger.info("PUT /groups/{} | authorized:true", groupId);

      groupService.save(group);

      return new ResponseEntity<>(new GroupResponse(groupService.find(groupId)), HttpStatus.OK);
    } else {
      logger.info("PUT /groups/{} | authorized:false", groupId);

      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  private static class GroupResponse {

    private Group group;

    public GroupResponse() {
    }

    public GroupResponse(Group group) {
      this.group = group;
    }

    public Group getGroup() {
      return group;
    }

    public void setGroup(Group group) {
      this.group = group;
    }
  }
}
