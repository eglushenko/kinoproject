package com.solvve.lab.kinoproject.service;


import com.solvve.lab.kinoproject.domain.Video;
import com.solvve.lab.kinoproject.dto.video.VideoCreateDTO;
import com.solvve.lab.kinoproject.dto.video.VideoPatchDTO;
import com.solvve.lab.kinoproject.dto.video.VideoPutDTO;
import com.solvve.lab.kinoproject.dto.video.VideoReadDTO;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private TranslationService translationService;

    private Video getVideoRequired(UUID id) {
        return videoRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Video.class, id));
    }

    public VideoReadDTO getVideo(UUID id) {
        Video video = getVideoRequired(id);
        return translationService.translate(video, VideoReadDTO.class);
    }

    public VideoReadDTO createVideo(VideoCreateDTO create) {
        Video video = translationService.translate(create, Video.class);
        video = videoRepository.save(video);
        return translationService.translate(video, VideoReadDTO.class);
    }

    public VideoReadDTO patchVideo(UUID id, VideoPatchDTO patch) {
        Video video = getVideoRequired(id);
        translationService.map(patch, video);
        video = videoRepository.save(video);
        return translationService.translate(video, VideoReadDTO.class);
    }

    public VideoReadDTO updateVideo(UUID id, VideoPutDTO put) {
        Video video = getVideoRequired(id);
        translationService.map(put, video);
        video = videoRepository.save(video);
        return translationService.translate(video, VideoReadDTO.class);
    }

    public void deleteVideo(UUID id) {
        videoRepository.delete(getVideoRequired(id));
    }
}
