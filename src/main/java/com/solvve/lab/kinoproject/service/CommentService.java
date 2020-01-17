package com.solvve.lab.kinoproject.service;

import com.solvve.lab.kinoproject.domain.Comment;
import com.solvve.lab.kinoproject.dto.CommentCreateDTO;
import com.solvve.lab.kinoproject.dto.CommentPatchDTO;
import com.solvve.lab.kinoproject.dto.CommentReadDTO;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    private Comment getCommentRequired(UUID id) {
        return commentRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException(Comment.class, id);
        });
    }

    public CommentReadDTO getComment(UUID id) {
        Comment comment = getCommentRequired(id);
        return toReadDTO(comment);

    }

    public CommentReadDTO toReadDTO(Comment comment) {
        CommentReadDTO commentReadDTO = new CommentReadDTO();
        commentReadDTO.setId(comment.getId());
        commentReadDTO.setCommentText(comment.getCommentText());
        commentReadDTO.setPostedDate(comment.getPostedDate());
        commentReadDTO.setCommentStatus(comment.getCommentStatus());
        commentReadDTO.setRate(comment.getRate());
        return commentReadDTO;
    }

    public CommentReadDTO createComment(CommentCreateDTO create) {
        Comment comment = new Comment();
        comment.setCommentText(create.getCommentText());
        comment.setCommentStatus(create.getCommentStatus());
        comment.setPostedDate(create.getPostedDate());
        comment.setRate(create.getRate());
        comment = commentRepository.save(comment);
        return toReadDTO(comment);
    }

    public CommentReadDTO patchComment(UUID id, CommentPatchDTO patch) {
        Comment comment = getCommentRequired(id);
        if (patch.getCommentText() != null) {
            comment.setCommentText(patch.getCommentText());
        }
        if (patch.getCommentStatus() != null) {
            comment.setCommentStatus(patch.getCommentStatus());
        }
        if (patch.getPostedDate() != null) {
            comment.setPostedDate(patch.getPostedDate());
        }
        if (patch.getRate() > 0.0) {
            comment.setRate(patch.getRate());
        }
        comment = commentRepository.save(comment);
        return toReadDTO(comment);
    }

    public void deleteComment(UUID id) {
        commentRepository.delete(getCommentRequired(id));
    }
}
