package com.solvve.lab.kinoproject.service;

import com.solvve.lab.kinoproject.BaseTest;
import com.solvve.lab.kinoproject.domain.Video;
import com.solvve.lab.kinoproject.dto.video.VideoCreateDTO;
import com.solvve.lab.kinoproject.dto.video.VideoPatchDTO;
import com.solvve.lab.kinoproject.dto.video.VideoPutDTO;
import com.solvve.lab.kinoproject.dto.video.VideoReadDTO;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.repository.VideoRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;


public class VideoServiceTest extends BaseTest {

    @Autowired
    private VideoService videoService;

    @Autowired
    private VideoRepository videoRepository;

    private Video createVideo() {
        Video video = new Video();
        video.setVideoLink("link");
        return videoRepository.save(video);
    }

    @Test
    public void testGetVideo() {
        Video video = createVideo();

        VideoReadDTO read = videoService.getVideo(video.getId());
        Assertions.assertThat(read)
                .isEqualToIgnoringGivenFields(video, "createdAt", "updatedAt");

    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetVideoWrongId() {
        videoService.getVideo(UUID.randomUUID());

    }

    @Test
    public void testCreateVideo() {
        VideoCreateDTO create = new VideoCreateDTO();
        create.setVideoLink("link");
        VideoReadDTO read = videoService.createVideo(create);
        Assertions.assertThat(create).isEqualToComparingFieldByField(read);
        Assert.assertNotNull(read.getId());

        Video video = videoRepository.findById(read.getId()).get();
        Assertions.assertThat(read)
                .isEqualToIgnoringGivenFields(video, "createdAt", "updatedAt");
    }

    @Test
    public void testPatchVideo() {
        Video video = createVideo();

        VideoPatchDTO patch = new VideoPatchDTO();
        patch.setVideoLink("11111");
        VideoReadDTO read = videoService.patchVideo(video.getId(), patch);

        Assertions.assertThat(patch)
                .isEqualToIgnoringGivenFields(read, "createdAt", "updatedAt");

        video = videoRepository.findById(read.getId()).get();
        Assertions.assertThat(video)
                .isEqualToIgnoringGivenFields(read, "createdAt", "updatedAt");
    }

    @Test
    public void testPutVideo() {
        Video video = createVideo();

        VideoPutDTO put = new VideoPutDTO();
        put.setVideoLink("link2");
        VideoReadDTO read = videoService.updateVideo(video.getId(), put);

        Assertions.assertThat(put)
                .isEqualToIgnoringGivenFields(read, "createdAt", "updatedAt");

        video = videoRepository.findById(read.getId()).get();
        Assertions.assertThat(video)
                .isEqualToIgnoringGivenFields(read, "createdAt", "updatedAt");
    }

    @Test
    public void testPatchVideoEmptyPatch() {
        Video video = createVideo();

        VideoPatchDTO patch = new VideoPatchDTO();

        VideoReadDTO read = videoService.patchVideo(video.getId(), patch);

        Assert.assertNotNull(read.getVideoLink());

        Video videoAfterUpdate = videoRepository.findById(read.getId()).get();

        Assert.assertNotNull(videoAfterUpdate.getVideoLink());

        Assertions.assertThat(video)
                .isEqualToIgnoringGivenFields(videoAfterUpdate,
                        "createdAt", "updatedAt");
    }

    @Test
    public void testDeleteVideo() {
        Video video = createVideo();

        videoService.deleteVideo(video.getId());

        Assert.assertFalse(videoRepository.existsById(video.getId()));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDeleteVideoNotFoundId() {
        videoService.deleteVideo(UUID.randomUUID());
    }

}