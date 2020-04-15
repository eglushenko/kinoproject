package com.solvve.lab.kinoproject.service;

import com.solvve.lab.kinoproject.BaseTest;
import com.solvve.lab.kinoproject.domain.Customer;
import com.solvve.lab.kinoproject.domain.Film;
import com.solvve.lab.kinoproject.domain.Rate;
import com.solvve.lab.kinoproject.dto.rate.RateCreateDTO;
import com.solvve.lab.kinoproject.dto.rate.RatePatchDTO;
import com.solvve.lab.kinoproject.dto.rate.RatePutDTO;
import com.solvve.lab.kinoproject.dto.rate.RateReadDTO;
import com.solvve.lab.kinoproject.enums.RateObjectType;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.job.UpdateAverageRateOfFilmJob;
import com.solvve.lab.kinoproject.repository.CustomerRepository;
import com.solvve.lab.kinoproject.repository.FilmRepository;
import com.solvve.lab.kinoproject.repository.RateRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.UUID;


public class RateServiceTest extends BaseTest {

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private RateService rateService;

    @SpyBean
    private FilmService filmService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private UpdateAverageRateOfFilmJob updateAverageRateOfFilmJob;

    private Customer createCustomer() {
        Customer customer = generateFlatEntityWithoutId(Customer.class);
        return customerRepository.save(customer);
    }

    private Film createFilm() {
        Film film = generateFlatEntityWithoutId(Film.class);
        return filmRepository.save(film);
    }


    private Rate createRate() {
        Customer c = createCustomer();
        Rate rate = generateFlatEntityWithoutId(Rate.class);
        rate.setCustomer(c);
        return rateRepository.save(rate);
    }

    @Test
    public void testGetRate() {
        Rate rate = createRate();

        RateReadDTO read = rateService.getRate(rate.getId());
        Assertions.assertThat(read)
                .isEqualToIgnoringGivenFields(rate, "customerId", "createdAt", "updatedAt");

    }

    @Test
    public void testCreateRate() {
        Customer c = createCustomer();
        RateCreateDTO create = generateObject(RateCreateDTO.class);
        create.setCustomerId(c.getId());
        RateReadDTO read = rateService.createRate(create);
        Assertions.assertThat(create).isEqualToComparingFieldByField(read);
        Assert.assertNotNull(read.getId());

        Rate rate = rateRepository.findById(read.getId()).get();
        Assertions.assertThat(read)
                .isEqualToIgnoringGivenFields(rate, "customerId", "createdAt", "updatedAt");
    }

    @Test
    public void testPatchRate() {
        Rate rate = createRate();

        RatePatchDTO patch = generateObject(RatePatchDTO.class);
        patch.setCustomerId(rate.getCustomer().getId());
        RateReadDTO read = rateService.patchRate(rate.getId(), patch);

        Assertions.assertThat(patch)
                .isEqualToIgnoringGivenFields(read, "customerId", "createdAt", "updatedAt");

        rate = rateRepository.findById(read.getId()).get();
        Assertions.assertThat(rate)
                .isEqualToIgnoringGivenFields(read, "customer", "createdAt", "updatedAt");
    }

    @Test
    public void testPutRate() {
        Rate rate = createRate();
        Customer customer = createCustomer();

        RatePutDTO put = generateObject(RatePutDTO.class);
        put.setCustomerId(customer.getId());
        RateReadDTO read = rateService.updateRate(rate.getId(), put);

        Assertions.assertThat(put)
                .isEqualToIgnoringGivenFields(read, "customerId", "createdAt", "updatedAt");

        rate = rateRepository.findById(read.getId()).get();
        Assertions.assertThat(rate)
                .isEqualToIgnoringGivenFields(read, "customer", "createdAt", "updatedAt");
    }

    @Test
    public void testPatchRateEmptyPatch() {
        Rate rate = createRate();

        RatePatchDTO patch = new RatePatchDTO();

        RateReadDTO read = rateService.patchRate(rate.getId(), patch);

        Assert.assertNotNull(read.getCustomerId());
        Assert.assertNotNull(read.getType());
        Assert.assertNotNull(read.getRate());
        Assert.assertNotNull(read.getRatedObjectId());


        Rate rateAfterUpdate = rateRepository.findById(read.getId()).get();

        Assert.assertNotNull(rateAfterUpdate.getCustomer());
        Assert.assertNotNull(rateAfterUpdate.getType());
        Assert.assertNotNull(rateAfterUpdate.getRate());
        Assert.assertNotNull(rateAfterUpdate.getRatedObjectId());


        Assertions.assertThat(rate)
                .isEqualToIgnoringGivenFields(rateAfterUpdate,
                        "customer", "createdAt", "updatedAt");
    }

    @Test
    public void testDeleteRate() {
        Rate rate = createRate();

        rateService.deleteRate(rate.getId());

        Assert.assertFalse(rateRepository.existsById(rate.getId()));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDeleteRateNotFoundId() {
        rateService.deleteRate(UUID.randomUUID());
    }

    @Test
    public void testUpdateAverageRateOfFilms() {
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

        updateAverageRateOfFilmJob.updateAverageRateOfFilm();

        film = filmRepository.findById(film.getId()).get();
        Assert.assertEquals(3.5, film.getAverageRate(), Double.MIN_NORMAL);
    }

    //TODO
    @Test
    public void testFilmsUpdatedIndependently() {
        Customer c1 = createCustomer();
        Customer c2 = createCustomer();
        Film film = createFilm();

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