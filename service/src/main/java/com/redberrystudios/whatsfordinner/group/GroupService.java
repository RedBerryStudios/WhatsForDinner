package com.redberrystudios.whatsfordinner.group;

import com.redberrystudios.whatsfordinner.checklist.Checklist;
import com.redberrystudios.whatsfordinner.checklist.ChecklistService;
import com.redberrystudios.whatsfordinner.day.Day;
import com.redberrystudios.whatsfordinner.day.DayService;
import com.redberrystudios.whatsfordinner.member.Member;
import com.redberrystudios.whatsfordinner.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class GroupService {

  private GroupMongoRepository groupMongoRepository;

  private DayService dayService;

  private ChecklistService checklistService;

  private MemberService memberService;

  @Autowired
  public GroupService(GroupMongoRepository groupMongoRepository, DayService dayService, ChecklistService checklistService, MemberService memberService) {
    this.groupMongoRepository = groupMongoRepository;
    this.dayService = dayService;
    this.checklistService = checklistService;
    this.memberService = memberService;
  }

  public Group find(Long groupId) {
    return persistenceToService(groupMongoRepository.find(groupId));
  }

  public Group findByJoinToken(String joinToken) {
    return persistenceToService(groupMongoRepository.findByJoinToken(joinToken));
  }

  private Group persistenceToService(GroupEntity groupEntity) {
    Group group = new Group();

    group.setId(groupEntity.getId());
    group.setName(groupEntity.getName());
    group.setJoinToken(groupEntity.getJoinToken());

    List<Member> members = groupEntity.getMembers().stream()
        .map(memberService::find)
        .collect(toList());
    group.setMembers(members);

    List<Day> days = groupEntity.getDays().stream()
        .map(dayService::find)
        .collect(toList());
    group.setDays(days);

    List<Checklist> checklists = groupEntity.getChecklists().stream()
        .map(checklistService::find)
        .collect(toList());
    group.setChecklists(checklists);

    return group;
  }

  private GroupEntity serviceToPersistence(Group group) {
    GroupEntity groupEntity = new GroupEntity();

    groupEntity.setId(group.getId());
    groupEntity.setName(group.getName());
    groupEntity.setJoinToken(group.getJoinToken());

    List<Long> memberIds = group.getMembers().stream()
        .map(Member::getId)
        .collect(toList());
    groupEntity.setMembers(memberIds);

    List<Long> dayIds = group.getDays().stream()
        .map(Day::getId)
        .collect(toList());
    groupEntity.setDays(dayIds);

    List<Long> checklistIds = group.getChecklists().stream()
        .map(Checklist::getId)
        .collect(toList());
    groupEntity.setChecklists(checklistIds);

    return groupEntity;
  }

}
