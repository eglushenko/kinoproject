package com.solvve.lab.kinoproject.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Genere extends AbstractEntity {

    private String genereName;

    private String genreDescription;

    @ManyToMany(mappedBy = "filmGeneres")
    private List<Film> films = new ArrayList<>();

}
