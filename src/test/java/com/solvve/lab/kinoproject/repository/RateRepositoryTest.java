package com.solvve.lab.kinoproject.repository;

import com.solvve.lab.kinoproject.BaseTest;
import com.solvve.lab.kinoproject.domain.Film;
import com.solvve.lab.kinoproject.domain.Rate;
import com.solvve.lab.kinoproject.enums.RateObjectType;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;


public class RateRepositoryTest extends BaseTest {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private RateRepository rateRepository;

    private Film createFilm() {
        ZoneOffset utc = ZoneOffset.UTC;
        Film film = new Film();
        film.setCategory("category");
        film.setCountry("UA");
        film.setFilmText("");
        film.setLang("en");
        film.setLength(83);
        film.setAverageRate(4.3);
        film.setTitle("LEGO FILM");
        film.setRealiseYear(LocalDateTime.of(2019, 01, 01, 00, 01).toInstant(utc));
        film.setLastUpdate(LocalDateTime.of(2019, 12, 01, 17, 01).toInstant(utc));
        return filmRepository.save(film);
    }


    @Test
    public void testCalcAveregeRateObject() {
        Film film = createFilm();
        Rate rate = new Rate();
        rate.setRate(2.0);
        rate.setRatedObjectId(film.getId());
        rate.setType(RateObjectType.FILM);
        rateRepository.save(rate);

        Rate rate2 = new Rate();
        rate2.setRatedObjectId(film.getId());
        rate2.setRate(1.0);
        rate2.setType(RateObjectType.FILM);
        rateRepository.save(rate2);

        Rate rate3 = new Rate();
        rate3.setType(RateObjectType.FILM);
        rate3.setRate(null);
        rate3.setRatedObjectId(film.getId());
        rateRepository.save(rate3);

        Rate rate1 = new Rate();
        rate1.setType(RateObjectType.FILM);
        rate1.setRatedObjectId(UUID.randomUUID());
        rate1.setRate(6.0);
        rateRepository.save(rate1);


        Assert.assertEquals(1.5, rateRepository.calcAverageMarkOfObjectId(film.getId()), Double.MIN_NORMAL);


    }

}