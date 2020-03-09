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
import org.springframework.transaction.support.TransactionTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(statements = {
        "delete from film",
        "delete from cast",
        "delete from scene"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class FilmRepositoryTest {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private TransactionTemplate transactionTemplate;


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
    public void testSaveFilm() {
        Film film = new Film();
        film = filmRepository.save(film);
        assertNotNull(film.getId());
        assertTrue(filmRepository.findById(film.getId()).isPresent());
    }


    @Test
    public void testGetFilmsByAverageRate() {
        Film film1 = createFilm();
        Film film2 = createFilm();
        film2.setAverageRate(2.0);
        filmRepository.save(film2);
        Film film3 = createFilm();


        List<Film> res = filmRepository.findByAverageRateGreaterThan(4.0);
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

    @Test
    public void testGetIdsOfFilms() {
        Set<UUID> expectedIds = new HashSet<>();
        expectedIds.add(createFilm().getId());
        expectedIds.add(createFilm().getId());


        transactionTemplate.executeWithoutResult(transactionStatus -> {
            Assert.assertEquals(expectedIds, new HashSet<>(filmRepository.getIdsOfFilms()
                    .collect(Collectors.toSet())));
        });

    }


}
