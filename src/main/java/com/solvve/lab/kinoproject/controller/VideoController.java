package com.solvve.lab.kinoproject.controller;


import com.solvve.lab.kinoproject.dto.video.VideoCreateDTO;
import com.solvve.lab.kinoproject.dto.video.VideoPatchDTO;
import com.solvve.lab.kinoproject.dto.video.VideoPutDTO;
import com.solvve.lab.kinoproject.dto.video.VideoReadDTO;
import com.solvve.lab.kinoproject.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/videos")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @GetMapping("/{id}")
    public VideoReadDTO getVideo(@PathVariable UUID id) {
        return videoService.getVideo(id);
    }

    @PostMapping
    public VideoReadDTO createVideo(@RequestBody VideoCreateDTO create) {
        return videoService.createVideo(create);
    }

    @PatchMapping("/{id}")
    public VideoReadDTO patchVideo(@PathVariable UUID id, @RequestBody VideoPatchDTO patch) {
        return videoService.patchVideo(id, patch);
    }

    @PutMapping("/{id}")
    public VideoReadDTO updateVideo(@PathVariable UUID id, @RequestBody VideoPutDTO put) {
        return videoService.updateVideo(id, put);
    }

    @DeleteMapping("/{id}")
    public void deleteVideo(@PathVariable UUID id) {
        videoService.deleteVideo(id);
    }

}
