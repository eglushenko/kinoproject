package com.solvve.lab.kinoproject;


import com.solvve.lab.kinoproject.domain.AbstractEntity;
import org.bitbucket.brunneng.br.Configuration;
import org.bitbucket.brunneng.br.RandomObjectGenerator;
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

    private RandomObjectGenerator generator = new RandomObjectGenerator();

    private RandomObjectGenerator flatGenerator;

    {
        Configuration c = new Configuration();
        c.setFlatMode(true);
        flatGenerator = new RandomObjectGenerator(c);
    }

    protected <T extends AbstractEntity> T generateEntityWithoutId(Class<T> entityClass) {
        T entity = generator.generateRandomObject(entityClass);
        entity.setId(null);
        return entity;
    }

    protected <T extends AbstractEntity> T generateFlatEntityWithoutId(Class<T> entityClass) {
        T entity = flatGenerator.generateRandomObject(entityClass);
        entity.setId(null);
        return entity;
    }

    protected <T> T generateObject(Class<T> objectClass) {
        return generator.generateRandomObject(objectClass);
    }


}
