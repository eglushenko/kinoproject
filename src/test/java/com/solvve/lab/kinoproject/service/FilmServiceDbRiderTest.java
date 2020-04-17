package com.solvve.lab.kinoproject.service;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import com.solvve.lab.kinoproject.BaseTest;
import com.solvve.lab.kinoproject.domain.Film;
import com.solvve.lab.kinoproject.repository.FilmRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;


@DBRider
public class FilmServiceDbRiderTest extends BaseTest {

    @Autowired
    private FilmService filmService;

    @Autowired
    private FilmRepository filmRepository;

    @Test
    @DataSet(value = "/dataset/testUpdateAverageRateFilm.xml")
    public void testUpdateAverageRateFilmsDbReader() {
        UUID filmId = UUID.fromString("f341ad89-6140-4bdb-a7c8-eb9e266230f4");
        filmService.updateAverageRateOfFilm(filmId);
        Film f = filmRepository.findById(filmId).get();
        Assert.assertEquals(3.5, f.getAverageRate(), Double.MIN_NORMAL);

    }

    @Test
    @DataSet(value = "/dataset/testUpdateAverageRateFilm.xml")
    @ExpectedDataSet(value = "/dataset/testUpdateAverageRateFilm_result.xml")
    public void testUpdateAverageRateFilmsDbREaderExpectedDataSet() {
        UUID filmId = UUID.fromString("f341ad89-6140-4bdb-a7c8-eb9e266230f4");
        filmService.updateAverageRateOfFilm(filmId);

    }

}
