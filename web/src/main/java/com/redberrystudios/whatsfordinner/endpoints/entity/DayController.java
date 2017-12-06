package com.redberrystudios.whatsfordinner.endpoints.entity;

import com.redberrystudios.whatsfordinner.day.Day;
import com.redberrystudios.whatsfordinner.day.DayService;
import com.redberrystudios.whatsfordinner.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/days")
public class DayController {

  @Autowired
  private DayService dayService;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @GetMapping
  public DaysResponse getDays() {
    String jwt = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();

    Long groupId = jwtTokenUtil.getGroupIdFromToken(jwt);

    System.out.println(groupId);

    return new DaysResponse(dayService.findAllByGroup(groupId));
  }

  private class DaysResponse {
    private List<Day> days;

    public DaysResponse(List<Day> days) {
      this.days = days;
    }

    public List<Day> getDays() {
      return days;
    }

    public void setDays(List<Day> days) {
      this.days = days;
    }
  }

}
