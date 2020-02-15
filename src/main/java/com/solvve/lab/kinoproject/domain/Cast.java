package com.solvve.lab.kinoproject.domain;

import com.solvve.lab.kinoproject.enums.NameFilmRole;
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
//TODO 'cast' is a reserved keyword and should be quoted
public class Cast {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Name name;

    @Enumerated(EnumType.STRING)
    private NameFilmRole roleInFilm;

    private String nameRoleInFilm;

    @ManyToOne
    //@JoinColumn(nullable = false,updatable = false)
    private Film film;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}
