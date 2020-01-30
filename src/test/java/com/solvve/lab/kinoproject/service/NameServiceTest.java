package com.solvve.lab.kinoproject.service;


import com.solvve.lab.kinoproject.domain.Name;
import com.solvve.lab.kinoproject.dto.name.NameCreateDTO;
import com.solvve.lab.kinoproject.dto.name.NamePatchDTO;
import com.solvve.lab.kinoproject.dto.name.NamePutDTO;
import com.solvve.lab.kinoproject.dto.name.NameReadDTO;
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

    private Name createName() {
        Name name = new Name();
        name.setFirstName("Jhon");
        name.setLastName("Dou");
        return nameRepository.save(name);
    }

    @Test
    public void testGetName() {
        Name name = createName();

        NameReadDTO nameReadDTO = nameService.getName(name.getId());
        Assertions.assertThat(nameReadDTO).isEqualToIgnoringGivenFields(name, "casts");

    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetNameWrongId() {
        nameService.getName(UUID.randomUUID());

    }

    @Test
    public void testCreateName() {
        NameCreateDTO create = new NameCreateDTO();
        create.setFirstName("Jhon");
        create.setLastName("Dou");
        NameReadDTO nameReadDTO = nameService.createName(create);
        Assertions.assertThat(create).isEqualToComparingFieldByField(nameReadDTO);
        Assert.assertNotNull(nameReadDTO.getId());

        Name name = nameRepository.findById(nameReadDTO.getId()).get();
        Assertions.assertThat(nameReadDTO).isEqualToIgnoringGivenFields(name, "casts");
    }

    @Test
    public void testPatchName() {
        Name name = createName();

        NamePatchDTO patch = new NamePatchDTO();
        patch.setFirstName("Jhon");
        patch.setLastName("Smith");
        NameReadDTO read = nameService.patchName(name.getId(), patch);

        Assertions.assertThat(patch).isEqualToIgnoringGivenFields(read, "casts");

        name = nameRepository.findById(read.getId()).get();
        Assertions.assertThat(name).isEqualToIgnoringGivenFields(read, "casts");
    }

    @Test
    public void testPutName() {
        Name name = createName();

        NamePutDTO put = new NamePutDTO();
        put.setFirstName("Jhon");
        put.setLastName("Dou");
        NameReadDTO read = nameService.putName(name.getId(), put);

        Assertions.assertThat(put).isEqualToIgnoringGivenFields(read, "casts");

        name = nameRepository.findById(read.getId()).get();
        Assertions.assertThat(name).isEqualToIgnoringGivenFields(read, "casts");
    }

    @Test
    public void testPatchNameEmptyPatch() {
        Name name = createName();

        NamePatchDTO patch = new NamePatchDTO();

        NameReadDTO read = nameService.patchName(name.getId(), patch);

        Assert.assertNotNull(read.getFirstName());
        Assert.assertNotNull(read.getLastName());

        Name nameAfterUpdate = nameRepository.findById(read.getId()).get();

        Assert.assertNotNull(nameAfterUpdate.getFirstName());
        Assert.assertNotNull(nameAfterUpdate.getLastName());

        Assertions.assertThat(name).isEqualToIgnoringGivenFields(nameAfterUpdate, "casts");
    }

    @Test
    public void testDeleteName() {
        Name name = createName();

        nameService.deleteName(name.getId());

        Assert.assertFalse(nameRepository.existsById(name.getId()));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDeleteNameNotFoundId() {
        nameService.deleteName(UUID.randomUUID());
    }


}
