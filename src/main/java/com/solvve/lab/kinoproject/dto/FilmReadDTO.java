package com.solvve.lab.kinoproject.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class FilmReadDTO {
    private UUID id;
    private String title;
    private String country;
    private String lang;
    private float rate;
    private int length;
    private LocalDate lastUpdate;
    private String actor;
    private String category;
    private String filmText;


}
