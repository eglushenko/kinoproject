package com.solvve.lab.kinoproject.service;

import com.solvve.lab.kinoproject.domain.Film;
import com.solvve.lab.kinoproject.domain.Scene;
import com.solvve.lab.kinoproject.dto.scene.SceneCreateDTO;
import com.solvve.lab.kinoproject.dto.scene.ScenePatchDTO;
import com.solvve.lab.kinoproject.dto.scene.ScenePutDTO;
import com.solvve.lab.kinoproject.dto.scene.SceneReadDTO;
import com.solvve.lab.kinoproject.enums.RateMPAA;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.repository.FilmRepository;
import com.solvve.lab.kinoproject.repository.SceneRepository;
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
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Sql(statements = "delete from scene", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class SceneServiceTest {

    @Autowired
    private SceneService sceneService;

    @Autowired
    private SceneRepository sceneRepository;

    @Autowired
    private FilmRepository filmRepository;

    private Film createFilm() {
        Film film = new Film();
        film.setCategory("category");
        film.setCountry("UA");
        film.setFilmText("");
        film.setLang("UA");
        film.setLength(83);
        film.setRate(4.3);
        film.setTitle("LEGO FILM");
        film.setMpaa(RateMPAA.PG);
        film.setLastUpdate(Instant.parse("2020-01-03T10:15:30.00Z"));
        return filmRepository.save(film);
    }


    private Scene createScene() {
        Film film = createFilm();
        Scene scene = new Scene();
        scene.setSceneLink("link");
        scene.setFilm(film);
        return sceneRepository.save(scene);
    }

    @Test
    public void testGetScene() {
        Scene scene = createScene();

        SceneReadDTO sceneReadDTO = sceneService.getScene(scene.getId());
        Assertions.assertThat(sceneReadDTO)
                .isEqualToIgnoringGivenFields(scene, "filmId", "createdAt", "updatedAt");

    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetSceneWrongId() {
        sceneService.getScene(UUID.randomUUID());

    }

    @Test
    public void testCreateScene() {
        Film film = createFilm();
        SceneCreateDTO create = new SceneCreateDTO();
        create.setSceneLink("link");
        create.setFilmId(film.getId());
        SceneReadDTO read = sceneService.createScene(create);
        Assertions.assertThat(create).isEqualToComparingFieldByField(read);
        Assert.assertNotNull(read.getId());

        Scene scene = sceneRepository.findById(read.getId()).get();
        Assertions.assertThat(read)
                .isEqualToIgnoringGivenFields(scene, "filmId", "createdAt", "updatedAt");
    }

    @Test
    public void testPatchScene() {
        Scene scene = createScene();

        ScenePatchDTO patch = new ScenePatchDTO();
        patch.setSceneLink("11111");
        SceneReadDTO read = sceneService.patchScene(scene.getId(), patch);

        Assertions.assertThat(patch)
                .isEqualToIgnoringGivenFields(read, "filmId", "createdAt", "updatedAt");

        scene = sceneRepository.findById(read.getId()).get();
        Assertions.assertThat(scene)
                .isEqualToIgnoringGivenFields(read, "film", "createdAt", "updatedAt");
    }

    @Test
    public void testPutScene() {
        Film film = createFilm();
        Scene scene = createScene();

        ScenePutDTO put = new ScenePutDTO();
        put.setSceneLink("link");
        put.setFilmId(film.getId());
        SceneReadDTO read = sceneService.updateScene(scene.getId(), put);

        Assertions.assertThat(put)
                .isEqualToIgnoringGivenFields(read, "filmId", "createdAt", "updatedAt");

        scene = sceneRepository.findById(read.getId()).get();
        Assertions.assertThat(scene)
                .isEqualToIgnoringGivenFields(read, "film", "createdAt", "updatedAt");
    }

    @Test
    public void testPatchSceneEmptyPatch() {
        Scene scene = createScene();

        ScenePatchDTO patch = new ScenePatchDTO();

        SceneReadDTO read = sceneService.patchScene(scene.getId(), patch);

        Assert.assertNotNull(read.getSceneLink());

        Scene sceneAfterUpdate = sceneRepository.findById(read.getId()).get();

        Assert.assertNotNull(sceneAfterUpdate.getSceneLink());

        Assertions.assertThat(scene)
                .isEqualToIgnoringGivenFields(sceneAfterUpdate,
                        "film", "createdAt", "updatedAt");
    }

    @Test
    public void testDeleteScene() {
        Scene scene = createScene();

        sceneService.deleteScene(scene.getId());

        Assert.assertFalse(sceneRepository.existsById(scene.getId()));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDeleteSceneNotFoundId() {
        sceneService.deleteScene(UUID.randomUUID());
    }

}