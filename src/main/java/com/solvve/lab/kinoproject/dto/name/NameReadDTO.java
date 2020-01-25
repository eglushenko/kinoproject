package com.solvve.lab.kinoproject.dto.name;

import lombok.Data;

import java.util.UUID;

@Data
public class NameReadDTO {
    private UUID id;
    private String firstName;
    private String lastName;

}
