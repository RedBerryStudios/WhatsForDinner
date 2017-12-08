package com.redberrystudios.whatsfordinner.endpoints.entity;

import com.redberrystudios.whatsfordinner.group.Group;
import com.redberrystudios.whatsfordinner.group.GroupService;
import com.redberrystudios.whatsfordinner.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private GroupService groupService;

  @GetMapping("/{groupId}")
  public ResponseEntity<GroupResponse> getGroupWithId(@PathVariable("groupId") Long groupId) {
    return new ResponseEntity<>(new GroupResponse(groupService.find(groupId)), HttpStatus.OK);
  }

  private static class GroupResponse {
    private List<Group> groups;

    public GroupResponse(Group groups) {
      this.groups = Collections.singletonList(groups);
    }

    public GroupResponse(List<Group> groups) {
      this.groups = groups;
    }

    public List<Group> getGroups() {
      return groups;
    }
  }
}
