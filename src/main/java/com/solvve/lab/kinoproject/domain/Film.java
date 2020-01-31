package com.solvve.lab.kinoproject.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
    private Instant lastUpdate;
    //One to Many (List of actors)
    private Instant realiseYear;
    private String category;
    private String filmText;
    @OneToMany(mappedBy = "film")
    private Set<Cast> casts = new HashSet<>();
}
