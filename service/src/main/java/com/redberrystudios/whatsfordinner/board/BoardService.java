package com.redberrystudios.whatsfordinner.board;

import com.redberrystudios.whatsfordinner.member.Member;
import com.redberrystudios.whatsfordinner.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardService {

  private BoardMongoRepository boardMongoRepository;

  private MemberService memberService;

  @Autowired
  public BoardService(BoardMongoRepository boardMongoRepository, MemberService memberService) {
    this.boardMongoRepository = boardMongoRepository;
    this.memberService = memberService;
  }

  public Board find(Long boardId) {
    return persistenceToService(boardMongoRepository.find(boardId));
  }

  public Board findByGroup(Long groupId, Long boardId) {
    return persistenceToService(boardMongoRepository.findByGroup(groupId, boardId));
  }

  public List<Board> findAllByGroup(Long groupId) {
    return boardMongoRepository.findAllByGroup(groupId)
        .stream()
        .map(this::persistenceToService)
        .collect(Collectors.toList());
  }

  public void delete(Board board) {
    boardMongoRepository.delete(serviceToPersistence(board));
  }

  public void save(Board board) {
    boardMongoRepository.save(serviceToPersistence(board));
  }

  private Board persistenceToService(BoardEntity boardEntity) {
    Board board = new Board();

    board.setId(boardEntity.getId());
    board.setName(boardEntity.getName());

    List<BoardElement> elements = boardEntity.getElements().stream()
        .map(entity -> {
          List<Member> subs = entity.getSubscribers().stream()
              .map(memberService::find)
              .collect(Collectors.toList());
          return new BoardElement(entity.getId(), entity.getName(), entity.getCategory(), memberService.find(entity.getProducer()), subs);
        })
        .collect(Collectors.toList());
    board.setElements(elements);

    return board;
  }

  private BoardEntity serviceToPersistence(Board board) {
    BoardEntity boardEntity = new BoardEntity();

    boardEntity.setId(board.getId());
    boardEntity.setName(board.getName());

    List<BoardElementEntity> elements = board.getElements().stream()
        .map(serviceModel -> {
          BoardElementEntity entity = new BoardElementEntity(serviceModel.getId(), serviceModel.getName(), serviceModel.getCategory(), serviceModel.getProducer().getId());
          entity.getSubscribers().addAll(serviceModel.getSubscribers().stream().map(sub -> sub.getId()).collect(Collectors.toList()));

          return entity;
        })
        .collect(Collectors.toList());
    boardEntity.setElements(elements);

    return boardEntity;
  }

}
