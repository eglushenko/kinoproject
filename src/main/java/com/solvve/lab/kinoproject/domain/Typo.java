package com.solvve.lab.kinoproject.domain;

import com.solvve.lab.kinoproject.enums.TypoStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Typo {
    @Id
    @GeneratedValue
    private UUID id;
    private String typoMessege;
    private String typoLink;
    @Enumerated(EnumType.STRING)
    private TypoStatus status;

}
