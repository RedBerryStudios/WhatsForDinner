package com.redberrystudios.whatsfordinner.endpoints.entity;

import com.redberrystudios.whatsfordinner.board.Board;
import com.redberrystudios.whatsfordinner.board.BoardService;
import com.redberrystudios.whatsfordinner.jwt.JwtTokenUtil;
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

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private BoardService boardService;

  @GetMapping
  public ResponseEntity<?> getBoardsForGroup() {
    String jwt = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
    Long groupId = jwtTokenUtil.getGroupIdFromToken(jwt);

    return new ResponseEntity<>(new BoardResponse(boardService.findAllByGroup(groupId)), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<?> createNewBoard(@RequestBody Board board) {
    boardService.save(board);

    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping("/{boardId}")
  public ResponseEntity<?> getBoardWithId(@PathVariable Long boardId) {
    String jwt = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
    Long groupId = jwtTokenUtil.getGroupIdFromToken(jwt);

    return new ResponseEntity<>(new BoardResponse(boardService.findByGroup(groupId, boardId)), HttpStatus.OK);
  }

  @PatchMapping("/{boardId}")
  public ResponseEntity<?> modifyBoardWithId(@PathVariable Long boardId) {
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  private static class BoardResponse {
    private List<Board> boards;

    public BoardResponse(Board board) {
      this.boards = Collections.singletonList(board);
    }

    public BoardResponse(List<Board> boards) {
      this.boards = boards;
    }

    public List<Board> getBoards() {
      return boards;
    }
  }
}
