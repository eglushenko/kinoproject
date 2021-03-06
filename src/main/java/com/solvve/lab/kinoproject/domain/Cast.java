package com.solvve.lab.kinoproject.domain;

import com.solvve.lab.kinoproject.enums.NameFilmRole;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
//TODO 'cast' is a reserved keyword and should be quoted
public class Cast extends AbstractEntity {

    @NotNull
    @Enumerated(EnumType.STRING)
    private NameFilmRole roleInFilm;

    @NotNull
    private String nameRoleInFilm;

    @NotNull
    @ManyToOne
    private Film film;

    @NotNull
    @ManyToOne
    private Name name;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}
