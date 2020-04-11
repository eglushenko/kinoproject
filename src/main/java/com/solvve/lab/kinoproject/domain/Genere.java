package com.solvve.lab.kinoproject.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class Genere extends AbstractEntity {

    private String genereName;

    private String genreDescription;


}
