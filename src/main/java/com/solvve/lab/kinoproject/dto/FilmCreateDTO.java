package com.solvve.lab.kinoproject.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FilmCreateDTO {
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
