package com.redberrystudios.whatsfordinner.endpoints.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.redberrystudios.whatsfordinner.board.Board;
import com.redberrystudios.whatsfordinner.board.BoardElement;
import com.redberrystudios.whatsfordinner.board.BoardService;
import com.redberrystudios.whatsfordinner.jwt.JwtTokenUtil;
import com.redberrystudios.whatsfordinner.member.Member;
import com.redberrystudios.whatsfordinner.member.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/boards")
public class BoardController {

  private static Logger logger = LoggerFactory.getLogger(BoardController.class);

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private BoardService boardService;

  @Autowired
  private MemberService memberService;

  @GetMapping
  public ResponseEntity<?> getBoardsForGroup() {
    String jwt = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
    Long groupId = jwtTokenUtil.getGroupIdFromToken(jwt);

    return new ResponseEntity<>(new BoardResponse(boardService.findAllByGroup(groupId)), HttpStatus.OK);
  }

  @GetMapping("/{boardId}")
  public ResponseEntity<?> getBoardWithId(@PathVariable Long boardId) {
    String jwt = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
    Long groupId = jwtTokenUtil.getGroupIdFromToken(jwt);

    return new ResponseEntity<>(new BoardResponse(boardService.findByGroup(groupId, boardId)), HttpStatus.OK);
  }

  @PutMapping("/{boardId}")
  public ResponseEntity<?> modifyBoardWithId(@PathVariable Long boardId, @RequestBody BoardResponse boardResponse) {
    String jwt = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
    Long groupId = jwtTokenUtil.getGroupIdFromToken(jwt);

    Board boardForGroup = boardService.findByGroup(groupId, boardId);
    if (boardForGroup != null) {
      Board board = boardResponse.getBoard();
      board.setId(boardId);

      logger.info("PUT /boards/{} | groupId:{} authorized:true", boardId, groupId);

      boardService.save(board);

      return new ResponseEntity<>(new BoardResponse(boardService.find(boardId)), HttpStatus.OK);
    } else {
      logger.info("PUT /boards/{} | groupId:{} authorized:false", boardId, groupId);

      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
  }

  @PostMapping("/{boardId}/subscribe")
  public ResponseEntity<?> subscribeToBoardElement(@PathVariable("boardId") Long boardId,
                                                   @RequestBody BoardElementSubscriptionRequest subscriptionRequest) {
    String jwt = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
    Long groupId = jwtTokenUtil.getGroupIdFromToken(jwt);
    Long memberId = jwtTokenUtil.getUserIdFromToken(jwt);

    Board board = boardService.findByGroup(groupId, boardId);

    if (board != null) {
      List<BoardElement> elements = board.getElements();

      for (BoardElement boardElement : elements) {
        boardElement.getSubscribers().removeIf(s -> s.getId().equals(memberId));

        if (boardElement.getId().equals(subscriptionRequest.getBoardElementId())) {
          Member member = memberService.find(memberId);
          boardElement.getSubscribers().add(member);
        }
      }

      logger.info("POST /boards/{}/subscribe | groupId:{} memberId:{} authorized:true", boardId, groupId, memberId);

      boardService.save(board);

      return new ResponseEntity<>("{}", HttpStatus.OK);
    } else {
      logger.info("POST /boards/{}/subscribe | groupId:{} memberId:{} authorized:false", boardId, groupId, memberId);

      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  private static class BoardResponse {
    private Board board;

    private List<Board> boards;

    public BoardResponse() {
    }

    public BoardResponse(Board board) {
      this.boards = Collections.singletonList(board);
    }

    public BoardResponse(List<Board> boards) {
      this.boards = boards;
    }

    public List<Board> getBoards() {
      return boards;
    }

    public Board getBoard() {
      return board;
    }

    public void setBoard(Board board) {
      this.board = board;
    }

    public void setBoards(List<Board> boards) {
      this.boards = boards;
    }
  }
}
