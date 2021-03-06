package com.solvve.lab.kinoproject.dto.comment;

import com.solvve.lab.kinoproject.enums.CommentStatus;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class CommentReadDTO {
    private UUID id;
    private String commentText;
    private LocalDate postedDate;
    private CommentStatus commentStatus;
    private Double rate;
    private Instant createdAt;
    private Instant updatedAt;
}
