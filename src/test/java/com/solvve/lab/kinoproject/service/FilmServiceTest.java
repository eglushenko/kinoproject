package com.solvve.lab.kinoproject.service;

import com.solvve.lab.kinoproject.domain.Film;
import com.solvve.lab.kinoproject.dto.FilmCreateDTO;
import com.solvve.lab.kinoproject.dto.FilmReadDTO;
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

import java.time.LocalDate;
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

    @Test
    public void getFilmTest() {
        Film film = new Film();
        film.setId(UUID.randomUUID());
        film.setActor("actor");
        film.setCategory("category");
        film.setCountry("UA");
        film.setFilmText("");
        film.setLang("UA");
        film.setLength(83);
        film.setRate(4.3F);
        film.setTitle("LEGO FILM");
        film.setLastUpdate(LocalDate.of(2019, 3, 15));
        film = filmRepository.save(film);

        FilmReadDTO readDTO = filmService.getFilm(film.getId());
        Assertions.assertThat(readDTO).isEqualToComparingFieldByField(film);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getFilmWrongId() {
        filmService.getFilm(UUID.randomUUID());

    }

    @Test
    public void createFilmTest() {
        FilmCreateDTO create = new FilmCreateDTO();
        create.setActor("actor");
        create.setCategory("category");
        create.setCountry("UA");
        create.setFilmText("");
        create.setLang("UA");
        create.setLength(83);
        create.setRate(4.3F);
        create.setTitle("LEGO FILM");
        create.setLastUpdate(LocalDate.of(2019, 3, 15));
        FilmReadDTO filmReadDTO = filmService.createFilm(create);
        Assertions.assertThat(create).isEqualToComparingFieldByField(filmReadDTO);
        Assert.assertNotNull(filmReadDTO.getId());

        Film film = filmRepository.findById(filmReadDTO.getId()).get();
        Assertions.assertThat(filmReadDTO).isEqualToComparingFieldByField(film);
    }
}