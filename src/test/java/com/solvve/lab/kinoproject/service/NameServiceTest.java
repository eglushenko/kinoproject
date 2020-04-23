package com.solvve.lab.kinoproject.service;


import com.solvve.lab.kinoproject.BaseTest;
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
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;


public class NameServiceTest extends BaseTest {

    @Autowired
    private NameRepository nameRepository;

    @Autowired
    private NameService nameService;

    private Name createName() {
        Name name = generateFlatEntityWithoutId(Name.class);
        return nameRepository.save(name);
    }

    @Test
    public void testGetName() {
        Name name = createName();

        NameReadDTO nameReadDTO = nameService.getName(name.getId());
        Assertions.assertThat(nameReadDTO)
                .isEqualToIgnoringGivenFields(name, "casts", "createdAt", "updatedAt");

    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetNameWrongId() {
        nameService.getName(UUID.randomUUID());

    }

    @Test
    public void testCreateName() {
        NameCreateDTO create = generateObject(NameCreateDTO.class);

        NameReadDTO nameReadDTO = nameService.createName(create);
        Assertions.assertThat(create).isEqualToComparingFieldByField(nameReadDTO);
        Assert.assertNotNull(nameReadDTO.getId());

        Name name = nameRepository.findById(nameReadDTO.getId()).get();
        Assertions.assertThat(nameReadDTO)
                .isEqualToIgnoringGivenFields(name, "casts", "createdAt", "updatedAt");
    }

    @Test
    public void testPatchName() {
        Name name = createName();

        NamePatchDTO patch = generateObject(NamePatchDTO.class);
        NameReadDTO read = nameService.patchName(name.getId(), patch);

        Assertions.assertThat(patch)
                .isEqualToIgnoringGivenFields(read, "casts", "createdAt", "updatedAt");

        name = nameRepository.findById(read.getId()).get();
        Assertions.assertThat(name)
                .isEqualToIgnoringGivenFields(read, "casts", "createdAt", "updatedAt");
    }

    @Test
    public void testPutName() {
        Name name = createName();

        NamePutDTO put = generateObject(NamePutDTO.class);
        NameReadDTO read = nameService.updateName(name.getId(), put);

        Assertions.assertThat(put)
                .isEqualToIgnoringGivenFields(read, "casts", "createdAt", "updatedAt");

        name = nameRepository.findById(read.getId()).get();
        Assertions.assertThat(name)
                .isEqualToIgnoringGivenFields(read, "casts", "createdAt", "updatedAt");
    }

    @Test
    public void testPatchNameEmptyPatch() {
        Name name = createName();

        NamePatchDTO patch = new NamePatchDTO();

        NameReadDTO read = nameService.patchName(name.getId(), patch);

        Assert.assertNotNull(read.getBiography());
        Assert.assertNotNull(read.getGender());

        Name nameAfterUpdate = nameRepository.findById(read.getId()).get();

        Assert.assertNotNull(nameAfterUpdate.getBiography());
        Assert.assertNotNull(nameAfterUpdate.getGender());

        Assertions.assertThat(name)
                .isEqualToIgnoringGivenFields(nameAfterUpdate,
                        "casts", "createdAt", "updatedAt");
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
