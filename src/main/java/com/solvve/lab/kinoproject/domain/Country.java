package com.solvve.lab.kinoproject.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Country {
    @Id
    @GeneratedValue
    private UUID id;
    private String countryName;
    private String countryShortName;

}
