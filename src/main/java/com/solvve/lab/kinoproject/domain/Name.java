package com.solvve.lab.kinoproject.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Name {
    @Id
    @GeneratedValue
    @NotNull
    private UUID id;
    private String firstName;
    private String lastName;

}
