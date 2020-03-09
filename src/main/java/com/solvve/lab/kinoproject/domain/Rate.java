package com.solvve.lab.kinoproject.domain;

import com.solvve.lab.kinoproject.enums.RateObjectType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;


@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Rate {

    @Id
    @GeneratedValue
    private UUID id;

    private Double rate;

    private UUID ratedObjectId;

    @Enumerated(EnumType.STRING)
    private RateObjectType type;

    @ManyToOne
    private Customer customer;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

}
