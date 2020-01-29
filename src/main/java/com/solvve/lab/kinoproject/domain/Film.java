package com.solvve.lab.kinoproject.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Getter
@Setter
@Entity
public class Film {
    @Id
    @GeneratedValue
    private UUID id;
    private String title;
    private String country;
    private String lang;
    private float rate;
    // film length in minutes
    private int length;
    //Using Sql date and time like TimeStamp
    private Instant lastUpdate;
    //One to Many (List of actors)
    private String actor;
    //List category
    private String category;
    private String filmText;
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "film")
    private Set<Cast> casts = new HashSet<>();
}
