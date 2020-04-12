package com.solvve.lab.kinoproject.event;


import com.solvve.lab.kinoproject.enums.TypoStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class TypoStatusChangedEvent {
    private UUID typoId;
    private TypoStatus newStatus;
}
