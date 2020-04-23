package com.solvve.lab.kinoproject.client.themoviedb.dto;


import lombok.Data;


@Data
public class PersonReadDTO {
    private String id;
    private String birthday;
    private String deathday;
    private String name;
    private int gender;
    private String biography;
}
