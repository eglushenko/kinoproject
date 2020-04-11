package com.solvve.lab.kinoproject.service;

import com.solvve.lab.kinoproject.domain.*;
import com.solvve.lab.kinoproject.dto.cast.CastCreateDTO;
import com.solvve.lab.kinoproject.dto.cast.CastPatchDTO;
import com.solvve.lab.kinoproject.dto.cast.CastPutDTO;
import com.solvve.lab.kinoproject.dto.cast.CastReadDTO;
import com.solvve.lab.kinoproject.dto.comment.CommentPatchDTO;
import com.solvve.lab.kinoproject.dto.comment.CommentPutDTO;
import com.solvve.lab.kinoproject.dto.customer.CustomerPatchDTO;
import com.solvve.lab.kinoproject.dto.customer.CustomerPutDTO;
import com.solvve.lab.kinoproject.dto.film.FilmPatchDTO;
import com.solvve.lab.kinoproject.dto.film.FilmPutDTO;
import com.solvve.lab.kinoproject.dto.film.FilmReadDTO;
import com.solvve.lab.kinoproject.dto.like.LikePatchDTO;
import com.solvve.lab.kinoproject.dto.like.LikePutDTO;
import com.solvve.lab.kinoproject.dto.name.NamePatchDTO;
import com.solvve.lab.kinoproject.dto.name.NamePutDTO;
import com.solvve.lab.kinoproject.dto.news.NewsPatchDTO;
import com.solvve.lab.kinoproject.dto.news.NewsPutDTO;
import com.solvve.lab.kinoproject.dto.rate.RateCreateDTO;
import com.solvve.lab.kinoproject.dto.rate.RatePatchDTO;
import com.solvve.lab.kinoproject.dto.rate.RatePutDTO;
import com.solvve.lab.kinoproject.dto.rate.RateReadDTO;
import com.solvve.lab.kinoproject.dto.review.ReviewPatchDTO;
import com.solvve.lab.kinoproject.dto.review.ReviewPutDTO;
import com.solvve.lab.kinoproject.dto.scene.SceneCreateDTO;
import com.solvve.lab.kinoproject.dto.scene.ScenePatchDTO;
import com.solvve.lab.kinoproject.dto.scene.ScenePutDTO;
import com.solvve.lab.kinoproject.dto.scene.SceneReadDTO;
import com.solvve.lab.kinoproject.dto.typo.TypoCreateDTO;
import com.solvve.lab.kinoproject.dto.typo.TypoPatchDTO;
import com.solvve.lab.kinoproject.dto.typo.TypoPutDTO;
import com.solvve.lab.kinoproject.dto.typo.TypoReadDTO;
import com.solvve.lab.kinoproject.dto.video.VideoPatchDTO;
import com.solvve.lab.kinoproject.dto.video.VideoPutDTO;
import com.solvve.lab.kinoproject.repository.RepositoryHelper;
import lombok.extern.slf4j.Slf4j;
import org.bitbucket.brunneng.ot.Configuration;
import org.bitbucket.brunneng.ot.ObjectTranslator;
import org.bitbucket.brunneng.ot.exceptions.TranslationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class TranslationService {

    @Autowired
    private RepositoryHelper repositoryHelper;

    private ObjectTranslator objectTranslator;

    public TranslationService() {
        objectTranslator = new ObjectTranslator(createConfiguration());

    }

    public <T> T translate(Object srcObject, Class<T> targetClass) {
        try {
            return objectTranslator.translate(srcObject, targetClass);
        } catch (TranslationException ex) {
            log.warn(ex.getMessage());
            throw (RuntimeException) ex.getCause();
        }

    }

    private Configuration createConfiguration() {
        Configuration c = new Configuration();
        configurationForCast(c);
        configurationForComment(c);
        configurationForFilm(c);
        configurationForNews(c);
        configurationForRate(c);
        configurationForName(c);
        configurationForReview(c);
        configurationForScene(c);
        configurationForVideo(c);
        configurationForTypo(c);
        configurationForCustomer(c);
        configurationForLike(c);
        configurationForAbstractEntity(c);
        return c;
    }

    private void configurationForAbstractEntity(Configuration c) {
        c.beanOfClass(AbstractEntity.class).setIdentifierProperty("id");
        c.beanOfClass(AbstractEntity.class).setBeanFinder((beanClass, id) ->
                repositoryHelper.getReferenceIfExist(beanClass, (UUID) id));
    }

    private void configurationForCast(Configuration c) {
        Configuration.Translation t = c.beanOfClass(Cast.class).translationTo(CastReadDTO.class);
        t.srcProperty("name.id").translatesTo("nameId");
        t.srcProperty("film.id").translatesTo("filmId");

        Configuration.Translation fromCreateToEntity = c.beanOfClass(CastCreateDTO.class)
                .translationTo(Cast.class);
        fromCreateToEntity.srcProperty("nameId").translatesTo("name.id");
        fromCreateToEntity.srcProperty("filmId").translatesTo("film.id");

        c.beanOfClass(CastPatchDTO.class).translationTo(Cast.class).mapOnlyNotNullProperties();
        c.beanOfClass(CastPutDTO.class).translationTo(Cast.class);

    }

    private void configurationForRate(Configuration c) {
        Configuration.Translation t = c.beanOfClass(Rate.class).translationTo(RateReadDTO.class);
        t.srcProperty("customer.id").translatesTo("customerId");

        Configuration.Translation fromCreateToEntity = c.beanOfClass(RateCreateDTO.class)
                .translationTo(Rate.class);
        fromCreateToEntity.srcProperty("customerId").translatesTo("customer.id");

        c.beanOfClass(RatePatchDTO.class).translationTo(Rate.class).mapOnlyNotNullProperties();
        c.beanOfClass(RatePutDTO.class).translationTo(Rate.class);

    }

    private void configurationForScene(Configuration c) {
        Configuration.Translation t = c.beanOfClass(Scene.class).translationTo(SceneReadDTO.class);
        t.srcProperty("film.id").translatesTo("filmId");

        Configuration.Translation fromCreateToEntity = c.beanOfClass(SceneCreateDTO.class)
                .translationTo(Scene.class);
        fromCreateToEntity.srcProperty("filmId").translatesTo("film.id");

        c.beanOfClass(ScenePatchDTO.class).translationTo(Scene.class).mapOnlyNotNullProperties();
        c.beanOfClass(ScenePutDTO.class).translationTo(Scene.class);

    }

    private void configurationForTypo(Configuration c) {
        Configuration.Translation t = c.beanOfClass(Typo.class).translationTo(TypoReadDTO.class);
        t.srcProperty("customer.id").translatesTo("customerId");

        Configuration.Translation fromCreateToEntity = c.beanOfClass(TypoCreateDTO.class)
                .translationTo(Typo.class);
        fromCreateToEntity.srcProperty("customerId").translatesTo("customer.id");

        c.beanOfClass(TypoPatchDTO.class).translationTo(Typo.class).mapOnlyNotNullProperties();
        c.beanOfClass(TypoPutDTO.class).translationTo(Typo.class);

    }

    private void configurationForCustomer(Configuration c) {
        c.beanOfClass(CustomerPatchDTO.class).translationTo(Customer.class).mapOnlyNotNullProperties();
        c.beanOfClass(CustomerPutDTO.class).translationTo(Customer.class);
    }

    private void configurationForVideo(Configuration c) {
        c.beanOfClass(VideoPatchDTO.class).translationTo(Video.class).mapOnlyNotNullProperties();
        c.beanOfClass(VideoPutDTO.class).translationTo(Video.class);
    }

    private void configurationForFilm(Configuration c) {
        c.beanOfClass(FilmPatchDTO.class).translationTo(Film.class).mapOnlyNotNullProperties();
        c.beanOfClass(FilmPutDTO.class).translationTo(Film.class);
    }

    private void configurationForNews(Configuration c) {
        c.beanOfClass(NewsPatchDTO.class).translationTo(News.class).mapOnlyNotNullProperties();
        c.beanOfClass(NewsPutDTO.class).translationTo(News.class);
    }

    private void configurationForLike(Configuration c) {
        c.beanOfClass(LikePatchDTO.class).translationTo(Like.class).mapOnlyNotNullProperties();
        c.beanOfClass(LikePutDTO.class).translationTo(Like.class);
    }

    private void configurationForComment(Configuration c) {
        c.beanOfClass(CommentPatchDTO.class).translationTo(Comment.class).mapOnlyNotNullProperties();
        c.beanOfClass(CommentPutDTO.class).translationTo(Comment.class);
    }

    private void configurationForName(Configuration c) {
        c.beanOfClass(NamePatchDTO.class).translationTo(Name.class).mapOnlyNotNullProperties();
        c.beanOfClass(NamePutDTO.class).translationTo(Name.class);
    }

    private void configurationForReview(Configuration c) {
        c.beanOfClass(ReviewPatchDTO.class).translationTo(Review.class).mapOnlyNotNullProperties();
        c.beanOfClass(ReviewPutDTO.class).translationTo(Review.class);
    }

    //Customer
    public void patchEntityCustomer(CustomerPatchDTO patch, Customer customer) {
        objectTranslator.mapBean(patch, customer);
    }

    public void updateEntityCustomer(CustomerPutDTO put, Customer customer) {
        objectTranslator.mapBean(put, customer);
    }

    //Cast
    public void patchEntityCast(CastPatchDTO patch, Cast cast) {
        if (patch.getNameRoleInFilm() != null) {
            cast.setNameRoleInFilm(patch.getNameRoleInFilm());
        }
        if (patch.getRoleInFilm() != null) {
            cast.setRoleInFilm(patch.getRoleInFilm());
        }
        if (patch.getNameId() != null) {
            cast.setName(repositoryHelper.getReferenceIfExist(Name.class, patch.getNameId()));
        }
        if (patch.getFilmId() != null) {
            cast.setFilm(repositoryHelper.getReferenceIfExist(Film.class, patch.getFilmId()));
        }
    }

    public void updateEntityCast(CastPutDTO put, Cast cast) {
        cast.setRoleInFilm(put.getRoleInFilm());
        cast.setNameRoleInFilm(put.getNameRoleInFilm());
        cast.setName(repositoryHelper.getReferenceIfExist(Name.class, put.getNameId()));
        cast.setFilm(repositoryHelper.getReferenceIfExist(Film.class, put.getFilmId()));

    }


    //Comment
    public void patchEntityComment(CommentPatchDTO patch, Comment comment) {
        objectTranslator.mapBean(patch, comment);
    }

    public void updateEntityComment(CommentPutDTO put, Comment comment) {
        comment.setCommentText(put.getCommentText());
        comment.setCommentStatus(put.getCommentStatus());
        comment.setPostedDate(put.getPostedDate());
        comment.setRate(put.getRate());
    }

    //Name
    public void patchEntityName(NamePatchDTO patch, Name name) {
        objectTranslator.mapBean(patch, name);
    }

    public void updateEntityName(NamePutDTO put, Name name) {
        objectTranslator.mapBean(put, name);
    }

    //Film
    public FilmReadDTO toReadFilm(Film film) {
        return objectTranslator.translate(film, FilmReadDTO.class);
    }

    public void patchEntityFilm(FilmPatchDTO patch, Film film) {
        objectTranslator.mapBean(patch, film);
    }

    public void updateEntityFilm(FilmPutDTO put, Film film) {
        objectTranslator.mapBean(put, film);
    }


    //Typo
    public void patchEntityTypo(TypoPatchDTO patch, Typo typo) {
        if (patch.getTypoMessage() != null) {
            typo.setTypoMessage(patch.getTypoMessage());
        }
        if (patch.getErrorText() != null) {
            typo.setErrorText(patch.getErrorText());
        }
        if (patch.getRightText() != null) {
            typo.setRightText(patch.getRightText());
        }
        if (patch.getTypoLink() != null) {
            typo.setTypoLink(patch.getTypoLink());
        }
        if (patch.getStatus() != null) {
            typo.setStatus(patch.getStatus());
        }
        if (patch.getCustomerId() != null) {
            typo.setCustomer(repositoryHelper.getReferenceIfExist(Customer.class, patch.getCustomerId()));
        }

    }

    public void updateEntityTypo(TypoPutDTO put, Typo typo) {
        typo.setTypoMessage(put.getTypoMessage());
        typo.setErrorText(put.getErrorText());
        typo.setRightText(put.getRightText());
        typo.setTypoLink(put.getTypoLink());
        typo.setStatus(put.getStatus());
        typo.setCustomer(repositoryHelper.getReferenceIfExist(Customer.class, put.getCustomerId()));
    }

    //Review
    public void patchEntityReview(ReviewPatchDTO patch, Review review) {
        objectTranslator.mapBean(patch, review);
    }

    public void updateEntityReview(ReviewPutDTO put, Review review) {
        objectTranslator.mapBean(put, review);
    }

    //Like
    public void patchEntityLike(LikePatchDTO patch, Like like) {
        objectTranslator.mapBean(patch, like);
    }

    public void updateEntityLike(LikePutDTO put, Like like) {
        objectTranslator.mapBean(put, like);
    }

    //News
    public void patchEntityNews(NewsPatchDTO patch, News news) {
        objectTranslator.mapBean(patch, news);
    }

    public void updateEntityNews(NewsPutDTO put, News news) {
        objectTranslator.mapBean(put, news);
    }

    //Scene
    @Transactional
    public Scene toEntityScene(SceneCreateDTO create) {
        Scene scene = translate(create, Scene.class);
        scene.setSceneLink(create.getSceneLink());
        scene.setFilm(repositoryHelper.getReferenceIfExist(Film.class, create.getFilmId()));
        return scene;
    }

    public void patchEntityScene(ScenePatchDTO patch, Scene scene) {
        if (patch.getSceneLink() != null) {
            scene.setSceneLink(patch.getSceneLink());
        }
        if (patch.getFilmId() != null) {
            scene.setFilm(repositoryHelper.getReferenceIfExist(Film.class, patch.getFilmId()));
        }
    }

    public void updateEntityScene(ScenePutDTO put, Scene scene) {
        scene.setSceneLink(put.getSceneLink());
        scene.setFilm(repositoryHelper.getReferenceIfExist(Film.class, put.getFilmId()));
    }

    //Video
    public void patchEntityVideo(VideoPatchDTO patch, Video video) {
        objectTranslator.mapBean(patch, video);
    }

    public void updateEntityVideo(VideoPutDTO put, Video video) {
        objectTranslator.mapBean(put, video);
    }

    //Rate
    public void patchEntityRate(RatePatchDTO patch, Rate rate) {
        if (patch.getCustomerId() != null) {
            rate.setCustomer(repositoryHelper.getReferenceIfExist(Customer.class, patch.getCustomerId()));
        }
        if (patch.getRate() != null) {
            rate.setRate(patch.getRate());
        }
        if (patch.getRatedObjectId() != null) {
            rate.setRatedObjectId(patch.getRatedObjectId());
        }
        if (patch.getType() != null) {
            rate.setType(patch.getType());
        }
    }

    public void updateEntityRate(RatePutDTO put, Rate rate) {
        rate.setCustomer(repositoryHelper.getReferenceIfExist(Customer.class, put.getCustomerId()));
        rate.setRate(put.getRate());
        rate.setRatedObjectId(put.getRatedObjectId());
        rate.setType(put.getType());
    }

}
