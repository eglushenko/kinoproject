package com.solvve.lab.kinoproject.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
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
    private LocalDate lastUpdate;
    //One to Many (List of actors)
    private String actor;
    //List category
    private String category;
    private String filmText;


}
