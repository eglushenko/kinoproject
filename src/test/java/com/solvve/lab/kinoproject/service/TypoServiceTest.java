package com.solvve.lab.kinoproject.service;

import com.solvve.lab.kinoproject.BaseTest;
import com.solvve.lab.kinoproject.domain.Customer;
import com.solvve.lab.kinoproject.domain.News;
import com.solvve.lab.kinoproject.domain.Typo;
import com.solvve.lab.kinoproject.dto.typo.TypoCreateDTO;
import com.solvve.lab.kinoproject.dto.typo.TypoPatchDTO;
import com.solvve.lab.kinoproject.dto.typo.TypoPutDTO;
import com.solvve.lab.kinoproject.dto.typo.TypoReadDTO;
import com.solvve.lab.kinoproject.enums.Gender;
import com.solvve.lab.kinoproject.enums.Role;
import com.solvve.lab.kinoproject.enums.TypoStatus;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.exception.EntityWrongStatusException;
import com.solvve.lab.kinoproject.job.UpdateTypoStatusCheckingToOpenJob;
import com.solvve.lab.kinoproject.repository.CustomerRepository;
import com.solvve.lab.kinoproject.repository.NewsRepository;
import com.solvve.lab.kinoproject.repository.RepositoryHelper;
import com.solvve.lab.kinoproject.repository.TypoRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;


public class TypoServiceTest extends BaseTest {

    @Autowired
    private TypoRepository typoRepository;

    @Autowired
    private TypoService typoService;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RepositoryHelper repositoryHelper;

    @Autowired
    private UpdateTypoStatusCheckingToOpenJob updateTypoStatusCheckingToOpenJob;

    private Customer createCustomer() {
        Customer customer = new Customer();
        customer.setLogin("user");
        customer.setFirstName("Jhon");
        customer.setLastName("Dou");
        customer.setEmail("mail@mail.ua");
        customer.setRole(Role.USER);
        customer.setGender(Gender.MALE);
        return customerRepository.save(customer);
    }

    private Typo createTypo() {
        Customer c = createCustomer();
        Typo typo = new Typo();
        typo.setTypoMessage("some text");
        typo.setStatus(TypoStatus.OPEN);
        typo.setTypoLink("link");
        typo.setCustomer(c);
        return typoRepository.save(typo);
    }

    @Test
    public void testGetTypo() {
        Typo typo = createTypo();

        TypoReadDTO read = typoService.getTypo(typo.getId());
        Assertions.assertThat(read)
                .isEqualToIgnoringGivenFields(typo, "customerId", "createdAt", "updatedAt");
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetTypoWrongId() {
        typoService.getTypo(UUID.randomUUID());

    }

    @Test
    public void testCreateTypo() {
        Customer customer = createCustomer();
        TypoCreateDTO create = new TypoCreateDTO();
        create.setTypoMessage("some text");
        create.setStatus(TypoStatus.OPEN);
        create.setTypoLink("link");
        create.setCustomerId(customer.getId());
        TypoReadDTO read = typoService.createTypo(create);
        Assertions.assertThat(create)
                .isEqualToIgnoringGivenFields(read, "customer", "createdAt", "updatedAt");
        Assert.assertNotNull(read.getId());

        Typo typo = typoRepository.findById(read.getId()).get();
        Assertions.assertThat(read)
                .isEqualToIgnoringGivenFields(typo, "customerId", "createdAt", "updatedAt");
    }

    @Test
    public void testPatchTypo() {
        Typo typo = createTypo();

        TypoPatchDTO patch = new TypoPatchDTO();
        patch.setTypoMessage("some text");
        patch.setStatus(TypoStatus.OPEN);
        patch.setTypoLink("link");
        TypoReadDTO read = typoService.patchTypo(typo.getId(), patch);

        Assertions.assertThat(patch)
                .isEqualToIgnoringGivenFields(read, "customerId", "createdAt", "updatedAt");

        typo = typoRepository.findById(read.getId()).get();
        Assertions.assertThat(typo)
                .isEqualToIgnoringGivenFields(read, "customer", "createdAt", "updatedAt");
    }

    @Test
    public void testPatchTypoEmptyPatch() {
        Typo typo = createTypo();

        TypoPatchDTO patch = new TypoPatchDTO();

        TypoReadDTO read = typoService.patchTypo(typo.getId(), patch);

        Assert.assertNotNull(read.getStatus());
        Assert.assertNotNull(read.getTypoLink());
        Assert.assertNotNull(read.getTypoMessage());

        Typo afterUpdate = typoRepository.findById(read.getId()).get();

        Assert.assertNotNull(afterUpdate.getStatus());
        Assert.assertNotNull(afterUpdate.getTypoLink());
        Assert.assertNotNull(afterUpdate.getTypoMessage());
        Assertions.assertThat(typo)
                .isEqualToIgnoringGivenFields(afterUpdate,
                        "customer", "createdAt", "updatedAt");

    }

    @Test
    public void testPutTypo() {
        Customer c = createCustomer();
        Typo typo = createTypo();
        TypoPutDTO put = new TypoPutDTO();
        put.setTypoMessage("some text");
        put.setStatus(TypoStatus.OPEN);
        put.setTypoLink("link");
        put.setCustomerId(c.getId());
        TypoReadDTO read = typoService.updateTypo(typo.getId(), put);

        Assertions.assertThat(put)
                .isEqualToIgnoringGivenFields(read,
                        "customerId", "createdAt", "updatedAt");

        typo = typoRepository.findById(read.getId()).get();
        Assertions.assertThat(typo)
                .isEqualToIgnoringGivenFields(read,
                        "customer", "createdAt", "updatedAt");
    }

    @Test
    public void testFixNews() {
        Customer customer = createCustomer();
        News news = new News();
        news.setTextNews("error txt");
        newsRepository.save(news);

        Typo typo = createTypo();
        Typo typo1 = createTypo();
        Typo typo2 = createTypo();
        typo.setErrorText("error txt");
        typo.setRightText("all works fine");
        typo1.setErrorText("error txt");
        typo2.setErrorText("error txt");
        typoRepository.save(typo);
        typoRepository.save(typo1);
        typoRepository.save(typo2);
        typoService.fixTypoNews(typo.getId(), news.getId(), customer.getId());
        typo2 = typoRepository.findById(typo2.getId()).get();
        Assert.assertEquals(TypoStatus.CLOSED, typo2.getStatus());
    }

    @Test(expected = EntityWrongStatusException.class)
    public void testFixNewsErrorStatus() {
        Customer customer = createCustomer();
        News news = new News();
        news.setTextNews("error txt");
        newsRepository.save(news);

        Typo typo = createTypo();
        typo.setStatus(TypoStatus.CHECKING);
        typo.setErrorText("error txt");
        typo.setRightText("all works fine");
        typoRepository.save(typo);
        typoService.fixTypoNews(typo.getId(), news.getId(), customer.getId());
    }


    @Test
    public void testDeleteTypo() {
        Typo typo = createTypo();

        typoService.deleteTypo(typo.getId());

        Assert.assertFalse(typoRepository.existsById(typo.getId()));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDeleteTypoNotFoundId() {
        typoService.deleteTypo(UUID.randomUUID());
    }
}