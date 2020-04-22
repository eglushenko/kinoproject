package com.solvve.lab.kinoproject.job;

import com.solvve.lab.kinoproject.BaseTest;
import com.solvve.lab.kinoproject.domain.Customer;
import com.solvve.lab.kinoproject.domain.Film;
import com.solvve.lab.kinoproject.domain.Rate;
import com.solvve.lab.kinoproject.enums.RateObjectType;
import com.solvve.lab.kinoproject.repository.CustomerRepository;
import com.solvve.lab.kinoproject.repository.FilmRepository;
import com.solvve.lab.kinoproject.repository.RateRepository;
import com.solvve.lab.kinoproject.service.FilmService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.UUID;

public class UpdateAverageRateOfFilmJobTest extends BaseTest {

    @Autowired
    private UpdateAverageRateOfFilmJob updateAverageRateOfFilmJob;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private RateRepository rateRepository;

    @SpyBean
    private FilmService filmService;

    private Customer createCustomer() {
        Customer customer = generateFlatEntityWithoutId(Customer.class);
        return customerRepository.save(customer);
    }

    private Film createFilm() {
        Film film = generateFlatEntityWithoutId(Film.class);
        film.setAverageRate(null);
        return filmRepository.save(film);
    }


    @Test
    public void testUpdateAverageRateOfFilms() {
        Customer c1 = createCustomer();
        Customer c2 = createCustomer();
        Film film = createFilm();
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

        updateAverageRateOfFilmJob.updateAverageRateOfFilm();

        film = filmRepository.findById(film.getId()).get();
        Assert.assertEquals(3.5, film.getAverageRate(), Double.MIN_NORMAL);
    }

    @Test
    public void testFilmsUpdatedIndependently() {
        Customer c1 = createCustomer();
        Customer c2 = createCustomer();
        Film film = createFilm();
        film.setAverageRate(null);
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

        UUID[] filedId = new UUID[1];
        Mockito.doAnswer(invocationOnMock -> {
            if (filedId[0] == null) {
                filedId[0] = invocationOnMock.getArgument(0);
                throw new RuntimeException();
            }
            return invocationOnMock.callRealMethod();
        }).when(filmService).updateAverageRateOfFilm(Mockito.any());

        updateAverageRateOfFilmJob.updateAverageRateOfFilm();

        for (Film f : filmRepository.findAll()) {
            if (f.getId().equals(filedId[0])) {
                Assert.assertNull(f.getAverageRate());
            } else {
                Assert.assertNotNull(f.getAverageRate());
            }
        }
    }

}