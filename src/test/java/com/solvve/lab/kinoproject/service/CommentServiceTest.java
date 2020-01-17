package com.solvve.lab.kinoproject.service;

import com.solvve.lab.kinoproject.domain.Comment;
import com.solvve.lab.kinoproject.dto.CommentReadDTO;
import com.solvve.lab.kinoproject.enums.CommentStatus;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.repository.CommentRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Sql(statements = "delete from comment", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CommentServiceTest {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    private CommentService commentService;

    private Comment createComment() {
        Comment comment = new Comment();
        comment.setCommentText("comment text");
        comment.setPostedDate(LocalDate.of(2020, 1, 22));
        comment.setCommentStatus(CommentStatus.UNCHECKED);
        comment.setRate(0.1F);
        return commentRepository.save(comment);
    }

    @Test
    public void testGetComment() {
        Comment comment = createComment();
        CommentReadDTO commentReadDTO = commentService.getComment(comment.getId());
        Assertions.assertThat(commentReadDTO).isEqualToComparingFieldByField(comment);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetCommentWrongId() {
        commentService.getComment(UUID.randomUUID());
    }

}