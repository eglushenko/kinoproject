package com.solvve.lab.kinoproject.dto.typo;


import com.solvve.lab.kinoproject.enums.TypoStatus;
import lombok.Data;

@Data
public class TypoCreateDTO {
    private String typoMessege;
    private String typoLink;
    private TypoStatus status;
}
