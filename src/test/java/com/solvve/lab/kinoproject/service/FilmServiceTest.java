package com.solvve.lab.kinoproject.service;

import com.solvve.lab.kinoproject.BaseTest;
import com.solvve.lab.kinoproject.domain.Film;
import com.solvve.lab.kinoproject.dto.FilmFilter;
import com.solvve.lab.kinoproject.dto.FilmReadExtendedDTO;
import com.solvve.lab.kinoproject.dto.film.FilmCreateDTO;
import com.solvve.lab.kinoproject.dto.film.FilmPatchDTO;
import com.solvve.lab.kinoproject.dto.film.FilmPutDTO;
import com.solvve.lab.kinoproject.dto.film.FilmReadDTO;
import com.solvve.lab.kinoproject.enums.RateMPAA;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.repository.FilmRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.UUID;


public class FilmServiceTest extends BaseTest {

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
        film.setAverageRate(4.3);
        film.setTitle("LEGO FILM");
        film.setMpaa(RateMPAA.PG);
        film.setLastUpdate(Instant.parse("2020-01-03T10:15:30.00Z"));
        return filmRepository.save(film);
    }

    @Test
    public void testGetFilm() {
        Film film = createFilm();

        FilmReadDTO readDTO = filmService.getFilm(film.getId());
        Assertions.assertThat(readDTO)
                .isEqualToIgnoringGivenFields(film,
                        "reviews", "media", "casts", "scenes", "createdAt", "updatedAt");
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
        create.setAverageRate(4.3);
        create.setTitle("LEGO FILM");
        create.setMpaa(RateMPAA.PG);
        create.setLastUpdate(Instant.parse("2020-01-03T10:15:30.00Z"));
        FilmReadDTO filmReadDTO = filmService.createFilm(create);
        Assertions.assertThat(create)
                .isEqualToIgnoringGivenFields(filmReadDTO,
                        "reviews", "media", "casts", "scenes", "createdAt", "updatedAt");
        Assert.assertNotNull(filmReadDTO.getId());

        Film film = filmRepository.findById(filmReadDTO.getId()).get();
        Assertions.assertThat(filmReadDTO)
                .isEqualToIgnoringGivenFields(film,
                        "reviews", "media", "casts", "scenes", "createdAt", "updatedAt");
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
        patch.setAverageRate(1.3);
        patch.setTitle("FILM");
        patch.setMpaa(RateMPAA.PG);
        patch.setLastUpdate(Instant.parse("2020-01-03T10:15:30.00Z"));
        FilmReadDTO read = filmService.patchFilm(film.getId(), patch);

        Assertions.assertThat(patch)
                .isEqualToIgnoringGivenFields(read,
                        "reviews", "media", "casts", "scenes", "createdAt", "updatedAt");

        film = filmRepository.findById(read.getId()).get();
        Assertions.assertThat(film)
                .isEqualToIgnoringGivenFields(read,
                        "reviews", "media", "casts", "scenes", "createdAt", "updatedAt");
    }

    @Test
    public void testPatchFilmEmptyPatch() {
        Film film = createFilm();

        FilmPatchDTO patch = new FilmPatchDTO();

        FilmReadDTO read = filmService.patchFilm(film.getId(), patch);

        Assert.assertNotNull(read.getCategory());
        Assert.assertNotNull(read.getCountry());
        Assert.assertNotNull(read.getFilmText());
        Assert.assertNotNull(read.getLang());
        Assert.assertNotNull(read.getLastUpdate());
        Assert.assertNotNull(read.getTitle());
        Assert.assertNotNull(read.getLength());
        Assert.assertNotNull(read.getAverageRate());
        Assert.assertNotNull(read.getMpaa());
        Film filmAfterUpdate = filmRepository.findById(read.getId()).get();

        Assert.assertNotNull(filmAfterUpdate.getCategory());
        Assert.assertNotNull(filmAfterUpdate.getCountry());
        Assert.assertNotNull(filmAfterUpdate.getFilmText());
        Assert.assertNotNull(filmAfterUpdate.getLang());
        Assert.assertNotNull(filmAfterUpdate.getLastUpdate());
        Assert.assertNotNull(filmAfterUpdate.getTitle());
        Assert.assertNotNull(filmAfterUpdate.getLength());
        Assert.assertNotNull(filmAfterUpdate.getAverageRate());
        Assert.assertNotNull(filmAfterUpdate.getMpaa());

        Assertions.assertThat(film)
                .isEqualToIgnoringGivenFields(filmAfterUpdate,
                        "reviews", "media", "casts", "scenes", "createdAt", "updatedAt");
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
        put.setAverageRate(1.3);
        put.setTitle("FILM");
        put.setMpaa(RateMPAA.PG);
        put.setLastUpdate(Instant.parse("2020-01-03T10:15:30.00Z"));
        FilmReadDTO read = filmService.updateFilm(film.getId(), put);

        Assertions.assertThat(put)
                .isEqualToIgnoringGivenFields(read,
                        "reviews", "media", "casts", "scenes", "createdAt", "updatedAt");

        film = filmRepository.findById(read.getId()).get();
        Assertions.assertThat(film)
                .isEqualToIgnoringGivenFields(read,
                        "reviews", "media", "mediaSet",
                        "casts", "scenes", "createdAt", "updatedAt");
    }

    @Test
    public void testGetFilmExtended() {
        Film film = createFilm();

        FilmReadExtendedDTO read = filmService.getFilmExtended(film.getId());
        Assertions.assertThat(read)
                .isEqualToIgnoringGivenFields(film,
                        "reviews", "media", "casts", "scenes",
                        "createdAt", "updatedAt");
    }

    @Test
    public void testGetFilmByEmptyFilter() {
        Film film1 = createFilm();
        Film film2 = createFilm();

        film1.setTitle("film1");
        filmRepository.save(film1);
        film2.setTitle("film2");
        filmRepository.save(film2);

        FilmFilter filmFilter = new FilmFilter();
        Assertions.assertThat(filmService.getFilms(filmFilter)).extracting("id")
                .containsExactlyInAnyOrder(film1.getId(), film2.getId());
    }


    @Test
    public void testGetFilmsByFilter() {
        Film film1 = createFilm();
        film1.setLength(80);
        filmRepository.save(film1);
        Film film2 = createFilm();
        film2.setLength(90);
        filmRepository.save(film2);
        Film film3 = createFilm();
        filmRepository.save(film3);
        Film film4 = createFilm();
        filmRepository.save(film4);

        FilmFilter filter = new FilmFilter();
        filter.setLength(83);

        Assertions.assertThat(filmService.getFilms(filter)).extracting("id")
                .containsExactlyInAnyOrder(film3.getId(), film4.getId());
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

    @Test
    public void testGetFilmsByEmptyFilter() {
        Film film1 = createFilm();
        Film film2 = createFilm();
        Film film3 = createFilm();

        FilmFilter filter = new FilmFilter();
        Assertions.assertThat(filmService.getFilms(filter)).extracting("id")
                .containsExactlyInAnyOrder(film1.getId(), film2.getId(), film3.getId());
    }

    @Test
    public void testGetFilmsByFilterLastUpdate() {
        Film film1 = createFilm();
        film1.setLastUpdate(Instant.parse("2020-01-03T01:15:30.00Z"));
        filmRepository.save(film1);
        Film film2 = createFilm();
        Film film3 = createFilm();

        FilmFilter filter = new FilmFilter();
        filter.setLastUpdate(Instant.parse("2020-01-03T10:15:30.00Z"));
        Assertions.assertThat(filmService.getFilms(filter)).extracting("id")
                .containsExactlyInAnyOrder(film2.getId(), film3.getId());
    }

    @Test
    public void testGetFilmsByFilterRelise() {
        Film film1 = createFilm();
        film1.setRealiseYear(Instant.parse("2019-01-01T00:01:00.00Z"));
        filmRepository.save(film1);
        Film film2 = createFilm();
        film2.setRealiseYear(Instant.parse("2019-01-01T00:01:00.00Z"));
        filmRepository.save(film2);
        Film film3 = createFilm();
        film3.setRealiseYear(Instant.parse("2020-01-01T00:01:00.00Z"));
        filmRepository.save(film3);
        Film film4 = createFilm();
        film4.setRealiseYear(Instant.parse("2019-01-01T00:00:00.01Z"));
        filmRepository.save(film4);

        FilmFilter filter = new FilmFilter();
        filter.setRealiseYear(Instant.parse("2019-01-01T00:01:00.00Z"));
        Assertions.assertThat(filmService.getFilms(filter)).extracting("id")
                .containsExactlyInAnyOrder(film1.getId(), film2.getId());
    }

    @Test
    public void testGetFilmsByFilterAllParam() {
        Film film1 = createFilm();
        film1.setRealiseYear(Instant.parse("2019-01-01T00:01:00.00Z"));
        film1.setLastUpdate(Instant.parse("2020-01-01T00:01:00.00Z"));
        filmRepository.save(film1);
        Film film2 = createFilm();
        film2.setLastUpdate(Instant.parse("2020-01-01T00:01:00.00Z"));
        film2.setRealiseYear(Instant.parse("2019-01-01T00:01:00.00Z"));
        filmRepository.save(film2);
        Film film3 = createFilm();
        film3.setLength(33);
        film3.setLastUpdate(Instant.parse("2020-01-01T00:01:00.00Z"));
        film3.setRealiseYear(Instant.parse("2020-01-01T00:01:00.00Z"));
        filmRepository.save(film3);
        Film film4 = createFilm();
        film4.setLength(86);
        film4.setLastUpdate(Instant.parse("2020-01-01T00:00:00.01Z"));
        film4.setRealiseYear(Instant.parse("2019-01-01T00:00:00.01Z"));
        filmRepository.save(film4);

        FilmFilter filter = new FilmFilter();
        filter.setRealiseYear(Instant.parse("2019-01-01T00:01:00.00Z"));
        filter.setLastUpdate(Instant.parse("2020-01-01T00:01:00.00Z"));
        filter.setLength(83);
        Assertions.assertThat(filmService.getFilms(filter)).extracting("id")
                .containsExactlyInAnyOrder(film1.getId(), film2.getId());
    }

}