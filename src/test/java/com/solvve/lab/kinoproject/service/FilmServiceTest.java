package com.solvve.lab.kinoproject.service;

import com.solvve.lab.kinoproject.domain.Film;
import com.solvve.lab.kinoproject.dto.film.FilmCreateDTO;
import com.solvve.lab.kinoproject.dto.film.FilmPatchDTO;
import com.solvve.lab.kinoproject.dto.film.FilmPutDTO;
import com.solvve.lab.kinoproject.dto.film.FilmReadDTO;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.repository.FilmRepository;
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
import java.util.UUID;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Sql(statements = "delete from film", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class FilmServiceTest {

    @Autowired
    FilmRepository filmRepository;

    @Autowired
    private FilmService filmService;

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
    public void testGetFilm() {
        Film film = new Film();
        film.setId(UUID.randomUUID());
        film.setCategory("category");
        film.setCountry("UA");
        film.setFilmText("");
        film.setLang("UA");
        film.setLength(83);
        film.setRate(4.3F);
        film.setTitle("LEGO FILM");
        film.setLastUpdate(Instant.parse("2020-01-03T10:15:30.00Z"));
        film = filmRepository.save(film);

        FilmReadDTO readDTO = filmService.getFilm(film.getId());
        Assertions.assertThat(readDTO).isEqualToComparingFieldByField(film);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getFilmWrongId() {
        filmService.getFilm(UUID.randomUUID());

    }

    @Test
    public void testCreateFilm() {
        FilmCreateDTO create = new FilmCreateDTO();
        create.setCategory("category");
        create.setCountry("UA");
        create.setFilmText("");
        create.setLang("UA");
        create.setLength(83);
        create.setRate(4.3F);
        create.setTitle("LEGO FILM");
        create.setLastUpdate(Instant.parse("2020-01-03T10:15:30.00Z"));
        FilmReadDTO filmReadDTO = filmService.createFilm(create);
        Assertions.assertThat(create).isEqualToComparingFieldByField(filmReadDTO);
        Assert.assertNotNull(filmReadDTO.getId());

        Film film = filmRepository.findById(filmReadDTO.getId()).get();
        Assertions.assertThat(filmReadDTO).isEqualToComparingFieldByField(film);
    }

    @Test
    public void testPatchFilm() {
        Film film = createFilm();

        FilmPatchDTO patch = new FilmPatchDTO();
        patch.setCategory("category");
        patch.setCountry("UA");
        patch.setFilmText("some text");
        patch.setLang("UA");
        patch.setLength(83);
        patch.setRate(1.3F);
        patch.setTitle("FILM");
        patch.setLastUpdate(Instant.parse("2020-01-03T10:15:30.00Z"));
        FilmReadDTO read = filmService.patchFilm(film.getId(), patch);

        Assertions.assertThat(patch).isEqualToComparingFieldByField(read);

        film = filmRepository.findById(read.getId()).get();
        Assertions.assertThat(film).isEqualToComparingFieldByField(read);
    }

    @Test
    public void testPutFilm() {
        Film film = createFilm();

        FilmPutDTO put = new FilmPutDTO();
        put.setCategory("category");
        put.setCountry("UA");
        put.setFilmText("some text");
        put.setLang("UA");
        put.setLength(83);
        put.setRate(1.3F);
        put.setTitle("FILM");
        put.setLastUpdate(Instant.parse("2020-01-03T10:15:30.00Z"));
        FilmReadDTO read = filmService.putFilm(film.getId(), put);

        Assertions.assertThat(put).isEqualToComparingFieldByField(read);

        film = filmRepository.findById(read.getId()).get();
        Assertions.assertThat(film).isEqualToComparingFieldByField(read);
    }


    @Test
    public void testDeleteFilm() {
        Film film = createFilm();

        filmService.deleteFilm(film.getId());

        Assert.assertFalse(filmRepository.existsById(film.getId()));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDeleteFilmNotFoundId() {
        filmService.deleteFilm(UUID.randomUUID());
    }

}