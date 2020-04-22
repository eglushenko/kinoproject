package com.solvve.lab.kinoproject.service.importer;

import com.solvve.lab.kinoproject.BaseTest;
import com.solvve.lab.kinoproject.domain.ExternalSystemImport;
import com.solvve.lab.kinoproject.domain.Film;
import com.solvve.lab.kinoproject.enums.ImportEntityType;
import com.solvve.lab.kinoproject.exception.ImportAlreadyPerformedException;
import com.solvve.lab.kinoproject.repository.ExternalSystemImportRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class ExternalSystemImportServiceTest extends BaseTest {

    @Autowired
    private ExternalSystemImportService externalSystemImportService;

    @Autowired
    private ExternalSystemImportRepository externalSystemImportRepository;

    @Test
    public void testValidateImportFromExternalSystem() throws ImportAlreadyPerformedException {
        externalSystemImportService.validateNotImported(Film.class, "09889");

    }

    @Test
    public void testExceptionWhenAlreadyExported() {
        ExternalSystemImport esi = generateFlatEntityWithoutId(ExternalSystemImport.class);
        esi.setEntityType(ImportEntityType.MOVIE);
        esi = externalSystemImportRepository.save(esi);

        String idInExternalSystem = esi.getEntityExternalId();

        ImportAlreadyPerformedException ex = Assertions.catchThrowableOfType(
                () -> externalSystemImportService.validateNotImported(Film.class, idInExternalSystem),
                ImportAlreadyPerformedException.class
        );
        Assertions.assertThat(ex.getExternalSystemImport()).isEqualToComparingFieldByField(esi);
    }

    @Test
    public void testExceptionWhenAlreadyExportedButDifferentType() throws ImportAlreadyPerformedException {
        ExternalSystemImport esi = generateFlatEntityWithoutId(ExternalSystemImport.class);
        esi.setEntityType(ImportEntityType.PERSON);
        esi = externalSystemImportRepository.save(esi);

        externalSystemImportService.validateNotImported(Film.class, esi.getEntityExternalId());
    }

    @Test
    public void testCreateSystemImport() {
        Film film = generateObject(Film.class);
        String idInExternalSystem = "id777";
        UUID importId = externalSystemImportService.createExternalSystemImport(film, idInExternalSystem);
        Assert.assertNotNull(importId);
        ExternalSystemImport esi = externalSystemImportRepository.findById(importId).get();
        Assert.assertEquals(idInExternalSystem, esi.getEntityExternalId());
        Assert.assertEquals(ImportEntityType.MOVIE, esi.getEntityType());
        Assert.assertEquals(film.getId(), esi.getEntityId());
    }

}