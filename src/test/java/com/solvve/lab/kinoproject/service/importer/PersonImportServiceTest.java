package com.solvve.lab.kinoproject.service.importer;

import com.solvve.lab.kinoproject.BaseTest;
import com.solvve.lab.kinoproject.client.themoviedb.TheMovieDbClient;
import com.solvve.lab.kinoproject.client.themoviedb.dto.PersonReadDTO;
import com.solvve.lab.kinoproject.domain.Name;
import com.solvve.lab.kinoproject.exception.ImportedEntityAlreadyExistException;
import com.solvve.lab.kinoproject.repository.NameRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

public class PersonImportServiceTest extends BaseTest {

    @MockBean
    private TheMovieDbClient theMovieDbClient;

    @Autowired
    private NameRepository nameRepository;

    @Autowired
    private PersonImportService personImportService;

    @Test
    public void testPersonImportAlreadyExist() {
        String movieExternalId = "id3";

        Name existingName = generateFlatEntityWithoutId(Name.class);
        existingName = nameRepository.save(existingName);

        PersonReadDTO read = generateObject(PersonReadDTO.class);
        read.setName(existingName.getName());
        Mockito.when(theMovieDbClient.getPerson(movieExternalId)).thenReturn(read);

        ImportedEntityAlreadyExistException ex = Assertions.catchThrowableOfType(
                () -> personImportService.importPerson(movieExternalId), ImportedEntityAlreadyExistException.class);
        Assert.assertEquals(Name.class, ex.getEntityClass());
        Assert.assertEquals(existingName.getId(), ex.getEntityId());
    }


}