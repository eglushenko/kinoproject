package com.solvve.lab.kinoproject.repository;


import com.solvve.lab.kinoproject.BaseTest;
import com.solvve.lab.kinoproject.domain.Customer;
import com.solvve.lab.kinoproject.domain.Film;
import com.solvve.lab.kinoproject.domain.Rate;
import com.solvve.lab.kinoproject.dto.film.FilmTopRatedReadDTO;
import com.solvve.lab.kinoproject.enums.RateObjectType;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class FilmRepositoryTest extends BaseTest {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private TransactionTemplate transactionTemplate;

    private Customer createCustomer() {
        Customer customer = generateFlatEntityWithoutId(Customer.class);
        return customerRepository.save(customer);
    }

    private Film createFilm() {
        Film film = generateFlatEntityWithoutId(Film.class);
        return filmRepository.save(film);
    }

    private Rate createRate(UUID id, Customer customer, boolean mark) {
        Rate rate = new Rate();
        rate.setRatedObjectId(id);
        rate.setType(RateObjectType.FILM);
        if (mark) {
            rate.setRate(1.0);
        }
        rate.setCustomer(customer);
        return rateRepository.save(rate);
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
        Instant realiseYear = LocalDateTime.of(2018, 01, 01, 00, 01).toInstant(utc);
        Instant last = LocalDateTime.of(2019, 12, 01, 00, 01).toInstant(utc);
        Film film1 = createFilm();
        film1.setLang("en");
        film1.setAverageRate(1.1);
        film1.setRealiseYear(realiseYear);
        film1.setLastUpdate(last);
        filmRepository.save(film1);
        Film film2 = createFilm();
        film2.setAverageRate(1.1);
        film2.setLang("en");
        film2.setRealiseYear(realiseYear);
        film2.setLastUpdate(last);
        filmRepository.save(film2);
        Film film3 = createFilm();

        List<Film> res = filmRepository.findFilmSortedByRealiseYearAndLastUpdate("en",
                1.1, last, realiseYear);
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

    @Test
    public void testGetTopRatedFilms() {
        Customer customer = createCustomer();

        int filmCount = 100;
        Set<UUID> filmsIds = new HashSet<>();
        for (int i = 0; i < filmCount; ++i) {
            Film f = createFilm();
            filmsIds.add(f.getId());

            createRate(f.getId(), customer, true);
            createRate(f.getId(), customer, true);
            createRate(f.getId(), customer, false);
        }
        List<FilmTopRatedReadDTO> topRatedFilms = filmRepository.getTopRatedFilms();
        Assertions.assertThat(topRatedFilms).isSortedAccordingTo(
                Comparator.comparing(FilmTopRatedReadDTO::getAverageRate).reversed());

        Assert.assertEquals(filmsIds, topRatedFilms.stream().map(FilmTopRatedReadDTO::getId)
                .collect(Collectors.toSet()));
        for (FilmTopRatedReadDTO f : topRatedFilms) {
            Assert.assertNotNull(f.getTitle());
            Assert.assertNotNull(f.getAverageRate());
            Assert.assertEquals(2, f.getCount());
        }
    }


}
