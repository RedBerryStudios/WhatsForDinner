package com.redberrystudios.whatsfordinner.day;

import com.redberrystudios.whatsfordinner.board.Board;
import com.redberrystudios.whatsfordinner.board.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DayService {

  private DayMongoRepository dayMongoRepository;

  private BoardService boardService;

  @Autowired
  public DayService(DayMongoRepository dayMongoRepository, BoardService boardService) {
    this.dayMongoRepository = dayMongoRepository;
    this.boardService = boardService;
  }

  public Day find(Long dayId) {
    return persistenceToService(dayMongoRepository.find(dayId));
  }

  public Day findByGroup(Long groupId, Long dayId) {
    return persistenceToService(dayMongoRepository.findByGroup(groupId, dayId));
  }

  public List<Day> findAllByGroup(Long groupId) {
    return dayMongoRepository.findAllByGroup(groupId).stream()
        .map(this::persistenceToService)
        .collect(Collectors.toList());
  }

  public void delete(Day day) {
    dayMongoRepository.delete(serviceToPersistence(day));
  }

  public void save(Day day) {
    dayMongoRepository.save(serviceToPersistence(day));
  }

  private Day persistenceToService(DayEntity dayEntity) {
    Day day = new Day();

    day.setId(dayEntity.getId());
    day.setDate(dayEntity.getDate());

    List<Board> boards = dayEntity.getBoards().stream()
        .map(boardService::find)
        .collect(Collectors.toList());
    day.setBoards(boards);

    return day;
  }

  private DayEntity serviceToPersistence(Day day) {
    DayEntity dayEntity = new DayEntity();

    dayEntity.setId(day.getId());
    dayEntity.setDate(day.getDate());

    List<Long> boardIds = day.getBoards().stream()
        .map(Board::getId)
        .collect(Collectors.toList());
    dayEntity.setBoards(boardIds);

    return dayEntity;
  }

}
