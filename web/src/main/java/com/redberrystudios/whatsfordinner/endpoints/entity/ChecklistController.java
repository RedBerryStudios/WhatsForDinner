package com.redberrystudios.whatsfordinner.endpoints.entity;

import com.redberrystudios.whatsfordinner.checklist.Checklist;
import com.redberrystudios.whatsfordinner.checklist.ChecklistService;
import com.redberrystudios.whatsfordinner.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/checklists")
public class ChecklistController {

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private ChecklistService checklistService;

  @GetMapping
  public ResponseEntity<?> getAllChecklists() {
    String jwt = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
    Long groupId = jwtTokenUtil.getGroupIdFromToken(jwt);

    return new ResponseEntity<>(new ChecklistResponse(checklistService.findAllByGroup(groupId)), HttpStatus.OK);
  }

  @GetMapping("/{listId}")
  public ResponseEntity<?> getChecklistWithId(@PathVariable Long listId) {
    String jwt = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
    Long groupId = jwtTokenUtil.getGroupIdFromToken(jwt);

    return new ResponseEntity<>(new ChecklistResponse(checklistService.findByGroup(groupId, listId)), HttpStatus.OK);
  }

  private static class ChecklistResponse {

    private List<Checklist> checklists;

    public ChecklistResponse(Checklist checklist) {
      this.checklists = Collections.singletonList(checklist);
    }

    public ChecklistResponse(List<Checklist> checklists) {
      this.checklists = checklists;
    }

    public List<Checklist> getChecklists() {
      return checklists;
    }
  }
}
