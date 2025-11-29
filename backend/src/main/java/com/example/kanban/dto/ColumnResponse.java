package com.example.kanban.dto;

import java.util.List;

public record ColumnResponse(
        Long id,
        String name,
        Integer position,
        List<CardResponse> cards
) { }