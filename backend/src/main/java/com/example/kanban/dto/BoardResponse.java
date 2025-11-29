package com.example.kanban.dto;

import java.util.List;

public record BoardResponse(
        Long id,
        String name,
        List<ColumnResponse> columns
) { }