package com.solvve.lab.kinoproject.dto.name;

import com.solvve.lab.kinoproject.enums.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class NameCreateDTO {
    private String name;
    private LocalDate birthday;
    private LocalDate deathday;
    private Gender gender;
    private String biography;
}
