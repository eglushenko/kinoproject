package com.solvve.lab.kinoproject.domain;

import com.solvve.lab.kinoproject.enums.CommentStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Comment extends AbstractEntity {

    private String commentText;

    private LocalDate postedDate;

    @Enumerated(EnumType.STRING)
    private CommentStatus commentStatus;

    private Double rate;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}
