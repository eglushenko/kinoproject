package com.solvve.lab.kinoproject.service;

import com.solvve.lab.kinoproject.BaseTest;
import com.solvve.lab.kinoproject.domain.Film;
import com.solvve.lab.kinoproject.domain.Genere;
import com.solvve.lab.kinoproject.dto.genere.GenereReadDTO;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.exception.hander.LinkDuplicatedException;
import com.solvve.lab.kinoproject.repository.FilmRepository;
import com.solvve.lab.kinoproject.repository.GenereRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.UUID;

public class GenereServiceTest extends BaseTest {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private GenereRepository genereRepository;

    @Autowired
    private GenereService genereService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    private Film createFilm() {
        Film film = generateFlatEntityWithoutId(Film.class);
        return filmRepository.save(film);
    }

    private Genere createGenere() {
        Genere genere = generateFlatEntityWithoutId(Genere.class);
        return genereRepository.save(genere);
    }


    @Test
    public void testAddGenereToFilm() {
        Film film = createFilm();
        Genere genere = createGenere();

        List<GenereReadDTO> res = genereService.addGenereToFilm(film.getId(), genere.getId());

        GenereReadDTO expected = new GenereReadDTO();
        expected.setId(genere.getId());
        expected.setGenereName(genere.getGenereName());
        expected.setGenreDescription(genere.getGenreDescription());
        Assertions.assertThat(res).containsExactlyInAnyOrder(expected);

        transactionTemplate.executeWithoutResult(g -> {
            Film filmAfterSave = filmRepository.findById(film.getId()).get();
            Assertions.assertThat(filmAfterSave.getFilmGeneres()).extracting(Genere::getId)
                    .containsExactlyInAnyOrder(genere.getId());
        });

    }

    @Test
    public void testDublicateGenereToFilm() {
        Film film = createFilm();
        Genere genere = createGenere();

        genereService.addGenereToFilm(film.getId(), genere.getId());

        Assertions.assertThatThrownBy(() -> {
            genereService.addGenereToFilm(film.getId(), genere.getId());
        }).isInstanceOf(LinkDuplicatedException.class);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testWrongGenereId() {
        Film film = createFilm();
        UUID genere = UUID.randomUUID();

        genereService.addGenereToFilm(film.getId(), genere);

    }

    @Test(expected = EntityNotFoundException.class)
    public void testWrongFilmId() {
        Genere genere = createGenere();
        UUID film = UUID.randomUUID();

        genereService.addGenereToFilm(film, genere.getId());

    }

    @Test
    public void testRemoveGenereFromFilm() {
        Film film = createFilm();
        Genere genere = createGenere();


        genereService.addGenereToFilm(film.getId(), genere.getId());
        film = filmRepository.findById(film.getId()).get();
        List<GenereReadDTO> reminingGeneres = genereService.removeGenereFromFilm(film.getId(), genere.getId());

        Assert.assertTrue(reminingGeneres.isEmpty());

        Film finalFilm = film;
        transactionTemplate.executeWithoutResult(g -> {
            Film filmAfterRemove = filmRepository.findById(finalFilm.getId()).get();
            Assert.assertTrue(filmAfterRemove.getFilmGeneres().isEmpty());
        });
    }

    @Test(expected = javax.persistence.EntityNotFoundException.class)
    public void testDeleteNotAddGenere() {
        Film film = createFilm();
        Genere genere = createGenere();
        genereService.removeGenereFromFilm(film.getId(), genere.getId());

    }

    @Test(expected = javax.persistence.EntityNotFoundException.class)
    public void testDeleteNotExistedGenere() {
        Film film = createFilm();
        genereService.removeGenereFromFilm(film.getId(), UUID.randomUUID());

    }

}