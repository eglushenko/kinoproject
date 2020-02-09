package com.solvve.lab.kinoproject.domain;

import com.solvve.lab.kinoproject.enums.CommentStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Comment {
    @Id
    @GeneratedValue
    private UUID id;
    private String commentText;
    private LocalDate postedDate;
    @Enumerated(EnumType.STRING)
    private CommentStatus commentStatus;
    private Float rate;
}
