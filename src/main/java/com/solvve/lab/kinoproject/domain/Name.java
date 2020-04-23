package com.solvve.lab.kinoproject.domain;


import com.solvve.lab.kinoproject.enums.Gender;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Name extends AbstractEntity {

    private String name;
    private LocalDate birthday;
    private LocalDate deathday;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String biography;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "name")
    private Set<Cast> casts = new HashSet<>();

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

}
