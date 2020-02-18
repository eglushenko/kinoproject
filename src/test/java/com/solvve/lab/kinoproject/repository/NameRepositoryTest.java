package com.solvve.lab.kinoproject.repository;


import com.solvve.lab.kinoproject.domain.Name;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(statements = "delete from name", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class NameRepositoryTest {

    @Autowired
    NameRepository nameRepository;

    private Name createName() {
        Name name = new Name();
        name.setFirstName("Jhon");
        name.setLastName("Dou");
        return nameRepository.save(name);
    }

    @Test
    public void testSaveName() {
        Name name = new Name();
        name = nameRepository.save(name);
        assertNotNull(name.getId());
        assertTrue(nameRepository.findById(name.getId()).isPresent());
    }

    @Test
    public void testNameCreateDate() {
        Name name = createName();
        Assert.assertNotNull(name.getCreatedAt());

        Instant createDateBeforeLoad = name.getCreatedAt();

        name = nameRepository.findById(name.getId()).get();

        Instant createDateAfterLoad = name.getCreatedAt();

        Assertions.assertThat(createDateBeforeLoad).isEqualTo(createDateAfterLoad);
    }

    @Test
    public void testNameUpdateDate() {
        Name name = createName();
        Assert.assertNotNull(name.getUpdatedAt());

        Instant updateDateBeforeLoad = name.getUpdatedAt();

        name.setLastName("Polur");
        name = nameRepository.save(name);
        name = nameRepository.findById(name.getId()).get();

        Instant updateDateAfterLoad = name.getUpdatedAt();

        Assert.assertNotNull(updateDateAfterLoad);
        Assertions.assertThat(updateDateAfterLoad).isAfter(updateDateBeforeLoad);
    }
}
