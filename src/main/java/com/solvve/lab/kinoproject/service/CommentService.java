package com.solvve.lab.kinoproject.service;

import com.solvve.lab.kinoproject.domain.Comment;
import com.solvve.lab.kinoproject.dto.comment.CommentCreateDTO;
import com.solvve.lab.kinoproject.dto.comment.CommentPatchDTO;
import com.solvve.lab.kinoproject.dto.comment.CommentPutDTO;
import com.solvve.lab.kinoproject.dto.comment.CommentReadDTO;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TranslationService translationService;

    private Comment getCommentRequired(UUID id) {
        return commentRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Comment.class, id));
    }

    public CommentReadDTO getComment(UUID id) {
        Comment comment = getCommentRequired(id);
        return translationService.toReadComment(comment);

    }

    public CommentReadDTO createComment(CommentCreateDTO create) {
        Comment comment = translationService.toEntityComment(create);
        comment = commentRepository.save(comment);
        return translationService.toReadComment(comment);
    }

    public CommentReadDTO patchComment(UUID id, CommentPatchDTO patch) {
        Comment comment = getCommentRequired(id);
        translationService.patchEntityComment(patch, comment);
        comment = commentRepository.save(comment);
        return translationService.toReadComment(comment);
    }

    public CommentReadDTO putComment(UUID id, CommentPutDTO put) {
        Comment comment = getCommentRequired(id);
        translationService.putEntityComment(put, comment);
        comment = commentRepository.save(comment);
        return translationService.toReadComment(comment);
    }


    public void deleteComment(UUID id) {
        commentRepository.delete(getCommentRequired(id));
    }
}
