package com.solvve.lab.kinoproject.controller;

import com.solvve.lab.kinoproject.domain.Scene;
import com.solvve.lab.kinoproject.dto.scene.SceneCreateDTO;
import com.solvve.lab.kinoproject.dto.scene.ScenePatchDTO;
import com.solvve.lab.kinoproject.dto.scene.ScenePutDTO;
import com.solvve.lab.kinoproject.dto.scene.SceneReadDTO;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.service.SceneService;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(SceneController.class)
public class SceneControllerTest extends BaseControllerTest {

    @MockBean
    private SceneService sceneService;

    private SceneReadDTO createSceneRead() {
        SceneReadDTO read = new SceneReadDTO();
        read.setId(UUID.randomUUID());
        read.setSceneLink("link");
        return read;
    }

    @Test
    public void testGetScene() throws Exception {
        SceneReadDTO scene = createSceneRead();

        Mockito.when(sceneService.getScene(scene.getId())).thenReturn(scene);

        String resultJson = mvc.perform(get("/api/v1/scenes/{id}", scene.getId()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        SceneReadDTO actual = objectMapper.readValue(resultJson, SceneReadDTO.class);

        Assertions.assertThat(actual)
                .isEqualToIgnoringGivenFields(scene, "createdAt", "updatedAt");

        Mockito.verify(sceneService).getScene(scene.getId());

    }

    @Test
    public void testGetSceneWrongId() throws Exception {
        UUID wrongId = UUID.randomUUID();

        EntityNotFoundException exception = new EntityNotFoundException(Scene.class, wrongId);
        Mockito.when(sceneService.getScene(wrongId)).thenThrow(exception);

        String resultJson = mvc.perform(get("/api/v1/scenes/{id}", wrongId))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();

        Assert.assertTrue(resultJson.contains(exception.getMessage()));
    }

    @Test
    public void testGetSceneWrongIdFormat() throws Exception {
        String resultJson = String.valueOf(mvc.perform(get("/api/v1/scenes/00000001"))
                .andReturn().getResponse().getStatus());
        Assert.assertTrue(resultJson.contains("400"));
    }

    @Test
    public void testCreateScene() throws Exception {
        SceneCreateDTO create = new SceneCreateDTO();
        create.setSceneLink("link");

        SceneReadDTO read = createSceneRead();

        Mockito.when(sceneService.createScene(create)).thenReturn(read);

        String resultJson = mvc.perform(post("/api/v1/scenes")
                .content(objectMapper.writeValueAsString(create))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        SceneReadDTO actual = objectMapper.readValue(resultJson, SceneReadDTO.class);
        Assertions.assertThat(actual)
                .isEqualToIgnoringGivenFields(read, "createdAt", "updatedAt");
    }

    @Test
    public void testPatchScene() throws Exception {
        ScenePatchDTO patch = new ScenePatchDTO();
        patch.setSceneLink("link2");

        SceneReadDTO read = createSceneRead();

        Mockito.when(sceneService.patchScene(read.getId(), patch)).thenReturn(read);

        String resultJson = mvc.perform(patch("/api/v1/scenes/{id}", read.getId().toString())
                .content(objectMapper.writeValueAsString(patch))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        SceneReadDTO actual = objectMapper.readValue(resultJson, SceneReadDTO.class);
        Assert.assertEquals(read, actual);
    }

    @Test
    public void testPutScene() throws Exception {
        ScenePutDTO putDTO = new ScenePutDTO();
        putDTO.setSceneLink("link");

        SceneReadDTO read = createSceneRead();

        Mockito.when(sceneService.updateScene(read.getId(), putDTO)).thenReturn(read);

        String resultJson = mvc.perform(put("/api/v1/scenes/{id}", read.getId().toString())
                .content(objectMapper.writeValueAsString(putDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        SceneReadDTO actual = objectMapper.readValue(resultJson, SceneReadDTO.class);
        Assert.assertEquals(read, actual);
    }

    @Test
    public void testDeleteScene() throws Exception {
        UUID id = UUID.randomUUID();

        mvc.perform(delete("/api/v1/scenes/{id}", id.toString())).andExpect(status().isOk());

        Mockito.verify(sceneService).deleteScene(id);
    }

}