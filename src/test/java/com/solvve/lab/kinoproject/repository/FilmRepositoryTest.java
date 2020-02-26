package com.solvve.lab.kinoproject.repository;


import com.solvve.lab.kinoproject.domain.Film;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(statements = {
        "delete from film",
        "delete from cast"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class FilmRepositoryTest {

    @Autowired
    private FilmRepository filmRepository;

    private Film createFilm() {
        ZoneOffset utc = ZoneOffset.UTC;
        Film film = new Film();
        film.setCategory("category");
        film.setCountry("UA");
        film.setFilmText("");
        film.setLang("en");
        film.setLength(83);
        film.setRate(4.3);
        film.setTitle("LEGO FILM");
        film.setRealiseYear(LocalDateTime.of(2019, 01, 01, 00, 01).toInstant(utc));
        film.setLastUpdate(LocalDateTime.of(2019, 12, 01, 17, 01).toInstant(utc));
        return filmRepository.save(film);
    }

    @Test
    public void testSaveFilm() {
        Film film = new Film();
        film = filmRepository.save(film);
        assertNotNull(film.getId());
        assertTrue(filmRepository.findById(film.getId()).isPresent());
    }


    @Test
    public void testGetFilmsByRate() {
        Film film1 = createFilm();
        Film film2 = createFilm();
        film2.setRate(2.0);
        filmRepository.save(film2);
        Film film3 = createFilm();


        List<Film> res = filmRepository.findByRateGreaterThan(4.0);
        Assertions.assertThat(res).extracting(Film::getId).containsExactlyInAnyOrder(film1.getId(), film3.getId());
    }

    @Test
    public void testGetFilmInIntervalAndParametrs() {
        ZoneOffset utc = ZoneOffset.UTC;
        Film film1 = createFilm();
        Film film2 = createFilm();
        Film film3 = createFilm();
        film3.setRealiseYear(LocalDateTime.of(2018, 01, 01, 00, 01).toInstant(utc));
        film3.setLastUpdate(LocalDateTime.of(2018, 01, 01, 00, 01).toInstant(utc));
        filmRepository.save(film3);

        Instant lastUpdate = LocalDateTime.of(2019, 12, 01, 00, 01).toInstant(utc);
        Instant param2 = LocalDateTime.of(2019, 01, 01, 00, 01).toInstant(utc);
        List<Film> res = filmRepository.findFilmSortedByRealiseYearAndLastUpdate("en", 1.1, lastUpdate, param2);
        Assertions.assertThat(res).extracting(Film::getId).containsExactlyInAnyOrder(film1.getId(), film2.getId());

    }

    @Test
    public void testFilmCreateDate() {
        Film film = createFilm();
        Assert.assertNotNull(film.getCreatedAt());

        Instant createDateBeforeLoad = film.getCreatedAt();

        film = filmRepository.findById(film.getId()).get();

        Instant createDateAfterLoad = film.getCreatedAt();

        Assertions.assertThat(createDateBeforeLoad).isEqualTo(createDateAfterLoad);
    }

    @Test
    public void testFilmUpdateDate() {
        Film film = createFilm();
        Assert.assertNotNull(film.getUpdatedAt());

        Instant updateDateBeforeLoad = film.getUpdatedAt();

        film.setTitle("bla bla bla");
        film = filmRepository.save(film);
        film = filmRepository.findById(film.getId()).get();

        Instant updateDateAfterLoad = film.getUpdatedAt();

        Assert.assertNotNull(updateDateAfterLoad);
        Assertions.assertThat(updateDateAfterLoad).isAfter(updateDateBeforeLoad);
    }

}
