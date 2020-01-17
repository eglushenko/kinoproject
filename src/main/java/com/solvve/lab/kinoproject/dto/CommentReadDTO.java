package com.solvve.lab.kinoproject.dto;

import com.solvve.lab.kinoproject.enums.CommentStatus;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class CommentReadDTO {
    private UUID id;
    private String commentText;
    private LocalDate postedDate;
    @Enumerated(EnumType.STRING)
    private CommentStatus commentStatus;
    private float rate;
}
