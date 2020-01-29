package com.solvve.lab.kinoproject.dto.typo;

import com.solvve.lab.kinoproject.enums.TypoStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class TypoReadDTO {
    private UUID id;
    private String typoMessege;
    private String typoLink;
    private TypoStatus status;
}
