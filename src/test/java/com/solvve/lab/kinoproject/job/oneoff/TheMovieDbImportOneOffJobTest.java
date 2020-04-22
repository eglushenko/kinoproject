package com.solvve.lab.kinoproject.job.oneoff;

import com.solvve.lab.kinoproject.BaseTest;
import com.solvve.lab.kinoproject.client.themoviedb.TheMovieDbClient;
import com.solvve.lab.kinoproject.client.themoviedb.dto.MovieReadShortDTO;
import com.solvve.lab.kinoproject.client.themoviedb.dto.MoviesPageDTO;
import com.solvve.lab.kinoproject.exception.ImportAlreadyPerformedException;
import com.solvve.lab.kinoproject.exception.ImportedEntityAlreadyExistException;
import com.solvve.lab.kinoproject.service.importer.MovieImportService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;


public class TheMovieDbImportOneOffJobTest extends BaseTest {

    @Autowired
    private TheMovieDbImportOneOffJob job;

    @MockBean
    private TheMovieDbClient client;

    @MockBean
    private MovieImportService movieImportService;

    private MoviesPageDTO generatePageWith2Results() {
        MoviesPageDTO page = generateObject(MoviesPageDTO.class);
        page.getResults().add(generateObject(MovieReadShortDTO.class));
        Assert.assertEquals(2, page.getResults().size());
        return page;
    }

    @Test
    public void testDoImport() throws ImportAlreadyPerformedException, ImportedEntityAlreadyExistException {
        MoviesPageDTO page = generatePageWith2Results();
        Mockito.when(client.getTopRatedMovies()).thenReturn(page);

        job.doImport();

        for (MovieReadShortDTO m : page.getResults()) {
            Mockito.verify(movieImportService).importMovie(m.getId());
        }
    }

    @Test
    public void testDoImportNoExceptionIfGetPageFiled() {
        Mockito.when(client.getTopRatedMovies()).thenThrow(RuntimeException.class);
        job.doImport();
        Mockito.verifyNoInteractions(movieImportService);
    }

    @Test
    public void testDoImportFirstFailedSecondSuccess() throws ImportedEntityAlreadyExistException, ImportAlreadyPerformedException {
        MoviesPageDTO page = generatePageWith2Results();
        Mockito.when(client.getTopRatedMovies()).thenReturn(page);
        Mockito.when(movieImportService.importMovie(page.getResults().get(0).getId())).
                thenThrow(RuntimeException.class);
        job.doImport();

        for (MovieReadShortDTO m : page.getResults()) {
            Mockito.verify(movieImportService).importMovie(m.getId());
        }
    }

}