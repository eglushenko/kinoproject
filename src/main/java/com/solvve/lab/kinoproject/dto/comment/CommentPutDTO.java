package com.solvve.lab.kinoproject.dto.comment;

import com.solvve.lab.kinoproject.enums.CommentStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CommentPutDTO {
    private String commentText;
    private LocalDate postedDate;
    private CommentStatus commentStatus;
    private Double rate;
}
