package com.solvve.lab.kinoproject.repository;


import com.solvve.lab.kinoproject.domain.Name;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(statements = "delete from name", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class NameRepositoryTest {

    @Autowired
    NameRepository nameRepository;

    @Test
    public void saveNameTest() {
        Name name = new Name();
        name = nameRepository.save(name);
        assertNotNull(name.getId());
        assertTrue(nameRepository.findById(name.getId()).isPresent());
    }
}
