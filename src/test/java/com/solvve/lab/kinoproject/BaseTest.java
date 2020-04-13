package com.solvve.lab.kinoproject;


import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(statements = {
        "delete from video",
        "delete from poster",
        "delete from media",
        "delete from comment",
        "delete from country",
        "delete from genere",
        "delete from language",
        "delete from like",
        "delete from news",
        "delete from review",
        "delete from cast",
        "delete from rate",
        "delete from scene",
        "delete from name",
        "delete from film",
        "delete from typo",
        "delete from customer"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)

public abstract class BaseTest {
}
