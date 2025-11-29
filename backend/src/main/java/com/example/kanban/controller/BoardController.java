package com.example.kanban.controller;

import com.example.kanban.dto.BoardResponse;
import com.example.kanban.dto.ColumnResponse;
import com.example.kanban.dto.CreateBoardRequest;
import com.example.kanban.dto.CreateColumnRequest;
import com.example.kanban.service.BoardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping
    public BoardResponse createBoard(@RequestBody CreateBoardRequest request) {
        return boardService.createBoard(request);
    }

    @GetMapping
    public List<BoardResponse> listBoards() {
        return boardService.listBoards();
    }

    @GetMapping("/{id}")
    public BoardResponse getBoard(@PathVariable Long id) {
        return boardService.getBoard(id);
    }

    @PostMapping("/{id}/columns")
    public ColumnResponse createColumn(
            @PathVariable Long id,
            @RequestBody CreateColumnRequest request
    ) {
        return boardService.createColumn(id, request);
    }
}
