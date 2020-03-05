package com.solvve.lab.kinoproject.service;


import com.solvve.lab.kinoproject.domain.Scene;
import com.solvve.lab.kinoproject.dto.scene.SceneCreateDTO;
import com.solvve.lab.kinoproject.dto.scene.ScenePatchDTO;
import com.solvve.lab.kinoproject.dto.scene.ScenePutDTO;
import com.solvve.lab.kinoproject.dto.scene.SceneReadDTO;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.repository.SceneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SceneService {

    @Autowired
    private SceneRepository sceneRepository;

    @Autowired
    private TranslationService translationService;

    public Scene getSceneRequired(UUID id) {
        return sceneRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(Scene.class, id));
    }

    public SceneReadDTO getScene(UUID id) {
        Scene scene = getSceneRequired(id);
        return translationService.toReadScene(scene);
    }

    public SceneReadDTO createScene(SceneCreateDTO create) {
        Scene scene = translationService.toEntityScene(create);
        scene = sceneRepository.save(scene);
        return translationService.toReadScene(scene);
    }

    public SceneReadDTO patchScene(UUID id, ScenePatchDTO patch) {
        Scene scene = getSceneRequired(id);
        translationService.patchEntityScene(patch, scene);
        scene = sceneRepository.save(scene);
        return translationService.toReadScene(scene);
    }

    public SceneReadDTO updateScene(UUID id, ScenePutDTO put) {
        Scene scene = getSceneRequired(id);
        translationService.updateEntityScene(put, scene);
        return translationService.toReadScene(scene);
    }

    public void deleteScene(UUID id) {
        sceneRepository.delete(getSceneRequired(id));
    }
}
