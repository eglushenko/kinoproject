package com.solvve.lab.kinoproject.service;

import com.solvve.lab.kinoproject.BaseTest;
import com.solvve.lab.kinoproject.domain.Customer;
import com.solvve.lab.kinoproject.domain.Film;
import com.solvve.lab.kinoproject.domain.Rate;
import com.solvve.lab.kinoproject.dto.FilmFilter;
import com.solvve.lab.kinoproject.dto.FilmReadExtendedDTO;
import com.solvve.lab.kinoproject.dto.film.FilmCreateDTO;
import com.solvve.lab.kinoproject.dto.film.FilmPatchDTO;
import com.solvve.lab.kinoproject.dto.film.FilmPutDTO;
import com.solvve.lab.kinoproject.dto.film.FilmReadDTO;
import com.solvve.lab.kinoproject.enums.RateObjectType;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.repository.CustomerRepository;
import com.solvve.lab.kinoproject.repository.FilmRepository;
import com.solvve.lab.kinoproject.repository.RateRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;


public class FilmServiceTest extends BaseTest {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private FilmService filmService;

    private Film createFilm() {
        Film film = generateFlatEntityWithoutId(Film.class);
        return filmRepository.save(film);
    }

    private Customer createCustomer() {
        Customer customer = generateFlatEntityWithoutId(Customer.class);
        return customerRepository.save(customer);
    }

    @Test
    public void testGetFilm() {
        Film film = createFilm();

        FilmReadDTO readDTO = filmService.getFilm(film.getId());
        Assertions.assertThat(readDTO)
                .isEqualToIgnoringGivenFields(film,
                        "reviews", "filmGeneres", "media",
                        "casts", "scenes", "createdAt", "updatedAt");
    }

    @Test(expected = EntityNotFoundException.class)
    public void getFilmWrongId() {
        filmService.getFilm(UUID.randomUUID());

    }

    @Test
    public void testCreateFilm() {
        FilmCreateDTO create = generateObject(FilmCreateDTO.class);
        FilmReadDTO filmReadDTO = filmService.createFilm(create);
        Assertions.assertThat(create)
                .isEqualToIgnoringGivenFields(filmReadDTO,
                        "reviews", "filmGeneres", "media",
                        "casts", "scenes", "createdAt", "updatedAt");
        Assert.assertNotNull(filmReadDTO.getId());

        Film film = filmRepository.findById(filmReadDTO.getId()).get();
        Assertions.assertThat(filmReadDTO)
                .isEqualToIgnoringGivenFields(film,
                        "reviews", "filmGeneres", "media",
                        "casts", "scenes", "createdAt", "updatedAt");
    }

    @Test
    public void testPatchFilm() {
        Film film = createFilm();

        FilmPatchDTO patch = generateObject(FilmPatchDTO.class);
        FilmReadDTO read = filmService.patchFilm(film.getId(), patch);

        Assertions.assertThat(patch)
                .isEqualToIgnoringGivenFields(read,
                        "reviews", "filmGeneres", "media",
                        "casts", "scenes", "createdAt", "updatedAt");

        film = filmRepository.findById(read.getId()).get();
        Assertions.assertThat(film)
                .isEqualToIgnoringGivenFields(read,
                        "reviews", "filmGeneres", "media",
                        "casts", "scenes", "createdAt", "updatedAt");
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
                        "reviews", "filmGeneres", "media",
                        "casts", "scenes", "createdAt", "updatedAt");
    }

    @Test
    public void testPutFilm() {
        Film film = createFilm();

        FilmPutDTO put = generateObject(FilmPutDTO.class);

        FilmReadDTO read = filmService.updateFilm(film.getId(), put);

        Assertions.assertThat(put)
                .isEqualToIgnoringGivenFields(read,
                        "reviews", "filmGeneres", "media",
                        "casts", "scenes", "createdAt", "updatedAt");

        film = filmRepository.findById(read.getId()).get();
        Assertions.assertThat(film)
                .isEqualToIgnoringGivenFields(read,
                        "reviews", "filmGeneres", "media", "mediaSet",
                        "casts", "scenes", "createdAt", "updatedAt");
    }

    @Test
    public void testGetFilmExtended() {
        Film film = createFilm();

        FilmReadExtendedDTO read = filmService.getFilmExtended(film.getId());
        Assertions.assertThat(read)
                .isEqualToIgnoringGivenFields(film,
                        "reviews", "filmGeneres", "media", "casts", "scenes",
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
        Assertions.assertThat(filmService.getFilms(filmFilter, Pageable.unpaged())
                .getData()).extracting("id")
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
        film3.setLength(83);
        filmRepository.save(film3);
        Film film4 = createFilm();
        film4.setLength(83);
        filmRepository.save(film4);

        FilmFilter filter = new FilmFilter();
        filter.setLength(83);

        Assertions.assertThat(filmService.getFilms(filter, Pageable.unpaged())
                .getData()).extracting("id")
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
        Assertions.assertThat(filmService.getFilms(filter, Pageable.unpaged())
                .getData()).extracting("id")
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
        Assertions.assertThat(filmService.getFilms(filter, Pageable.unpaged())
                .getData()).extracting("id")
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
        Assertions.assertThat(filmService.getFilms(filter, Pageable.unpaged())
                .getData()).extracting("id")
                .containsExactlyInAnyOrder(film1.getId(), film2.getId());
    }

    @Test
    public void testGetFilmsByFilterAllParam() {
        Film film1 = createFilm();
        film1.setLength(83);
        film1.setRealiseYear(Instant.parse("2019-01-01T00:01:00.00Z"));
        film1.setLastUpdate(Instant.parse("2020-01-01T00:01:00.00Z"));
        filmRepository.save(film1);
        Film film2 = createFilm();
        film2.setLength(83);
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
        Assertions.assertThat(filmService.getFilms(filter, Pageable.unpaged())
                .getData()).extracting("id")
                .containsExactlyInAnyOrder(film1.getId(), film2.getId());
    }

    @Test
    public void testGetFilmsWithEmptyFilterWithPagingAndSort() {
        Film film1 = createFilm();
        film1.setLength(60);
        filmRepository.save(film1);
        Film film2 = createFilm();
        film2.setLength(62);
        filmRepository.save(film2);
        createFilm();
        createFilm();

        FilmFilter filter = new FilmFilter();
        PageRequest pageRequest = PageRequest.of(1, 2, Sort.by(Sort.Direction.DESC, "length"));
        Assertions.assertThat(filmService.getFilms(filter, pageRequest).getData()).extracting("id")
                .isEqualTo(Arrays.asList(film2.getId(), film1.getId()));
    }

    @Test
    public void updateAverageRateFilms() {
        Customer c1 = createCustomer();
        Customer c2 = createCustomer();
        Film film = createFilm();
        film.setAverageRate(0.0);
        filmRepository.save(film);

        Rate r1 = new Rate();
        r1.setCustomer(c1);
        r1.setType(RateObjectType.FILM);
        r1.setRate(2.0);
        r1.setRatedObjectId(film.getId());
        rateRepository.save(r1);

        Rate r2 = new Rate();
        r2.setCustomer(c2);
        r2.setType(RateObjectType.FILM);
        r2.setRate(5.0);
        r2.setRatedObjectId(film.getId());
        rateRepository.save(r2);

        filmService.updateAverageRateOfFilm(film.getId());

        film = filmRepository.findById(film.getId()).get();
        Assert.assertEquals(3.5, film.getAverageRate(), Double.MIN_NORMAL);

    }


}