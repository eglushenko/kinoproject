package com.solvve.lab.kinoproject.service;

import com.solvve.lab.kinoproject.domain.Like;
import com.solvve.lab.kinoproject.dto.like.LikeCreateDTO;
import com.solvve.lab.kinoproject.dto.like.LikePatchDTO;
import com.solvve.lab.kinoproject.dto.like.LikePutDTO;
import com.solvve.lab.kinoproject.dto.like.LikeReadDTO;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private TranslationService translationService;

    private Like getLikeRequired(UUID id) {
        return likeRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Like.class, id));
    }

    public LikeReadDTO getLike(UUID id) {
        Like like = getLikeRequired(id);
        return translationService.toReadLike(like);

    }


    public LikeReadDTO createLike(LikeCreateDTO create) {
        Like like = translationService.toEntityLike(create);
        like = likeRepository.save(like);
        return translationService.toReadLike(like);
    }

    public LikeReadDTO patchLike(UUID id, LikePatchDTO patch) {
        Like like = getLikeRequired(id);
        translationService.patchEntityLike(patch, like);
        like = likeRepository.save(like);
        return translationService.toReadLike(like);
    }

    public LikeReadDTO updateLike(UUID id, LikePutDTO put) {
        Like like = getLikeRequired(id);
        translationService.updateEntityLike(put, like);
        like = likeRepository.save(like);
        return translationService.toReadLike(like);
    }


    public void deleteLike(UUID id) {
        likeRepository.delete(getLikeRequired(id));
    }

}


