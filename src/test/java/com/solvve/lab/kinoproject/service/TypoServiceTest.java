package com.solvve.lab.kinoproject.service;

import com.solvve.lab.kinoproject.domain.Typo;
import com.solvve.lab.kinoproject.dto.typo.TypoCreateDTO;
import com.solvve.lab.kinoproject.dto.typo.TypoPatchDTO;
import com.solvve.lab.kinoproject.dto.typo.TypoPutDTO;
import com.solvve.lab.kinoproject.dto.typo.TypoReadDTO;
import com.solvve.lab.kinoproject.enums.TypoStatus;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.repository.TypoRepository;
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
@Sql(statements = "delete from typo", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class TypoServiceTest {
    @Autowired
    private TypoRepository typoRepository;

    @Autowired
    private TypoService typoService;

    private Typo createTypo() {
        Typo typo = new Typo();
        typo.setTypoMessege("some text");
        typo.setStatus(TypoStatus.OPEN);
        typo.setTypoLink("link");
        return typoRepository.save(typo);
    }

    @Test
    public void testGetTypo() {
        Typo typo = createTypo();

        TypoReadDTO read = typoService.getTypo(typo.getId());
        Assertions.assertThat(read).isEqualToIgnoringGivenFields(typo, "createdAt", "updatedAt");
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetTypoWrongId() {
        typoService.getTypo(UUID.randomUUID());

    }

    @Test
    public void testCreateTypo() {
        TypoCreateDTO create = new TypoCreateDTO();
        create.setTypoMessege("some text");
        create.setStatus(TypoStatus.OPEN);
        create.setTypoLink("link");
        TypoReadDTO read = typoService.createTypo(create);
        Assertions.assertThat(create).isEqualToIgnoringGivenFields(read, "createdAt", "updatedAt");
        Assert.assertNotNull(read.getId());

        Typo typo = typoRepository.findById(read.getId()).get();
        Assertions.assertThat(read).isEqualToIgnoringGivenFields(typo, "createdAt", "updatedAt");
    }

    @Test
    public void testPatchTypo() {
        Typo typo = createTypo();

        TypoPatchDTO patch = new TypoPatchDTO();
        patch.setTypoMessege("some text");
        patch.setStatus(TypoStatus.OPEN);
        patch.setTypoLink("link");
        TypoReadDTO read = typoService.patchTypo(typo.getId(), patch);

        Assertions.assertThat(patch).isEqualToIgnoringGivenFields(read, "createdAt", "updatedAt");

        typo = typoRepository.findById(read.getId()).get();
        Assertions.assertThat(typo).isEqualToIgnoringGivenFields(read, "createdAt", "updatedAt");
    }

    @Test
    public void testPatchTypoEmptyPatch() {
        Typo typo = createTypo();

        TypoPatchDTO patch = new TypoPatchDTO();

        TypoReadDTO read = typoService.patchTypo(typo.getId(), patch);

        Assert.assertNotNull(read.getStatus());
        Assert.assertNotNull(read.getTypoLink());
        Assert.assertNotNull(read.getTypoMessege());

        Typo afterUpdate = typoRepository.findById(read.getId()).get();

        Assert.assertNotNull(afterUpdate.getStatus());
        Assert.assertNotNull(afterUpdate.getTypoLink());
        Assert.assertNotNull(afterUpdate.getTypoMessege());
        Assertions.assertThat(typo).isEqualToIgnoringGivenFields(afterUpdate, "createdAt", "updatedAt");

    }

    @Test
    public void testPutTypo() {
        Typo typo = createTypo();

        TypoPutDTO put = new TypoPutDTO();
        put.setTypoMessege("some text");
        put.setStatus(TypoStatus.OPEN);
        put.setTypoLink("link");
        TypoReadDTO read = typoService.putTypo(typo.getId(), put);

        Assertions.assertThat(put).isEqualToIgnoringGivenFields(read, "createdAt", "updatedAt");

        typo = typoRepository.findById(read.getId()).get();
        Assertions.assertThat(typo).isEqualToIgnoringGivenFields(read, "createdAt", "updatedAt");
    }

    @Test
    public void testDeleteTypo() {
        Typo typo = createTypo();

        typoService.deleteTypo(typo.getId());

        Assert.assertFalse(typoRepository.existsById(typo.getId()));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDeleteTypoNotFoundId() {
        typoService.deleteTypo(UUID.randomUUID());
    }
}