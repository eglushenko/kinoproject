package com.solvve.lab.kinoproject.service.importer;

import com.solvve.lab.kinoproject.BaseTest;
import com.solvve.lab.kinoproject.client.themoviedb.TheMovieDbClient;
import com.solvve.lab.kinoproject.client.themoviedb.dto.MovieReadDTO;
import com.solvve.lab.kinoproject.domain.Film;
import com.solvve.lab.kinoproject.exception.ImportAlreadyPerformedException;
import com.solvve.lab.kinoproject.exception.ImportedEntityAlreadyExistException;
import com.solvve.lab.kinoproject.repository.FilmRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;


public class MovieImportServiceTest extends BaseTest {

    @Autowired
    private FilmRepository filmRepository;

    @MockBean
    private TheMovieDbClient theMovieDbClient;

    @Autowired
    private MovieImportService movieImportService;

    @Test
    public void testMovieImportAlreadyExist() {
        String movieExternalId = "id44";

        Film existingFilm = generateFlatEntityWithoutId(Film.class);
        existingFilm = filmRepository.save(existingFilm);

        MovieReadDTO read = generateObject(MovieReadDTO.class);
        read.setOriginalTitle(existingFilm.getOriginalTitle());
        Mockito.when(theMovieDbClient.getMovie(movieExternalId)).thenReturn(read);

        ImportedEntityAlreadyExistException ex = Assertions.catchThrowableOfType(
                () -> movieImportService.importMovie(movieExternalId), ImportedEntityAlreadyExistException.class);
        Assert.assertEquals(Film.class, ex.getEntityClass());
        Assert.assertEquals(existingFilm.getId(), ex.getEntityId());
    }

    @Test
    public void testNoCallClientOnDuplicateImport() throws ImportedEntityAlreadyExistException, ImportAlreadyPerformedException {
        String movieExternalId = "id4";

        MovieReadDTO read = generateObject(MovieReadDTO.class);
        Mockito.when(theMovieDbClient.getMovie(movieExternalId)).thenReturn(read);

        movieImportService.importMovie(movieExternalId);
        Mockito.verify(theMovieDbClient).getMovie(movieExternalId);
        Mockito.reset(theMovieDbClient);

        Assertions.assertThatThrownBy(
                () -> movieImportService.importMovie(movieExternalId))
                .isInstanceOf(ImportAlreadyPerformedException.class);

        Mockito.verifyNoInteractions(theMovieDbClient);
    }

}