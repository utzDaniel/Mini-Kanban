package com.example.kanban.service;

import com.example.kanban.domain.model.BoardColumn;
import com.example.kanban.domain.model.Card;
import com.example.kanban.dto.CardResponse;
import com.example.kanban.dto.CreateCardRequest;
import com.example.kanban.dto.MoveCardRequest;
import com.example.kanban.dto.UpdateCardRequest;
import com.example.kanban.repository.CardRepository;
import com.example.kanban.repository.ColumnRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CardService {

    private final CardRepository cardRepository;
    private final ColumnRepository columnRepository;

    public CardService(CardRepository cardRepository,
                       ColumnRepository columnRepository) {
        this.cardRepository = cardRepository;
        this.columnRepository = columnRepository;
    }

    @Transactional
    public CardResponse createCard(Long columnId, CreateCardRequest request) {
        BoardColumn column = columnRepository.findById(columnId)
                .orElseThrow(() -> new RuntimeException("Column não encontrado"));

        Card card = new Card();
        card.setTitle(request.title());
        card.setDescription(request.description());
        card.setColumn(column);

        Card saved = cardRepository.save(card);

        return new CardResponse(saved.getId(), saved.getTitle(), saved.getDescription());
    }

    @Transactional
    public CardResponse update(Long cardId, UpdateCardRequest request) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card não encontrado"));

        card.setTitle(request.title());
        card.setDescription(request.description());

        return new CardResponse(card.getId(), card.getTitle(), card.getDescription());
    }

    @Transactional
    public void delete(Long cardId) {
        cardRepository.deleteById(cardId);
    }

    @Transactional
    public CardResponse moveCard(Long cardId, MoveCardRequest request) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card não encontrado"));

        BoardColumn newColumn = columnRepository.findById(request.newColumnId())
                .orElseThrow(() -> new RuntimeException("Column informada não encontrado"));

        card.setColumn(newColumn);

        return new CardResponse(card.getId(), card.getTitle(), card.getDescription());
    }
}