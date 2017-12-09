package com.redberrystudios.whatsfordinner.group;

import static java.util.stream.Collectors.toList;

import com.redberrystudios.whatsfordinner.board.Board;
import com.redberrystudios.whatsfordinner.board.BoardService;
import com.redberrystudios.whatsfordinner.checklist.Checklist;
import com.redberrystudios.whatsfordinner.checklist.ChecklistService;
import com.redberrystudios.whatsfordinner.generator.IdentifierGeneratorService;
import com.redberrystudios.whatsfordinner.member.Member;
import com.redberrystudios.whatsfordinner.member.MemberService;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

  private GroupMongoRepository groupMongoRepository;

  private ChecklistService checklistService;

  private MemberService memberService;

  private BoardService boardService;

  private IdentifierGeneratorService identifierGeneratorService;

  @Autowired
  public GroupService(GroupMongoRepository groupMongoRepository,
      @Lazy ChecklistService checklistService,
      @Lazy MemberService memberService,
      @Lazy BoardService boardService,
      IdentifierGeneratorService identifierGeneratorService) {
    this.groupMongoRepository = groupMongoRepository;
    this.checklistService = checklistService;
    this.memberService = memberService;
    this.boardService = boardService;
    this.identifierGeneratorService = identifierGeneratorService;
    this.identifierGeneratorService = identifierGeneratorService;
  }

  public Group find(Long groupId) {
    return persistenceToService(groupMongoRepository.find(groupId));
  }

  public Group findByJoinToken(String joinToken) {
    return persistenceToService(groupMongoRepository.findByJoinToken(joinToken));
  }

  public Group findByMember(Long memberId) {
    return persistenceToService(groupMongoRepository.findByMember(memberId));
  }

  public List<Group> findAll() {
    return groupMongoRepository.findAll()
        .stream()
        .map(this::persistenceToService)
        .collect(toList());
  }

  public Long delete(Group group) {
    return groupMongoRepository.delete(serviceToPersistence(group));
  }

  public Long save(Group group) {

    if (group.getId() == null) {

      Long groupId = identifierGeneratorService
          .generateLongIdentifier(id -> groupMongoRepository.find(id) != null);

      group.setId(groupId);

      String token = identifierGeneratorService
          .generateStringIdentifier(
              joinToken -> groupMongoRepository.findByJoinToken(joinToken) != null);

      group.setJoinToken(token);

      Checklist checklist = new Checklist();
      checklist.setName("Shopping List");

      checklistService.save(checklist);
      group.setChecklists(Collections.singletonList(checklist));

      List<DayElement> dayElements = new ArrayList<>();
      for (int i = -3; i <= 3; i++) {
        DayElement dayElement = new DayElement();

        LocalDate localDate = LocalDate.now().plusDays(i);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        dayElement.setDate(date);

        Board board1 = new Board();
        board1.setName("Lunch");

        Board board2 = new Board();
        board2.setName("Dinner");

        boardService.save(board1);
        boardService.save(board2);

        List<Board> boards = new ArrayList<>();
        boards.add(board1);
        boards.add(board2);

        dayElement.setBoards(boards);
        dayElements.add(dayElement);
      }

      group.setDays(dayElements);
    }

    return groupMongoRepository.save(serviceToPersistence(group));
  }

  private Group persistenceToService(GroupEntity groupEntity) {
    if (groupEntity == null) {
      return null;
    }

    Group group = new Group();

    group.setId(groupEntity.getId());
    group.setName(groupEntity.getName());
    group.setJoinToken(groupEntity.getJoinToken());

    List<Member> members = groupEntity.getMembers().stream()
        .map(memberService::find)
        .collect(toList());
    group.setMembers(members);

    List<DayElement> days = groupEntity.getDays().stream()
        .map(this::dayPersistenceToService)
        .collect(toList());
    group.setDays(days);

    List<Checklist> checklists = groupEntity.getChecklists().stream()
        .map(checklistService::find)
        .collect(toList());
    group.setChecklists(checklists);

    return group;
  }

  private GroupEntity serviceToPersistence(Group group) {
    if (group == null) {
      return null;
    }

    GroupEntity groupEntity = new GroupEntity();

    groupEntity.setId(group.getId());
    groupEntity.setName(group.getName());
    groupEntity.setJoinToken(group.getJoinToken());

    List<Long> memberIds = group.getMembers().stream()
        .map(Member::getId)
        .collect(toList());
    groupEntity.setMembers(memberIds);

    List<DayElementEntity> days = group.getDays().stream()
        .map(this::dayServiceToPersistence)
        .collect(toList());
    groupEntity.setDays(days);

    List<Long> checklistIds = group.getChecklists().stream()
        .map(Checklist::getId)
        .collect(toList());
    groupEntity.setChecklists(checklistIds);

    return groupEntity;
  }

  private DayElement dayPersistenceToService(DayElementEntity entity) {
    DayElement dayElement = new DayElement();

    dayElement.setDate(entity.getDate());
    dayElement.setBoards(entity.getBoards().stream()
        .map(boardService::find)
        .collect(toList())
    );

    return dayElement;
  }

  private DayElementEntity dayServiceToPersistence(DayElement dayElement) {
    DayElementEntity dayElementEntity = new DayElementEntity();

    dayElementEntity.setDate(dayElement.getDate());
    dayElementEntity.setBoards(dayElement.getBoards().stream()
        .map(Board::getId)
        .collect(toList())
    );

    return dayElementEntity;
  }

}
