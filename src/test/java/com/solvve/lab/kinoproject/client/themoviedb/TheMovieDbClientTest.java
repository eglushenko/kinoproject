package com.solvve.lab.kinoproject.client.themoviedb;

import com.solvve.lab.kinoproject.BaseTest;
import com.solvve.lab.kinoproject.client.themoviedb.dto.MovieReadDTO;
import com.solvve.lab.kinoproject.client.themoviedb.dto.MovieReadShortDTO;
import com.solvve.lab.kinoproject.client.themoviedb.dto.MoviesPageDTO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TheMovieDbClientTest extends BaseTest {

    @Autowired
    private TheMovieDbClient theMovieDbClient;

    @Test
    public void testGetMovieRu() {
        String movieId = "280";
        MovieReadDTO movie = theMovieDbClient.getMovie(movieId, "ru");
        Assert.assertEquals(movieId, movie.getId());
        Assert.assertEquals("Terminator 2: Judgment Day", movie.getOriginalTitle());
        Assert.assertEquals("Терминатор 2: Судный день", movie.getTitle());
    }

    @Test
    public void testGetMovie() {
        String movieId = "280";
        MovieReadDTO movie = theMovieDbClient.getMovie(movieId, null);
        Assert.assertEquals(movieId, movie.getId());
        Assert.assertEquals("Terminator 2: Judgment Day", movie.getOriginalTitle());
    }

    @Test
    public void testGetTopRatedMovies() {
        MoviesPageDTO moviesPage = theMovieDbClient.getTopRatedMovies();
        Assert.assertTrue(moviesPage.getTotalPages() > 0);
        Assert.assertTrue(moviesPage.getTotalResults() > 0);
        Assert.assertTrue(moviesPage.getResults().size() > 0);

        for (MovieReadShortDTO read : moviesPage.getResults()) {
            Assert.assertNotNull(read.getId());
            Assert.assertNotNull(read.getOriginalTitle());
        }


    }


}