package com.solvve.lab.kinoproject.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
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
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "name")
    private Set<Cast> casts = new HashSet<>();

}
