package com.solvve.lab.kinoproject.repository;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = "spring.liquibase.change-log=classpath:db/changelog/db.changelog-master.xml")
@Sql(statements = {
        "delete from cast",
        "delete from comment",
        "delete from name",
        "delete from typo",
        "delete from customer",
        "delete from film"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)

public class LiquibaseLoadDataTest {

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
