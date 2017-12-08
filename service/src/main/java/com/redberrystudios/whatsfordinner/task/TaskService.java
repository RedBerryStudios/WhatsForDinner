package com.redberrystudios.whatsfordinner.task;

import com.redberrystudios.whatsfordinner.board.Board;
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

  @Autowired
  public TaskService(GroupService groupService) {
    this.groupService = groupService;
  }

  @Scheduled(cron = "0 0 23 * * ?")
  public void updateDays() {
    System.out.println("Triggered!");
    for (Group g : groupService.findAll()) {

      List<DayElement> newDays = g.getDays();
      newDays.remove(0);

      DayElement lastDay = newDays.get(newDays.size() - 1);

      DayElement newDayElement = new DayElement();

      newDayElement.setDate(getNextDay(lastDay.getDate()));

      List<Board> newBoards = new ArrayList<>(lastDay.getBoards());

      for(Board b : newBoards){
        b.getElements().removeAll(b.getElements());
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
