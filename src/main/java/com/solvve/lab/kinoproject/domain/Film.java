package com.solvve.lab.kinoproject.domain;


import com.solvve.lab.kinoproject.enums.FilmStatus;
import com.solvve.lab.kinoproject.enums.RateMPAA;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Film extends AbstractEntity {

    private String title;
    private Boolean adult;
    private Integer budget;
    private String homePage;
    private String originalTitle;

    @Enumerated(EnumType.STRING)
    private FilmStatus status;
    private Integer rateCount;
    private String country;
    private String lang;
    private Double averageRate;
    private Integer length;
    private Instant lastUpdate;
    private Instant realiseYear;
    private String category;
    private String filmText;

    @Enumerated(EnumType.STRING)
    private RateMPAA mpaa;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "film")
    private Set<Cast> casts = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "film")
    private Set<Scene> scenes = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "film")
    private Set<Media> media = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "film")
    private Set<Review> reviews = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "film_genere",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "genere_id"))
    private List<Genere> filmGeneres = new ArrayList<>();

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}
