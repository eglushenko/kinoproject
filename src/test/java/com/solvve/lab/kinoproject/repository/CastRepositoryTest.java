package com.solvve.lab.kinoproject.repository;

import com.solvve.lab.kinoproject.BaseTest;
import com.solvve.lab.kinoproject.domain.Cast;
import com.solvve.lab.kinoproject.domain.Film;
import com.solvve.lab.kinoproject.domain.Name;
import com.solvve.lab.kinoproject.enums.NameFilmRole;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionSystemException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


public class CastRepositoryTest extends BaseTest {

    @Autowired
    private CastRepository castRepository;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private NameRepository nameRepository;

    private Cast createCast() {
        Film f = createFilm();
        Name n = createName();
        Cast cast = new Cast();
        cast.setRoleInFilm(NameFilmRole.ACTOR);
        cast.setNameRoleInFilm("Jhon Dou");
        cast.setName(n);
        cast.setFilm(f);
        return castRepository.save(cast);
    }

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

    private Name createName() {
        Name name = new Name();
        name.setName("Jhon");
        return nameRepository.save(name);
    }

    @Test
    public void testCastCreateDate() {
        Cast cast = createCast();
        Assert.assertNotNull(cast.getCreatedAt());

        Instant createDateBeforeLoad = cast.getCreatedAt();

        cast = castRepository.findById(cast.getId()).get();

        Instant createDateAfterLoad = cast.getCreatedAt();

        Assertions.assertThat(createDateBeforeLoad).isEqualTo(createDateAfterLoad);
    }

    @Test
    public void testCastUpdateDate() {
        Cast cast = createCast();
        Assert.assertNotNull(cast.getUpdatedAt());

        Instant updateDateBeforeLoad = cast.getUpdatedAt();

        cast.setRoleInFilm(NameFilmRole.PRODUSER);
        cast = castRepository.save(cast);
        cast = castRepository.findById(cast.getId()).get();

        Instant updateDateAfterLoad = cast.getUpdatedAt();

        Assert.assertNotNull(updateDateAfterLoad);
        Assertions.assertThat(updateDateAfterLoad).isAfter(updateDateBeforeLoad);
    }

    @Test(expected = TransactionSystemException.class)
    public void testSaveCastValidation() {
        Cast cast = new Cast();
        castRepository.save(cast);
    }

}