package com.solvve.lab.kinoproject.service;

import com.solvve.lab.kinoproject.domain.Like;
import com.solvve.lab.kinoproject.dto.like.LikeCreateDTO;
import com.solvve.lab.kinoproject.dto.like.LikePatchDTO;
import com.solvve.lab.kinoproject.dto.like.LikePutDTO;
import com.solvve.lab.kinoproject.dto.like.LikeReadDTO;
import com.solvve.lab.kinoproject.enums.LikedObjectType;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.repository.LikeRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Sql(statements = "delete from like", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class LikeServiceTest {

    @Autowired
    private LikeService likeService;

    @Autowired
    private LikeRepository likeRepository;

    private Like createLike() {
        Like like = new Like();
        like.setLikedObjectId(UUID.randomUUID());
        like.setLike(true);
        like.setType(LikedObjectType.COMMENT);
        return likeRepository.save(like);
    }

    @Test
    public void testGetLike() {
        Like like = createLike();

        LikeReadDTO read = likeService.getLike(like.getId());
        Assertions.assertThat(read)
                .isEqualToIgnoringGivenFields(like, "createdAt", "updatedAt");

    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetLikeWrongId() {
        likeService.getLike(UUID.randomUUID());

    }

    @Test
    public void testCreateLike() {
        LikeCreateDTO create = new LikeCreateDTO();
        create.setLikedObjectId(UUID.randomUUID());
        create.setLike(false);
        create.setType(LikedObjectType.COMMENT);

        LikeReadDTO read = likeService.createLike(create);
        Assertions.assertThat(create).isEqualToComparingFieldByField(read);
        Assert.assertNotNull(read.getId());

        Like like = likeRepository.findById(read.getId()).get();
        Assertions.assertThat(read)
                .isEqualToIgnoringGivenFields(like, "createdAt", "updatedAt");
    }

    @Test
    public void testPatchLike() {
        Like like = createLike();

        LikePatchDTO patch = new LikePatchDTO();
        patch.setLikedObjectId(UUID.randomUUID());
        patch.setLike(true);
        patch.setType(LikedObjectType.COMMENT);
        LikeReadDTO read = likeService.patchLike(like.getId(), patch);

        Assertions.assertThat(patch)
                .isEqualToIgnoringGivenFields(read, "createdAt", "updatedAt");

        like = likeRepository.findById(read.getId()).get();
        Assertions.assertThat(like)
                .isEqualToIgnoringGivenFields(read, "createdAt", "updatedAt");
    }

    @Test
    public void testPutLike() {
        Like like = createLike();

        LikePutDTO put = new LikePutDTO();
        put.setLike(true);
        put.setLikedObjectId(UUID.randomUUID());
        put.setType(LikedObjectType.COMMENT);
        LikeReadDTO read = likeService.updateLike(like.getId(), put);

        Assertions.assertThat(put)
                .isEqualToIgnoringGivenFields(read, "createdAt", "updatedAt");

        like = likeRepository.findById(read.getId()).get();
        Assertions.assertThat(like)
                .isEqualToIgnoringGivenFields(read, "createdAt", "updatedAt");
    }

    @Test
    public void testPatchLikeEmptyPatch() {
        Like like = createLike();

        LikePatchDTO patch = new LikePatchDTO();

        LikeReadDTO read = likeService.patchLike(like.getId(), patch);

        Assert.assertNotNull(read.getLikedObjectId());
        Assert.assertNotNull(read.getLike());
        Assert.assertNotNull(read.getType());

        Like likeAfterUpdate = likeRepository.findById(read.getId()).get();

        Assert.assertNotNull(likeAfterUpdate.getLikedObjectId());
        Assert.assertNotNull(likeAfterUpdate.getLike());
        Assert.assertNotNull(likeAfterUpdate.getType());

        Assertions.assertThat(like)
                .isEqualToIgnoringGivenFields(likeAfterUpdate,
                        "createdAt", "updatedAt");
    }

    @Test
    public void testDeleteLike() {
        Like like = createLike();

        likeService.deleteLike(like.getId());

        Assert.assertFalse(likeRepository.existsById(like.getId()));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDeleteLikeNotFoundId() {
        likeService.deleteLike(UUID.randomUUID());
    }

}