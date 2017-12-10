package com.redberrystudios.whatsfordinner.board;

import com.redberrystudios.whatsfordinner.generator.IdentifierGeneratorService;
import com.redberrystudios.whatsfordinner.member.Member;
import com.redberrystudios.whatsfordinner.member.MemberService;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

  private BoardMongoRepository boardMongoRepository;

  private MemberService memberService;

  private IdentifierGeneratorService identifierGeneratorService;

  @Autowired
  public BoardService(BoardMongoRepository boardMongoRepository, MemberService memberService,
      IdentifierGeneratorService identifierGeneratorService) {
    this.boardMongoRepository = boardMongoRepository;
    this.memberService = memberService;
    this.identifierGeneratorService = identifierGeneratorService;
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

  public Long delete(Board board) {
    return boardMongoRepository.delete(serviceToPersistence(board));
  }

  public Long save(Board board) {

    if (board.getId() == null) {

      Long id = identifierGeneratorService
          .generateLongIdentifier(i -> boardMongoRepository.find(i) != null);

      board.setId(id);
    }

    for(BoardElement element : board.getElements()){
      if (element.getId() == null) {

        Long elementId = identifierGeneratorService
            .generateLongIdentifier(i -> {
              for(BoardElement e : board.getElements()) {
                if (e.getId() != null && e.getId().equals(i))
                  return true;
              }
              return false;
            });

        element.setId(elementId);
      }
    }

    return boardMongoRepository.save(serviceToPersistence(board));
  }

  private Board persistenceToService(BoardEntity boardEntity) {
    if (boardEntity == null) {
      return null;
    }

    Board board = new Board();

    board.setId(boardEntity.getId());
    board.setName(boardEntity.getName());

    List<BoardElement> elements = boardEntity.getElements().stream()
        .map(entity -> {
          List<Member> subs = entity.getSubscribers().stream()
              .map(memberService::find)
              .collect(Collectors.toList());
          return new BoardElement(entity.getId(), entity.getName(), entity.getCategory(),
              memberService.find(entity.getProducer()), subs);
        })
        .collect(Collectors.toList());
    board.setElements(elements);

    return board;
  }

  private BoardEntity serviceToPersistence(Board board) {
    if (board == null) {
      return null;
    }

    BoardEntity boardEntity = new BoardEntity();

    boardEntity.setId(board.getId());
    boardEntity.setName(board.getName());

    List<BoardElementEntity> elements = board.getElements().stream()
        .map(serviceModel -> {
          BoardElementEntity entity = new BoardElementEntity(serviceModel.getId(),
              serviceModel.getName(), serviceModel.getCategory(),
              serviceModel.getProducer().getId());
          entity.getSubscribers().addAll(serviceModel.getSubscribers().stream().map(Member::getId)
              .collect(Collectors.toList()));

          return entity;
        })
        .collect(Collectors.toList());
    boardEntity.setElements(elements);

    return boardEntity;
  }

}
