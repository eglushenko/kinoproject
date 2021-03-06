package com.solvve.lab.kinoproject.service;

import com.solvve.lab.kinoproject.BaseTest;
import com.solvve.lab.kinoproject.domain.Film;
import com.solvve.lab.kinoproject.domain.Scene;
import com.solvve.lab.kinoproject.dto.scene.SceneCreateDTO;
import com.solvve.lab.kinoproject.dto.scene.ScenePatchDTO;
import com.solvve.lab.kinoproject.dto.scene.ScenePutDTO;
import com.solvve.lab.kinoproject.dto.scene.SceneReadDTO;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.repository.FilmRepository;
import com.solvve.lab.kinoproject.repository.SceneRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;


public class SceneServiceTest extends BaseTest {

    @Autowired
    private SceneService sceneService;

    @Autowired
    private SceneRepository sceneRepository;

    @Autowired
    private FilmRepository filmRepository;

    private Film createFilm() {
        Film film = generateFlatEntityWithoutId(Film.class);
        return filmRepository.save(film);
    }


    private Scene createScene() {
        Film f = createFilm();
        Scene scene = generateFlatEntityWithoutId(Scene.class);
        scene.setFilm(f);
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
        SceneCreateDTO create = generateObject(SceneCreateDTO.class);
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
        Film film = createFilm();
        Scene scene = createScene();

        ScenePatchDTO patch = generateObject(ScenePatchDTO.class);
        patch.setFilmId(film.getId());
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

        ScenePutDTO put = generateObject(ScenePutDTO.class);
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
        Assert.assertNotNull(read.getFilmId());

        Scene sceneAfterUpdate = sceneRepository.findById(read.getId()).get();

        Assert.assertNotNull(sceneAfterUpdate.getSceneLink());
        Assert.assertNotNull(sceneAfterUpdate.getFilm().getId());

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

    @Test(expected = EntityNotFoundException.class)
    public void testCreateSceneWithWrongFilm() {
        SceneCreateDTO create = generateObject(SceneCreateDTO.class);
        create.setFilmId(UUID.randomUUID());
        sceneService.createScene(create);
    }

}