package com.solvve.lab.kinoproject.service;


import com.solvve.lab.kinoproject.domain.Name;
import com.solvve.lab.kinoproject.dto.NameCreateDTO;
import com.solvve.lab.kinoproject.dto.NameReadDTO;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.repository.NameRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
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
@Sql(statements = "delete from name", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class NameServiceTest {
    @Autowired
    NameRepository nameRepository;
    @Autowired
    private NameService nameService;

    @Test
    public void testGetName() {
        Name name = new Name();
        name.setId(UUID.randomUUID());
        name.setFirstName("Jhon");
        name.setLastName("Dou");
        name = nameRepository.save(name);

        NameReadDTO nameReadDTO = nameService.getName(name.getId());
        Assertions.assertThat(nameReadDTO).isEqualToComparingFieldByField(name);

    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetActorWrongId() {
        nameService.getName(UUID.randomUUID());

    }

    @Test
    public void testCreateActor() {
        NameCreateDTO create = new NameCreateDTO();
        create.setFirstName("Jhon");
        create.setLastName("Dou");
        NameReadDTO nameReadDTO = nameService.createName(create);
        Assertions.assertThat(create).isEqualToComparingFieldByField(nameReadDTO);
        Assert.assertNotNull(nameReadDTO.getId());

        Name name = nameRepository.findById(nameReadDTO.getId()).get();
        Assertions.assertThat(nameReadDTO).isEqualToComparingFieldByField(name);
    }


}
