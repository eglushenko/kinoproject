package com.solvve.lab.kinoproject.service.importer;


import com.solvve.lab.kinoproject.domain.AbstractEntity;
import com.solvve.lab.kinoproject.domain.ExternalSystemImport;
import com.solvve.lab.kinoproject.domain.Film;
import com.solvve.lab.kinoproject.domain.Name;
import com.solvve.lab.kinoproject.enums.ImportEntityType;
import com.solvve.lab.kinoproject.exception.ImportAlreadyPerformedException;
import com.solvve.lab.kinoproject.repository.ExternalSystemImportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExternalSystemImportService {

    @Autowired
    private ExternalSystemImportRepository externalSystemImportRepository;

    private ImportEntityType getImportEntityType(Class<? extends AbstractEntity> entityToImport) {
        if (Film.class.equals(entityToImport)) {
            return ImportEntityType.MOVIE;
        } else if (Name.class.equals(entityToImport)) {
            return ImportEntityType.PERSON;
        }
        throw new IllegalArgumentException(" Import for " + entityToImport + "is not supported!");
    }

    public void validateNotImported(Class<? extends AbstractEntity> entityToImport,
                                    String idInExternalSystem) throws ImportAlreadyPerformedException {
        ImportEntityType importEntityType = getImportEntityType(entityToImport);
        ExternalSystemImport esi = externalSystemImportRepository.
                findByEntityExternalIdAndEntityType(idInExternalSystem, importEntityType);
        if (esi != null) {
            throw new ImportAlreadyPerformedException(esi);
        }

    }

    public <T extends AbstractEntity> UUID createExternalSystemImport(T entity, String idInExternalSystem) {
        ImportEntityType importEntityType = getImportEntityType(entity.getClass());
        ExternalSystemImport esi = new ExternalSystemImport();
        esi.setEntityType(importEntityType);
        esi.setEntityExternalId(idInExternalSystem);
        esi.setEntityId(entity.getId());
        esi = externalSystemImportRepository.save(esi);
        return esi.getId();
    }
}
