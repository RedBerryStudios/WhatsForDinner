package com.redberrystudios.whatsfordinner.endpoints.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.redberrystudios.whatsfordinner.checklist.Checklist;
import com.redberrystudios.whatsfordinner.checklist.ChecklistService;
import com.redberrystudios.whatsfordinner.group.Group;
import com.redberrystudios.whatsfordinner.group.GroupService;
import com.redberrystudios.whatsfordinner.jwt.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checklists")
public class ChecklistController {

  private static Logger logger = LoggerFactory.getLogger(ChecklistController.class);

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private ChecklistService checklistService;

  @Autowired
  private GroupService groupService;

  @GetMapping
  public ResponseEntity<?> getAllChecklists() {
    String jwt = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
    Long groupId = jwtTokenUtil.getGroupIdFromToken(jwt);

    logger.info("GET /checklists | groupId:{}", groupId);

    return new ResponseEntity<>(new ChecklistResponse(checklistService.findAllByGroup(groupId)), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<?> createChecklist(@RequestBody ChecklistResponse checklistResponse) {
    String jwt = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
    Long groupId = jwtTokenUtil.getGroupIdFromToken(jwt);

    Long newListId = checklistService.save(checklistResponse.getChecklist());

    Checklist checklist = checklistService.find(newListId);

    Group group = groupService.find(groupId);
    group.getChecklists().add(checklist);

    groupService.save(group);

    logger.info("POST /checklists | groupId:{} newListId:{}", groupId, newListId);

    return new ResponseEntity<>(new ChecklistResponse(checklist), HttpStatus.CREATED);
  }

  @GetMapping("/{listId}")
  public ResponseEntity<?> getChecklistWithId(@PathVariable Long listId) {
    String jwt = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
    Long groupId = jwtTokenUtil.getGroupIdFromToken(jwt);

    logger.info("GET /checklists/{} | groupId:{}", listId, groupId);

    return new ResponseEntity<>(new ChecklistResponse(checklistService.findByGroup(groupId, listId)), HttpStatus.OK);
  }

  @PutMapping("/{listId}")
  public ResponseEntity<?> saveList(@PathVariable("listId") Long listId, @RequestBody ChecklistResponse checklistResponse) {
    String jwt = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
    Long groupId = jwtTokenUtil.getGroupIdFromToken(jwt);

    Checklist checklistForGroup = checklistService.findByGroup(groupId, listId);
    if (checklistForGroup != null) {
      Checklist checklist = checklistResponse.getChecklist();
      checklist.setId(listId);

      logger.info("PUT /checklists/{} | groupId:{} authorized:true", listId, groupId);

      checklistService.save(checklist);

      return new ResponseEntity<>(new ChecklistResponse(checklistService.find(listId)), HttpStatus.OK);
    } else {
      logger.info("PUT /checklists/{} | groupId:{} authorized:false", listId, groupId);

      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
  }

  @DeleteMapping("/{listId}")
  public ResponseEntity<?> deleteChecklist(@PathVariable("listId") Long listId) {
    String jwt = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
    Long groupId = jwtTokenUtil.getGroupIdFromToken(jwt);

    Checklist checklistForGroup = checklistService.findByGroup(groupId, listId);
    if (checklistForGroup != null) {
      logger.info("DELETE /checklists/{} | groupId:{} authorized:true", listId, groupId);

      Group group = groupService.find(groupId);
      group.getChecklists().removeIf(list -> list.getId().equals(listId));
      groupService.save(group);

      checklistService.delete(checklistForGroup);

      return new ResponseEntity<>("{}", HttpStatus.OK);
    } else {
      logger.info("DELETE /checklists/{} | groupId:{} authorized:false", listId, groupId);

      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  private static class ChecklistResponse {

    private Checklist checklist;

    private List<Checklist> checklists;

    public ChecklistResponse() {
    }

    public ChecklistResponse(Checklist checklist) {
      this.checklist = checklist;
    }

    public ChecklistResponse(List<Checklist> checklists) {
      this.checklists = checklists;
    }

    public List<Checklist> getChecklists() {
      return checklists;
    }

    public void setChecklists(List<Checklist> checklists) {
      this.checklists = checklists;
    }

    public Checklist getChecklist() {
      return checklist;
    }

    public void setChecklist(Checklist checklist) {
      this.checklist = checklist;
    }
  }
}
