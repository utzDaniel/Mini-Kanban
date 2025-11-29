package com.example.kanban.dto;

public record CardResponse(
        Long id,
        String title,
        String description
) { }
