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
public class Review {
    @Id
    @GeneratedValue
    private UUID id;
    private String reviewText;

}