package com.solvve.lab.kinoproject.repository;


import com.solvve.lab.kinoproject.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;


@TestPropertySource(properties = "spring.liquibase.change-log=classpath:db/changelog/db.changelog-master.xml")
public class LiquibaseLoadDataTest extends BaseTest {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private CastRepository castRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private NameRepository nameRepository;

    @Autowired
    private TypoRepository typoRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void testDataLoad() {
        Assert.assertTrue(filmRepository.count() > 0);
        Assert.assertTrue(castRepository.count() > 0);
        Assert.assertTrue(commentRepository.count() > 0);
        Assert.assertTrue(nameRepository.count() > 0);
        Assert.assertTrue(typoRepository.count() > 0);
        Assert.assertTrue(customerRepository.count() > 0);
    }
}
