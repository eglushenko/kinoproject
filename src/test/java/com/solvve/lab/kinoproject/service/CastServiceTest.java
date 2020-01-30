package com.solvve.lab.kinoproject.service;

import com.solvve.lab.kinoproject.domain.Cast;
import com.solvve.lab.kinoproject.dto.cast.CastCreateDTO;
import com.solvve.lab.kinoproject.dto.cast.CastPatchDTO;
import com.solvve.lab.kinoproject.dto.cast.CastPutDTO;
import com.solvve.lab.kinoproject.dto.cast.CastReadDTO;
import com.solvve.lab.kinoproject.enums.NameFilmRole;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.repository.CastRepository;
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
@Sql(statements = "delete from cast", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CastServiceTest {

    @Autowired
    CastRepository castRepository;

    @Autowired
    private CastService castService;

    private Cast createCast() {
        Cast cast = new Cast();
        //cast.setName("somt");
        cast.setRoleInFilm(NameFilmRole.ACTOR);
        cast.setNameRoleInFilm("Jhon Dou");
        return castRepository.save(cast);
    }

    @Test
    public void testGetCast() {
        Cast cast = createCast();
        CastReadDTO castReadDTO = castService.getCast(cast.getId());
        Assertions.assertThat(castReadDTO).isEqualToIgnoringGivenFields(cast, "film", "name");
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetCastWrongId() {
        castService.getCast(UUID.randomUUID());
    }

    @Test
    public void createCastTest() {
        CastCreateDTO create = new CastCreateDTO();
        CastReadDTO castReadDTO = castService.createCast(create);
        Assertions.assertThat(create).isEqualToIgnoringGivenFields(castReadDTO, "film", "name");
        Assert.assertNotNull(castReadDTO.getId());

        Cast cast = castRepository.findById(castReadDTO.getId()).get();
        Assertions.assertThat(castReadDTO).isEqualToIgnoringGivenFields(cast, "film", "name");
    }

    @Test
    public void testPatchCast() {
        Cast cast = createCast();

        CastPatchDTO patch = new CastPatchDTO();
        //patch.setName("somt");
        patch.setRoleInFilm(NameFilmRole.ACTOR);
        patch.setNameRoleInFilm("Jhon Dou");
        CastReadDTO read = castService.patchCast(cast.getId(), patch);

        Assertions.assertThat(patch).isEqualToIgnoringGivenFields(read, "film", "name");

        cast = castRepository.findById(read.getId()).get();
        Assertions.assertThat(cast).isEqualToIgnoringGivenFields(read, "film", "name");
    }

    @Test
    public void testPatchCastEmptyPatch() {
        Cast cast = createCast();

        CastPatchDTO patch = new CastPatchDTO();

        CastReadDTO read = castService.patchCast(cast.getId(), patch);

        //Assert.assertNotNull(read.getName());
        Assert.assertNotNull(read.getNameRoleInFilm());
        Assert.assertNotNull(read.getRoleInFilm());

        Cast castAfterUpdate = castRepository.findById(read.getId()).get();

        //Assert.assertNotNull(castAfterUpdate.getName());
        Assert.assertNotNull(castAfterUpdate.getNameRoleInFilm());
        Assert.assertNotNull(castAfterUpdate.getRoleInFilm());

        Assertions.assertThat(cast).isEqualToIgnoringGivenFields(castAfterUpdate, "film", "name");
    }

    @Test
    public void testPutCast() {
        Cast cast = createCast();

        CastPutDTO put = new CastPutDTO();
        //put.setName("somt");
        put.setRoleInFilm(NameFilmRole.DIRECTOR);
        put.setNameRoleInFilm("Jhon Dou");
        CastReadDTO read = castService.putCast(cast.getId(), put);

        Assertions.assertThat(put).isEqualToIgnoringGivenFields(read, "film", "name");

        cast = castRepository.findById(read.getId()).get();
        Assertions.assertThat(cast).isEqualToIgnoringGivenFields(read, "film", "name");
    }

    @Test
    public void testDeleteCast() {
        Cast cast = createCast();

        castService.deleteCast(cast.getId());

        Assert.assertFalse(castRepository.existsById(cast.getId()));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDeleteCastNotFoundId() {
        castService.deleteCast(UUID.randomUUID());
    }

}