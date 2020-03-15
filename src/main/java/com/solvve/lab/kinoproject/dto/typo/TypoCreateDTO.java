package com.solvve.lab.kinoproject.dto.typo;


import com.solvve.lab.kinoproject.enums.TypoStatus;
import lombok.Data;

@Data
public class TypoCreateDTO {
    private String typoMessage;
    private String errorText;
    private String rightText;
    private String typoLink;
    private TypoStatus status;
}
