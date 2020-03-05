package com.solvve.lab.kinoproject.controller;


import com.solvve.lab.kinoproject.dto.scene.SceneCreateDTO;
import com.solvve.lab.kinoproject.dto.scene.ScenePatchDTO;
import com.solvve.lab.kinoproject.dto.scene.ScenePutDTO;
import com.solvve.lab.kinoproject.dto.scene.SceneReadDTO;
import com.solvve.lab.kinoproject.service.SceneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/scenes")
public class SceneController {

    @Autowired
    private SceneService sceneService;

    @GetMapping("/{id}")
    public SceneReadDTO getScene(@PathVariable UUID id) {
        return sceneService.getScene(id);
    }

    @PostMapping
    public SceneReadDTO createScene(@RequestBody SceneCreateDTO create) {
        return sceneService.createScene(create);
    }

    @PatchMapping("/{id}")
    public SceneReadDTO patchScene(@PathVariable UUID id, @RequestBody ScenePatchDTO patch) {
        return sceneService.patchScene(id, patch);
    }

    @PutMapping("/{id}")
    public SceneReadDTO updateScene(@PathVariable UUID id, @RequestBody ScenePutDTO put) {
        return sceneService.updateScene(id, put);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteScene(@PathVariable UUID id) {
        sceneService.deleteScene(id);
    }


}