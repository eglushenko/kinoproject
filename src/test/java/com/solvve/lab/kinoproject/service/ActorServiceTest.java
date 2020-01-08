package com.solvve.lab.kinoproject.service;


import com.solvve.lab.kinoproject.domain.Actor;
import com.solvve.lab.kinoproject.dto.ActorReadDTO;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.repository.ActorRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Sql(statements = "delete from actor", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ActorServiceTest {
    @Autowired
    ActorRepository actorRepository;
    @Autowired
    private ActorService actorService;

    @Test
    public void getActorTest() {
        Actor actor = new Actor();
        actor.setId(UUID.randomUUID());
        actor.setFirstName("Jhon");
        actor.setLastName("Dou");
        actor = actorRepository.save(actor);

        ActorReadDTO actorReadDTO = actorService.getActor(actor.getId());
        Assertions.assertThat(actorReadDTO).isEqualToComparingFieldByField(actor);

    }

    @Test(expected = EntityNotFoundException.class)
    public void getActorWrongId() {
        actorService.getActor(UUID.randomUUID());

    }


}
