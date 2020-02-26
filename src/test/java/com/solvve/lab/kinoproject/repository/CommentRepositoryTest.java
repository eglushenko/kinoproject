package com.solvve.lab.kinoproject.repository;

import com.solvve.lab.kinoproject.domain.Comment;
import com.solvve.lab.kinoproject.enums.CommentStatus;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.time.LocalDate;

import static org.junit.Assert.assertNotNull;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(statements =
        "delete from comment",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    private Comment createComment() {
        Comment comment = new Comment();
        comment.setCommentText("comment text");
        comment.setPostedDate(LocalDate.of(2020, 1, 22));
        comment.setCommentStatus(CommentStatus.UNCHECKED);
        comment.setRate(0.1);
        return commentRepository.save(comment);
    }

    @Test
    public void testCommentCreateDate() {
        Comment comment = createComment();
        assertNotNull(comment.getCreatedAt());

        Instant createDateBeforeLoad = comment.getCreatedAt();

        comment = commentRepository.findById(comment.getId()).get();

        Instant createDateAfterLoad = comment.getCreatedAt();

        Assertions.assertThat(createDateBeforeLoad).isEqualTo(createDateAfterLoad);
    }

    @Test
    public void testCommentUpdateDate() {
        Comment comment = createComment();
        Assert.assertNotNull(comment.getUpdatedAt());

        Instant updateDateBeforeLoad = comment.getUpdatedAt();

        comment.setCommentStatus(CommentStatus.RETURNED);
        comment = commentRepository.save(comment);
        comment = commentRepository.findById(comment.getId()).get();

        Instant updateDateAfterLoad = comment.getUpdatedAt();

        Assert.assertNotNull(updateDateAfterLoad);
        Assertions.assertThat(updateDateAfterLoad).isAfter(updateDateBeforeLoad);
    }

}