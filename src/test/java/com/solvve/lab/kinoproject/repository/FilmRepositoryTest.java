package com.solvve.lab.kinoproject.repository;


import com.solvve.lab.kinoproject.domain.Film;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
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
        Film film = new Film();
        film.setCategory("category");
        film.setCountry("UA");
        film.setFilmText("");
        film.setLang("UA");
        film.setLength(83);
        film.setRate(4.3F);
        film.setTitle("LEGO FILM");
        film.setLastUpdate(Instant.parse("2020-01-03T10:15:30.00Z"));
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
        film2.setRate(2F);
        filmRepository.save(film2);
        Film film3 = createFilm();


        List<Film> res = filmRepository.findByRateGreaterThan(4F);
        Assertions.assertThat(res).extracting(Film::getId).containsExactlyInAnyOrder(film1.getId(), film3.getId());
    }

}
