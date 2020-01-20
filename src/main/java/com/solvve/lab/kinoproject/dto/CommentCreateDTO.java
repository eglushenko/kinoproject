package com.solvve.lab.kinoproject.dto;

import com.solvve.lab.kinoproject.enums.CommentStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CommentCreateDTO {
    private String commentText;
    private LocalDate postedDate;
    private CommentStatus commentStatus;
    private float rate;
}
