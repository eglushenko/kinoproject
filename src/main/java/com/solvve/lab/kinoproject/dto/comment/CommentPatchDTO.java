package com.solvve.lab.kinoproject.dto.comment;

import com.solvve.lab.kinoproject.enums.CommentStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CommentPatchDTO {
    private String commentText;
    private LocalDate postedDate;
    private CommentStatus commentStatus;
    private float rate;
}
