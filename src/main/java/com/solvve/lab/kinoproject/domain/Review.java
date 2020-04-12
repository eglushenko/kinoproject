package com.solvve.lab.kinoproject.domain;

import com.solvve.lab.kinoproject.enums.ReviewStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Review extends AbstractEntity {

    private String reviewText;

    @ManyToOne
    private Film film;

    @ManyToOne
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private ReviewStatus status;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

}
