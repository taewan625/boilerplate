package com.exporum.admin.domain.board.controller;

import com.exporum.admin.domain.board.model.Board;
import com.exporum.admin.domain.board.model.BoardDTO;
import com.exporum.admin.domain.board.model.PageableBoard;
import com.exporum.admin.domain.board.service.BoardService;
import com.exporum.core.enums.OperationStatus;
import com.exporum.core.response.ContentsResponse;
import com.exporum.core.response.OperationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 17.
 * @description :
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/${application.api.admin}")
public class BoardRestController {

    private final BoardService boardService;

    @PostMapping("/board")
    public PageableBoard getPageableAttendee(PageableBoard search) {
        boardService.getPageableBoard(search);
        return search;
    }

    @GetMapping("/board/{id}")
    public ResponseEntity<OperationResponse> getBoard(@PathVariable long id) {
        Board board = boardService.getBoard(id);
        return ResponseEntity.ok(new ContentsResponse<>(OperationStatus.SUCCESS, board));
    }

    @PutMapping("/board/{id}")
    public ResponseEntity<OperationResponse> updateBoard(@PathVariable long id, @Valid @ModelAttribute BoardDTO board) {
        boardService.updateBoardProcess(id, board);
        return ResponseEntity.ok(new OperationResponse(OperationStatus.SUCCESS));
    }

    @PutMapping("/board/{id}/disable")
    public ResponseEntity<OperationResponse> disableBoard(@PathVariable long id, @RequestBody BoardDTO board) {
        boardService.updateBoardDisable(id, board);
        return ResponseEntity.ok(new OperationResponse(OperationStatus.SUCCESS));
    }

    @DeleteMapping("/board/{id}")
    public ResponseEntity<OperationResponse> deleteBoard(@PathVariable long id) {
        boardService.deleteBoard(id);
        return ResponseEntity.ok(new OperationResponse(OperationStatus.SUCCESS));
    }

    @PostMapping("/board/insert")
    public ResponseEntity<OperationResponse> createBoard(@Valid @ModelAttribute BoardDTO board) {
        boardService.insertBoardProcess(board);
        return ResponseEntity.ok(new OperationResponse(OperationStatus.SUCCESS));
    }



}
