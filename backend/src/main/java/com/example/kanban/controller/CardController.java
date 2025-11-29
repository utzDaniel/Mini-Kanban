package com.example.kanban.controller;

import com.example.kanban.dto.CardResponse;
import com.example.kanban.dto.CreateCardRequest;
import com.example.kanban.dto.MoveCardRequest;
import com.example.kanban.dto.UpdateCardRequest;
import com.example.kanban.service.CardService;
import org.springframework.web.bind.annotation.*;

@RestController
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/columns/{id}/cards")
    public CardResponse create(@PathVariable Long id, @RequestBody CreateCardRequest request) {
        return cardService.createCard(id, request);
    }

    @PutMapping("/cards/{id}")
    public CardResponse update(@PathVariable Long id, @RequestBody UpdateCardRequest request) {
        return cardService.update(id, request);
    }

    @DeleteMapping("/cards/{id}")
    public void delete(@PathVariable Long id) {
        cardService.delete(id);
    }

    @PatchMapping("/cards/{id}/move")
    public CardResponse move(@PathVariable Long id, @RequestBody MoveCardRequest request) {
        return cardService.moveCard(id, request);
    }
}