package com.solvve.lab.kinoproject.controller;


import com.solvve.lab.kinoproject.dto.like.LikeCreateDTO;
import com.solvve.lab.kinoproject.dto.like.LikePatchDTO;
import com.solvve.lab.kinoproject.dto.like.LikePutDTO;
import com.solvve.lab.kinoproject.dto.like.LikeReadDTO;
import com.solvve.lab.kinoproject.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @GetMapping("/{id}")
    public LikeReadDTO getLike(@PathVariable UUID id) {
        return likeService.getLike(id);
    }

    @PostMapping
    public LikeReadDTO createLike(@RequestBody LikeCreateDTO create) {
        return likeService.createLike(create);
    }

    @PatchMapping("/{id}")
    public LikeReadDTO patchLike(@PathVariable UUID id, @RequestBody LikePatchDTO patch) {
        return likeService.patchLike(id, patch);
    }

    @PutMapping("/{id}")
    public LikeReadDTO updateLike(@PathVariable UUID id, @RequestBody LikePutDTO put) {
        return likeService.updateLike(id, put);
    }

    @DeleteMapping("/{id}")
    public void deleteLike(@PathVariable UUID id) {
        likeService.deleteLike(id);
    }
}
