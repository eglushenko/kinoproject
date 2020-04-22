package com.solvve.lab.kinoproject.exception;

import com.solvve.lab.kinoproject.domain.ExternalSystemImport;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ImportAlreadyPerformedException extends Exception {
    private ExternalSystemImport externalSystemImport;

    public ImportAlreadyPerformedException(ExternalSystemImport esi) {
        super(String.format("Already performed import of %s with id=%s and id in external system  =%s",
                esi.getEntityType(), esi.getEntityId(), esi.getEntityExternalId()));
        this.externalSystemImport = esi;
    }


}
