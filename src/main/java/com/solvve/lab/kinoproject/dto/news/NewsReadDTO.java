package com.solvve.lab.kinoproject.dto.news;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class NewsReadDTO {
    private UUID id;
    private String textNews;
    private Instant createdAt;
    private Instant updatedAt;
}
