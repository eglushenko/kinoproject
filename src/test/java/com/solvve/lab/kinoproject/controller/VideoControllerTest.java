package com.solvve.lab.kinoproject.controller;

import com.solvve.lab.kinoproject.domain.Video;
import com.solvve.lab.kinoproject.dto.video.VideoCreateDTO;
import com.solvve.lab.kinoproject.dto.video.VideoPatchDTO;
import com.solvve.lab.kinoproject.dto.video.VideoPutDTO;
import com.solvve.lab.kinoproject.dto.video.VideoReadDTO;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.service.VideoService;
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


@WebMvcTest(VideoController.class)
public class VideoControllerTest extends BaseControllerTest {

    @MockBean
    private VideoService videoService;

    private VideoReadDTO createVideoRead() {
        VideoReadDTO read = new VideoReadDTO();
        read.setId(UUID.randomUUID());
        read.setVideoLink("link");
        return read;
    }

    @Test
    public void testGetVideo() throws Exception {
        VideoReadDTO video = createVideoRead();

        Mockito.when(videoService.getVideo(video.getId())).thenReturn(video);

        String resultJson = mvc.perform(get("/api/v1/videos/{id}", video.getId()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        VideoReadDTO actual = objectMapper.readValue(resultJson, VideoReadDTO.class);

        Assertions.assertThat(actual)
                .isEqualToIgnoringGivenFields(video, "createdAt", "updatedAt");

        Mockito.verify(videoService).getVideo(video.getId());

    }

    @Test
    public void testGetVideoWrongId() throws Exception {
        UUID wrongId = UUID.randomUUID();

        EntityNotFoundException exception = new EntityNotFoundException(Video.class, wrongId);
        Mockito.when(videoService.getVideo(wrongId)).thenThrow(exception);

        String resultJson = mvc.perform(get("/api/v1/videos/{id}", wrongId))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();

        Assert.assertTrue(resultJson.contains(exception.getMessage()));
    }

    @Test
    public void testGetVideoWrongIdFormat() throws Exception {
        String resultJson = String.valueOf(mvc.perform(get("/api/v1/videos/00000001"))
                .andReturn().getResponse().getStatus());
        Assert.assertTrue(resultJson.contains("400"));
    }

    @Test
    public void testCreateVideo() throws Exception {
        VideoCreateDTO create = new VideoCreateDTO();
        create.setVideoLink("link");

        VideoReadDTO read = createVideoRead();

        Mockito.when(videoService.createVideo(create)).thenReturn(read);

        String resultJson = mvc.perform(post("/api/v1/videos")
                .content(objectMapper.writeValueAsString(create))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        VideoReadDTO actual = objectMapper.readValue(resultJson, VideoReadDTO.class);
        Assertions.assertThat(actual)
                .isEqualToIgnoringGivenFields(read, "createdAt", "updatedAt");
    }

    @Test
    public void testPatchVideo() throws Exception {
        VideoPatchDTO patch = new VideoPatchDTO();
        patch.setVideoLink("link2");

        VideoReadDTO read = createVideoRead();

        Mockito.when(videoService.patchVideo(read.getId(), patch)).thenReturn(read);

        String resultJson = mvc.perform(patch("/api/v1/videos/{id}", read.getId().toString())
                .content(objectMapper.writeValueAsString(patch))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        VideoReadDTO actual = objectMapper.readValue(resultJson, VideoReadDTO.class);
        Assert.assertEquals(read, actual);
    }

    @Test
    public void testPutVideo() throws Exception {
        VideoPutDTO putDTO = new VideoPutDTO();
        putDTO.setVideoLink("link");

        VideoReadDTO read = createVideoRead();

        Mockito.when(videoService.updateVideo(read.getId(), putDTO)).thenReturn(read);

        String resultJson = mvc.perform(put("/api/v1/videos/{id}", read.getId().toString())
                .content(objectMapper.writeValueAsString(putDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        VideoReadDTO actual = objectMapper.readValue(resultJson, VideoReadDTO.class);
        Assert.assertEquals(read, actual);
    }

    @Test
    public void testDeleteVideo() throws Exception {
        UUID id = UUID.randomUUID();

        mvc.perform(delete("/api/v1/videos/{id}", id.toString())).andExpect(status().isOk());

        Mockito.verify(videoService).deleteVideo(id);
    }

}