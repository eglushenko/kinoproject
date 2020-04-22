package com.solvve.lab.kinoproject.repository;

import com.solvve.lab.kinoproject.domain.ExternalSystemImport;
import com.solvve.lab.kinoproject.enums.ImportEntityType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExternalSystemImportRepository extends CrudRepository<ExternalSystemImport, UUID> {

    ExternalSystemImport findByEntityExternalIdAndEntityType(String externalId, ImportEntityType entityType);

}
