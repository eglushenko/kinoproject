package com.solvve.lab.kinoproject.controller;


import com.solvve.lab.kinoproject.dto.comment.CommentCreateDTO;
import com.solvve.lab.kinoproject.dto.comment.CommentPatchDTO;
import com.solvve.lab.kinoproject.dto.comment.CommentPutDTO;
import com.solvve.lab.kinoproject.dto.comment.CommentReadDTO;
import com.solvve.lab.kinoproject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/{id}")
    public CommentReadDTO getComment(@PathVariable UUID id) {
        return commentService.getComment(id);
    }

    @PostMapping
    public CommentReadDTO createComment(@RequestBody CommentCreateDTO createDTO) {
        return commentService.createComment(createDTO);
    }

    @PatchMapping("/{id}")
    public CommentReadDTO patchComment(@PathVariable UUID id, @RequestBody CommentPatchDTO patchDTO) {
        return commentService.patchComment(id, patchDTO);
    }

    @PutMapping("/{id}")
    public CommentReadDTO updateComment(@PathVariable UUID id, @RequestBody CommentPutDTO put) {
        return commentService.updateComment(id, put);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable UUID id) {
        commentService.deleteComment(id);
    }
}
