package com.example.kanban.service;

import com.example.kanban.domain.model.Board;
import com.example.kanban.domain.model.BoardColumn;
import com.example.kanban.dto.*;
import com.example.kanban.repository.BoardRepository;
import com.example.kanban.repository.ColumnRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final ColumnRepository columnRepository;

    public BoardService(BoardRepository boardRepository,
                        ColumnRepository columnRepository) {
        this.boardRepository = boardRepository;
        this.columnRepository = columnRepository;
    }

    public BoardResponse createBoard(CreateBoardRequest request) {
        Board board = new Board();
        board.setName(request.name());
        Board saved = boardRepository.save(board);
        return toResponse(saved);
    }

    public java.util.List<BoardResponse> listBoards() {
        return boardRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public BoardResponse getBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board não encontrado"));
        return toResponse(board);
    }

    @Transactional
    public ColumnResponse createColumn(Long boardId, CreateColumnRequest request) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("Board não encontrado"));

        BoardColumn column = new BoardColumn();
        column.setName(request.name());
        column.setPosition(request.position());
        column.setBoard(board);

        BoardColumn saved = columnRepository.save(column);
        return new ColumnResponse(saved.getId(), saved.getName(), saved.getPosition(), java.util.List.of());
    }

    private BoardResponse toResponse(Board board) {
        var columns = board.getColumns()
                .stream()
                .map(col -> new ColumnResponse(
                        col.getId(),
                        col.getName(),
                        col.getPosition(),
                        col.getCards().stream()
                                .map(c -> new CardResponse(c.getId(), c.getTitle(), c.getDescription()))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());

        return new BoardResponse(board.getId(), board.getName(), columns);
    }
}
