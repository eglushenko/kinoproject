package com.solvve.lab.kinoproject.repository;


import com.solvve.lab.kinoproject.domain.Actor;
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
@Sql(statements = "delete from actor", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ActorRepositoryTest {

    @Autowired
    ActorRepository actorRepository;

    @Test
    public void saveActorTest() {
        Actor actor = new Actor();
        actor = actorRepository.save(actor);
        assertNotNull(actor.getId());
        assertTrue(actorRepository.findById(actor.getId()).isPresent());
    }
}
