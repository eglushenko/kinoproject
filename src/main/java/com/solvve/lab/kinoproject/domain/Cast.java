package com.solvve.lab.kinoproject.domain;

import com.solvve.lab.kinoproject.enums.NameFilmRole;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
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
}
