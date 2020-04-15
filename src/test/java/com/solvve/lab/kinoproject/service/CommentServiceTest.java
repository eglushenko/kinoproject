package com.solvve.lab.kinoproject.service;

import com.solvve.lab.kinoproject.BaseTest;
import com.solvve.lab.kinoproject.domain.Comment;
import com.solvve.lab.kinoproject.dto.comment.CommentCreateDTO;
import com.solvve.lab.kinoproject.dto.comment.CommentPatchDTO;
import com.solvve.lab.kinoproject.dto.comment.CommentPutDTO;
import com.solvve.lab.kinoproject.dto.comment.CommentReadDTO;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.repository.CommentRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;


public class CommentServiceTest extends BaseTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentService commentService;

    private Comment createComment() {
        Comment comment = generateFlatEntityWithoutId(Comment.class);
        return commentRepository.save(comment);
    }

    @Test
    public void testGetComment() {
        Comment comment = createComment();
        CommentReadDTO commentReadDTO = commentService.getComment(comment.getId());
        Assertions.assertThat(commentReadDTO)
                .isEqualToIgnoringGivenFields(comment, "createdAt", "updatedAt");
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetCommentWrongId() {
        commentService.getComment(UUID.randomUUID());
    }

    @Test
    public void createCommentTest() {
        CommentCreateDTO create = generateObject(CommentCreateDTO.class);

        CommentReadDTO commentReadDTO = commentService.createComment(create);

        Assertions.assertThat(create)
                .isEqualToIgnoringGivenFields(commentReadDTO, "createdAt", "updatedAt");
        Assert.assertNotNull(commentReadDTO.getId());

        Comment comment = commentRepository.findById(commentReadDTO.getId()).get();
        Assertions.assertThat(commentReadDTO)
                .isEqualToIgnoringGivenFields(comment, "createdAt", "updatedAt");
    }

    @Test
    public void testPatchComment() {
        Comment comment = createComment();

        CommentPatchDTO patch = generateObject(CommentPatchDTO.class);

        CommentReadDTO read = commentService.patchComment(comment.getId(), patch);

        Assertions.assertThat(patch)
                .isEqualToIgnoringGivenFields(read, "createdAt", "updatedAt");

        comment = commentRepository.findById(read.getId()).get();
        Assertions.assertThat(comment)
                .isEqualToIgnoringGivenFields(read, "createdAt", "updatedAt");
    }

    @Test
    public void testPatchCommentEmptyPatch() {
        Comment comment = createComment();

        CommentPatchDTO patch = new CommentPatchDTO();

        CommentReadDTO read = commentService.patchComment(comment.getId(), patch);

        Assert.assertNotNull(read.getCommentStatus());
        Assert.assertNotNull(read.getCommentText());
        Assert.assertNotNull(read.getPostedDate());
        Assert.assertTrue(read.getRate() > 0.0F);

        Comment commentAfterUpdate = commentRepository.findById(read.getId()).get();

        Assert.assertNotNull(commentAfterUpdate.getCommentStatus());
        Assert.assertNotNull(commentAfterUpdate.getCommentText());
        Assert.assertNotNull(commentAfterUpdate.getPostedDate());
        Assert.assertTrue(commentAfterUpdate.getRate() > 0.0F);


        Assertions.assertThat(comment)
                .isEqualToIgnoringGivenFields(commentAfterUpdate, "createdAt", "updatedAt");
    }

    @Test
    public void testPutComment() {
        Comment comment = createComment();

        CommentPutDTO put = generateObject(CommentPutDTO.class);

        CommentReadDTO read = commentService.updateComment(comment.getId(), put);

        Assertions.assertThat(put).isEqualToIgnoringGivenFields(read,
                "createdAt", "updatedAt");

        comment = commentRepository.findById(read.getId()).get();
        Assertions.assertThat(comment)
                .isEqualToIgnoringGivenFields(read, "createdAt", "updatedAt");
    }

    @Test
    public void testDeleteComment() {
        Comment comment = createComment();

        commentService.deleteComment(comment.getId());

        Assert.assertFalse(commentRepository.existsById(comment.getId()));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDeleteCommentNotFoundId() {
        commentService.deleteComment(UUID.randomUUID());
    }


}