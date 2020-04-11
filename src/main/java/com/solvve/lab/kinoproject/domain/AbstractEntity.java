package com.solvve.lab.kinoproject.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractEntity {
    @Id
    @GeneratedValue
    private UUID id;
}
