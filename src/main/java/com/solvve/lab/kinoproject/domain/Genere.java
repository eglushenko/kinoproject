package com.solvve.lab.kinoproject.domain;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Genere {
    @Id
    private UUID id;
    private String genereName;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getGenereName() {
        return genereName;
    }

    public void setGenereName(String genereName) {
        this.genereName = genereName;
    }
}
