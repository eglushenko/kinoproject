package com.solvve.lab.kinoproject.domain;

import com.solvve.lab.kinoproject.enums.TypoStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Typo {
    @Id
    @GeneratedValue
    private UUID id;

    private String typoMessege;

    private String typoLink;

    @Enumerated(EnumType.STRING)
    private TypoStatus status;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

}
