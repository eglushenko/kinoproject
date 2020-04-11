package com.solvve.lab.kinoproject.domain;

import com.solvve.lab.kinoproject.enums.LikedObjectType;
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
import java.util.UUID;


@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Like extends AbstractEntity {

    private Boolean like;

    private UUID likedObjectId;

    @Enumerated(EnumType.STRING)
    private LikedObjectType type;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}
