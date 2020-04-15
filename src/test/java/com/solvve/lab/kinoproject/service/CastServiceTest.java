package com.solvve.lab.kinoproject.service;

import com.solvve.lab.kinoproject.BaseTest;
import com.solvve.lab.kinoproject.domain.Cast;
import com.solvve.lab.kinoproject.domain.Film;
import com.solvve.lab.kinoproject.domain.Name;
import com.solvve.lab.kinoproject.dto.cast.CastCreateDTO;
import com.solvve.lab.kinoproject.dto.cast.CastPatchDTO;
import com.solvve.lab.kinoproject.dto.cast.CastPutDTO;
import com.solvve.lab.kinoproject.dto.cast.CastReadDTO;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.repository.CastRepository;
import com.solvve.lab.kinoproject.repository.FilmRepository;
import com.solvve.lab.kinoproject.repository.NameRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.UUID;


public class CastServiceTest extends BaseTest {

    @Autowired
    private CastRepository castRepository;

    @Autowired
    private NameRepository nameRepository;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private CastService castService;

    private Cast createCast() {
        Cast cast = generateFlatEntityWithoutId(Cast.class);
        Name n = createName();
        Film f = createFilm();
        cast.setName(n);
        cast.setFilm(f);
        return castRepository.save(cast);
    }

    private Name createName() {
        Name name = generateFlatEntityWithoutId(Name.class);
        return nameRepository.save(name);
    }

    private Film createFilm() {
        Film film = generateFlatEntityWithoutId(Film.class);
        return filmRepository.save(film);

    }

    @Test
    public void testGetCast() {
        Cast cast = createCast();
        CastReadDTO castReadDTO = castService.getCast(cast.getId());
        Assertions.assertThat(castReadDTO).isEqualToIgnoringGivenFields(cast,
                "filmId", "nameId", "createdAt", "updatedAt");
    }

    @Test
    public void testCastCreateDate() {
        Cast cast = createCast();
        Assert.assertNotNull(cast.getCreatedAt());

        Instant createDateBeforeLoad = cast.getCreatedAt();

        cast = castRepository.findById(cast.getId()).get();

        Instant createDateAfterLoad = cast.getCreatedAt();

        Assertions.assertThat(createDateBeforeLoad).isEqualTo(createDateAfterLoad);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetCastWrongId() {
        castService.getCast(UUID.randomUUID());
    }

    @Test
    public void testCreateCast() {
        Name n = createName();
        Film f = createFilm();
        CastCreateDTO create = generateObject(CastCreateDTO.class);
        create.setNameId(n.getId());
        create.setFilmId(f.getId());
        CastReadDTO read = castService.createCast(create);
        Assertions.assertThat(create).isEqualToComparingFieldByField(read);
        Assert.assertNotNull(read.getId());

        Cast cast = castRepository.findById(read.getId()).get();
        Assertions.assertThat(cast).isEqualToIgnoringGivenFields(read,
                "film", "name", "createdAt", "updatedAt");
    }

    @Test
    public void testPatchCast() {
        Cast cast = createCast();
        Name n = createName();
        Film f = createFilm();
        CastPatchDTO patch = generateObject(CastPatchDTO.class);
        patch.setNameId(n.getId());
        patch.setFilmId(f.getId());
        CastReadDTO read = castService.patchCast(cast.getId(), patch);

        Assertions.assertThat(patch).isEqualToIgnoringGivenFields(read,
                "film", "name", "createdAt", "updatedAt");

        cast = castRepository.findById(read.getId()).get();
        Assertions.assertThat(cast).isEqualToIgnoringGivenFields(read,
                "film", "name", "createdAt", "updatedAt");
    }

    @Test
    public void testCastEmptyPatch() {
        Cast cast = createCast();

        CastPatchDTO patch = new CastPatchDTO();

        CastReadDTO read = castService.patchCast(cast.getId(), patch);

        Assert.assertNotNull(read.getNameRoleInFilm());
        Assert.assertNotNull(read.getRoleInFilm());
        Assert.assertNotNull(read.getNameId());
        Assert.assertNotNull(read.getFilmId());

        Cast castAfterUpdate = castRepository.findById(read.getId()).get();

        Assert.assertNotNull(castAfterUpdate.getNameRoleInFilm());
        Assert.assertNotNull(castAfterUpdate.getRoleInFilm());
        Assert.assertNotNull(castAfterUpdate.getName());
        Assert.assertNotNull(castAfterUpdate.getFilm());

        Assertions.assertThat(cast).isEqualToIgnoringGivenFields(castAfterUpdate,
                "film", "name", "createdAt", "updatedAt");
    }

    @Test
    public void testPutCast() {
        Cast cast = createCast();
        Name name = createName();
        Film f = createFilm();
        CastPutDTO put = generateObject(CastPutDTO.class);
        put.setNameId(name.getId());
        put.setFilmId(f.getId());
        CastReadDTO read = castService.updateCast(cast.getId(), put);
        Assertions.assertThat(put).isEqualToIgnoringGivenFields(read,
                "film", "name", "createdAt", "updatedAt");

        cast = castRepository.findById(read.getId()).get();
        Assertions.assertThat(cast).isEqualToIgnoringGivenFields(read,
                "film", "name", "createdAt", "updatedAt");
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

    @Test(expected = EntityNotFoundException.class)
    public void testCreateCastWithWrongFilm() {
        Name n = createName();
        CastCreateDTO create = generateObject(CastCreateDTO.class);
        create.setNameId(n.getId());
        create.setFilmId(UUID.randomUUID());

        castService.createCast(create);
    }

}