package com.redberrystudios.whatsfordinner.task;

import com.redberrystudios.whatsfordinner.board.Board;
import com.redberrystudios.whatsfordinner.board.BoardService;
import com.redberrystudios.whatsfordinner.group.DayElement;
import com.redberrystudios.whatsfordinner.group.Group;
import com.redberrystudios.whatsfordinner.group.GroupService;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TaskService {

  private GroupService groupService;

  private BoardService boardService;

  @Autowired
  public TaskService(GroupService groupService,
      BoardService boardService) {
    this.groupService = groupService;
    this.boardService = boardService;
  }

  @Scheduled(cron = "0 0 23 * * ?")
  public void updateDays() {

    for (Group g : groupService.findAll()) {

      List<DayElement> newDays = g.getDays();
      newDays.remove(0);

      DayElement lastDay = newDays.get(newDays.size() - 1);

      DayElement newDayElement = new DayElement();

      newDayElement.setDate(getNextDay(lastDay.getDate()));

      List<Board> newBoards = new ArrayList<>();

      for (Board b : lastDay.getBoards()) {
        String name = b.getName();

        Board board = new Board();
        board.setName(name);

        boardService.save(board);

        newBoards.add(board);
      }

      newDayElement.setBoards(newBoards);

      newDays.add(newDayElement);

      g.setDays(newDays);

      groupService.save(g);
    }
  }

  private Date getNextDay(Date day){
    LocalDate nextDayDate = day.toInstant().atZone(ZoneId.systemDefault())
        .toLocalDate().plusDays(1);

    return Date.from(nextDayDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

  }
}
