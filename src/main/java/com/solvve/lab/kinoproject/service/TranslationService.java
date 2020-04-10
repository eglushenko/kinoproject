package com.solvve.lab.kinoproject.service;

import com.solvve.lab.kinoproject.domain.*;
import com.solvve.lab.kinoproject.dto.FilmReadExtendedDTO;
import com.solvve.lab.kinoproject.dto.cast.CastCreateDTO;
import com.solvve.lab.kinoproject.dto.cast.CastPatchDTO;
import com.solvve.lab.kinoproject.dto.cast.CastPutDTO;
import com.solvve.lab.kinoproject.dto.cast.CastReadDTO;
import com.solvve.lab.kinoproject.dto.comment.CommentCreateDTO;
import com.solvve.lab.kinoproject.dto.comment.CommentPatchDTO;
import com.solvve.lab.kinoproject.dto.comment.CommentPutDTO;
import com.solvve.lab.kinoproject.dto.comment.CommentReadDTO;
import com.solvve.lab.kinoproject.dto.customer.CustomerCreateDTO;
import com.solvve.lab.kinoproject.dto.customer.CustomerPatchDTO;
import com.solvve.lab.kinoproject.dto.customer.CustomerPutDTO;
import com.solvve.lab.kinoproject.dto.customer.CustomerReadDTO;
import com.solvve.lab.kinoproject.dto.film.FilmCreateDTO;
import com.solvve.lab.kinoproject.dto.film.FilmPatchDTO;
import com.solvve.lab.kinoproject.dto.film.FilmPutDTO;
import com.solvve.lab.kinoproject.dto.film.FilmReadDTO;
import com.solvve.lab.kinoproject.dto.like.LikeCreateDTO;
import com.solvve.lab.kinoproject.dto.like.LikePatchDTO;
import com.solvve.lab.kinoproject.dto.like.LikePutDTO;
import com.solvve.lab.kinoproject.dto.like.LikeReadDTO;
import com.solvve.lab.kinoproject.dto.name.NameCreateDTO;
import com.solvve.lab.kinoproject.dto.name.NamePatchDTO;
import com.solvve.lab.kinoproject.dto.name.NamePutDTO;
import com.solvve.lab.kinoproject.dto.name.NameReadDTO;
import com.solvve.lab.kinoproject.dto.news.NewsCreateDTO;
import com.solvve.lab.kinoproject.dto.news.NewsPatchDTO;
import com.solvve.lab.kinoproject.dto.news.NewsPutDTO;
import com.solvve.lab.kinoproject.dto.news.NewsReadDTO;
import com.solvve.lab.kinoproject.dto.rate.RateCreateDTO;
import com.solvve.lab.kinoproject.dto.rate.RatePatchDTO;
import com.solvve.lab.kinoproject.dto.rate.RatePutDTO;
import com.solvve.lab.kinoproject.dto.rate.RateReadDTO;
import com.solvve.lab.kinoproject.dto.review.ReviewCreateDTO;
import com.solvve.lab.kinoproject.dto.review.ReviewPatchDTO;
import com.solvve.lab.kinoproject.dto.review.ReviewPutDTO;
import com.solvve.lab.kinoproject.dto.review.ReviewReadDTO;
import com.solvve.lab.kinoproject.dto.scene.SceneCreateDTO;
import com.solvve.lab.kinoproject.dto.scene.ScenePatchDTO;
import com.solvve.lab.kinoproject.dto.scene.ScenePutDTO;
import com.solvve.lab.kinoproject.dto.scene.SceneReadDTO;
import com.solvve.lab.kinoproject.dto.typo.TypoCreateDTO;
import com.solvve.lab.kinoproject.dto.typo.TypoPatchDTO;
import com.solvve.lab.kinoproject.dto.typo.TypoPutDTO;
import com.solvve.lab.kinoproject.dto.typo.TypoReadDTO;
import com.solvve.lab.kinoproject.dto.video.VideoCreateDTO;
import com.solvve.lab.kinoproject.dto.video.VideoPatchDTO;
import com.solvve.lab.kinoproject.dto.video.VideoPutDTO;
import com.solvve.lab.kinoproject.dto.video.VideoReadDTO;
import com.solvve.lab.kinoproject.repository.RepositoryHelper;
import org.bitbucket.brunneng.ot.Configuration;
import org.bitbucket.brunneng.ot.ObjectTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TranslationService {

    @Autowired
    private RepositoryHelper repositoryHelper;

    private ObjectTranslator objectTranslator;

    {
        new ObjectTranslator();
    }

    public TranslationService() {
        objectTranslator = new ObjectTranslator(createConfiguration());

    }

    private Configuration createConfiguration() {
        Configuration c = new Configuration();
        configurationForCast(c);
        configurationForRate(c);
        configurationForScene(c);
        configurationForTypo(c);
        return c;
    }

    private void configurationForCast(Configuration c) {
        Configuration.Translation t = c.beanOfClass(Cast.class).translationTo(CastReadDTO.class);
        t.srcProperty("name.id").translatesTo("nameId");
        t.srcProperty("film.id").translatesTo("filmId");

    }

    private void configurationForRate(Configuration c) {
        Configuration.Translation t = c.beanOfClass(Rate.class).translationTo(RateReadDTO.class);
        t.srcProperty("customer.id").translatesTo("customerId");

    }

    private void configurationForScene(Configuration c) {
        Configuration.Translation t = c.beanOfClass(Scene.class).translationTo(SceneReadDTO.class);
        t.srcProperty("film.id").translatesTo("filmId");

    }

    private void configurationForTypo(Configuration c) {
        Configuration.Translation t = c.beanOfClass(Typo.class).translationTo(TypoReadDTO.class);
        t.srcProperty("customer.id").translatesTo("customerId");

    }


    //Customer
    public CustomerReadDTO toReadCustomer(Customer customer) {
        return objectTranslator.translate(customer, CustomerReadDTO.class);
    }

    public Customer toEntityCustomer(CustomerCreateDTO create) {
        Customer customer = new Customer();
        customer.setLogin(create.getLogin());
        customer.setFirstName(create.getFirstName());
        customer.setLastName(create.getLastName());
        customer.setEmail(create.getEmail());
        customer.setRole(create.getRole());
        customer.setGender(create.getGender());
        return customer;
    }

    public void patchEntityCustomer(CustomerPatchDTO patch, Customer customer) {
        if (patch.getFirstName() != null) {
            customer.setFirstName(patch.getFirstName());
        }
        if (patch.getLastName() != null) {
            customer.setLastName(patch.getLastName());
        }
        if (patch.getLogin() != null) {
            customer.setLogin(patch.getLogin());
        }
        if (patch.getEmail() != null) {
            customer.setEmail(patch.getEmail());
        }
        if (patch.getRole() != null) {
            customer.setRole(patch.getRole());
        }
        if (patch.getGender() != null) {
            customer.setGender(patch.getGender());
        }
    }

    public void updateEntityCustomer(CustomerPutDTO put, Customer customer) {
        customer.setLogin(put.getLogin());
        customer.setFirstName(put.getFirstName());
        customer.setLastName(put.getLastName());
        customer.setEmail(put.getEmail());
        customer.setRole(put.getRole());
        customer.setGender(put.getGender());
    }

    //Cast
    public Cast toEntityCast(CastCreateDTO create) {
        Cast cast = new Cast();
        cast.setNameRoleInFilm(create.getNameRoleInFilm());
        cast.setRoleInFilm(create.getRoleInFilm());
        cast.setName(repositoryHelper.getReferenceIfExist(Name.class, create.getNameId())); //TODO
        cast.setFilm(repositoryHelper.getReferenceIfExist(Film.class, create.getFilmId()));
        return cast;
    }

    public CastReadDTO toReadCast(Cast cast) {
        return objectTranslator.translate(cast, CastReadDTO.class);
    }

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
    public Comment toEntityComment(CommentCreateDTO create) {
        Comment comment = new Comment();
        comment.setCommentText(create.getCommentText());
        comment.setCommentStatus(create.getCommentStatus());
        comment.setPostedDate(create.getPostedDate());
        comment.setRate(create.getRate());
        return comment;
    }

    public CommentReadDTO toReadComment(Comment comment) {
        CommentReadDTO commentReadDTO = new CommentReadDTO();
        commentReadDTO.setId(comment.getId());
        commentReadDTO.setCommentText(comment.getCommentText());
        commentReadDTO.setPostedDate(comment.getPostedDate());
        commentReadDTO.setCommentStatus(comment.getCommentStatus());
        commentReadDTO.setRate(comment.getRate());
        return commentReadDTO;
    }

    public void patchEntityComment(CommentPatchDTO patch, Comment comment) {
        if (patch.getCommentText() != null) {
            comment.setCommentText(patch.getCommentText());
        }
        if (patch.getCommentStatus() != null) {
            comment.setCommentStatus(patch.getCommentStatus());
        }
        if (patch.getPostedDate() != null) {
            comment.setPostedDate(patch.getPostedDate());
        }
        if (patch.getRate() != null) {
            comment.setRate(patch.getRate());
        }
    }

    public void updateEntityComment(CommentPutDTO put, Comment comment) {
        comment.setCommentText(put.getCommentText());
        comment.setCommentStatus(put.getCommentStatus());
        comment.setPostedDate(put.getPostedDate());
        comment.setRate(put.getRate());
    }

    //Name
    public Name toEntityName(NameCreateDTO create) {
        Name name = new Name();
        name.setFirstName(create.getFirstName());
        name.setLastName(create.getLastName());
        return name;
    }

    public NameReadDTO toReadName(Name name) {
        return objectTranslator.translate(name, NameReadDTO.class);
    }

    public void patchEntityName(NamePatchDTO patch, Name name) {
        if (patch.getFirstName() != null) {
            name.setFirstName(patch.getFirstName());
        }
        if (patch.getLastName() != null) {
            name.setLastName(patch.getLastName());
        }
    }

    public void updateEntityName(NamePutDTO put, Name name) {
        name.setFirstName(put.getFirstName());
        name.setLastName(put.getLastName());
    }

    //Film
    public Film toEntityFilm(FilmCreateDTO create) {
        Film film = new Film();
        film.setCategory(create.getCategory());
        film.setCountry(create.getCountry());
        film.setFilmText(create.getFilmText());
        film.setLang(create.getLang());
        film.setLength(create.getLength());
        film.setAverageRate(create.getAverageRate());
        film.setTitle(create.getTitle());
        film.setLastUpdate(create.getLastUpdate());
        film.setMpaa(create.getMpaa());
        return film;
    }

    public FilmReadDTO toReadFilm(Film film) {
        return objectTranslator.translate(film, FilmReadDTO.class);
    }

    public void patchEntityFilm(FilmPatchDTO patch, Film film) {
        if (patch.getTitle() != null) {
            film.setTitle(patch.getTitle());
        }
        if (patch.getFilmText() != null) {
            film.setFilmText(patch.getFilmText());
        }
        if (patch.getCategory() != null) {
            film.setCategory(patch.getCategory());
        }
        if (patch.getCountry() != null) {
            film.setCountry(patch.getCountry());
        }
        if (patch.getLang() != null) {
            film.setLang(patch.getLang());
        }
        if (patch.getLength() != null) {
            film.setLength(patch.getLength());
        }
        if (patch.getLastUpdate() != null) {
            film.setLastUpdate(patch.getLastUpdate());
        }
        if (patch.getAverageRate() != null) {
            film.setAverageRate(patch.getAverageRate());
        }
        if (patch.getMpaa() != null) {
            film.setMpaa(patch.getMpaa());
        }
    }

    public void updateEntityFilm(FilmPutDTO put, Film film) {
        film.setTitle(put.getTitle());
        film.setFilmText(put.getFilmText());
        film.setCategory(put.getCategory());
        film.setCountry(put.getCountry());
        film.setLang(put.getLang());
        film.setLength(put.getLength());
        film.setLastUpdate(put.getLastUpdate());
        film.setAverageRate(put.getAverageRate());
        film.setMpaa(put.getMpaa());
    }

    public FilmReadExtendedDTO toReadExtendedFilm(Film film) {
        FilmReadExtendedDTO filmReadExtendedDTO = new FilmReadExtendedDTO();
        filmReadExtendedDTO.setId(film.getId());
        filmReadExtendedDTO.setTitle(film.getTitle());
        filmReadExtendedDTO.setCategory(film.getCategory());
        filmReadExtendedDTO.setCountry(film.getCountry());
        filmReadExtendedDTO.setLang(film.getLang());
        filmReadExtendedDTO.setAverageRate(film.getAverageRate());
        filmReadExtendedDTO.setLength(film.getLength());
        filmReadExtendedDTO.setFilmText(film.getFilmText());
        filmReadExtendedDTO.setLastUpdate(film.getLastUpdate());
        filmReadExtendedDTO.setMpaa(film.getMpaa());
        return filmReadExtendedDTO;
    }

    //Typo
    public TypoReadDTO toReadTypo(Typo typo) {
        return objectTranslator.translate(typo, TypoReadDTO.class);
    }

    public Typo toEntityTypo(TypoCreateDTO create) {
        Typo typo = new Typo();
        typo.setTypoMessage(create.getTypoMessage());
        typo.setErrorText(create.getErrorText());
        typo.setRightText(create.getRightText());
        typo.setTypoLink(create.getTypoLink());
        typo.setStatus(create.getStatus());
        typo.setCustomer(repositoryHelper.getReferenceIfExist(Customer.class, create.getCustomerId()));
        return typo;
    }

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
    public ReviewReadDTO toReadReview(Review review) {
        return objectTranslator.translate(review, ReviewReadDTO.class);
    }

    public Review toEntityReview(ReviewCreateDTO create) {
        Review review = new Review();
        review.setReviewText(create.getReviewText());
        return review;
    }

    public void patchEntityReview(ReviewPatchDTO patch, Review review) {
        if (patch.getReviewText() != null) {
            review.setReviewText(patch.getReviewText());
        }
    }

    public void updateEntityReview(ReviewPutDTO put, Review review) {
        review.setReviewText(put.getReviewText());
    }

    //Like
    public LikeReadDTO toReadLike(Like like) {
        return objectTranslator.translate(like, LikeReadDTO.class);
    }

    public Like toEntityLike(LikeCreateDTO create) {
        Like like = new Like();
        like.setLike(create.getLike());
        like.setType(create.getType());
        like.setLikedObjectId(create.getLikedObjectId());
        return like;
    }

    public void patchEntityLike(LikePatchDTO patch, Like like) {
        if (patch.getLike() != null) {
            like.setLike(patch.getLike());
        }
        if (patch.getLikedObjectId() != null) {
            like.setLikedObjectId(patch.getLikedObjectId());
        }
        if (patch.getType() != null) {
            like.setType(patch.getType());
        }
    }

    public void updateEntityLike(LikePutDTO put, Like like) {
        like.setLike(put.getLike());
        like.setType(put.getType());
        like.setLikedObjectId(put.getLikedObjectId());
    }

    //News
    public NewsReadDTO toReadNews(News news) {
        return objectTranslator.translate(news, NewsReadDTO.class);
    }

    public News toEntityNews(NewsCreateDTO create) {
        News news = new News();
        news.setTextNews(create.getTextNews());
        return news;
    }

    public void patchEntityNews(NewsPatchDTO patch, News news) {
        if (patch.getTextNews() != null) {
            news.setTextNews(patch.getTextNews());
        }
    }

    public void updateEntityNews(NewsPutDTO put, News news) {
        news.setTextNews(put.getTextNews());
    }

    //Scene
    public SceneReadDTO toReadScene(Scene scene) {
        return objectTranslator.translate(scene, SceneReadDTO.class);
    }

    public Scene toEntityScene(SceneCreateDTO create) {
        Scene scene = new Scene();
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
    public VideoReadDTO toReadVideo(Video video) {
        return objectTranslator.translate(video, VideoReadDTO.class);
    }

    public Video toEntityVideo(VideoCreateDTO create) {
        Video video = new Video();
        video.setVideoLink(create.getVideoLink());
        return video;
    }

    public void patchEntityVideo(VideoPatchDTO patch, Video video) {
        if (patch.getVideoLink() != null) {
            video.setVideoLink(patch.getVideoLink());
        }
    }

    public void updateEntityVideo(VideoPutDTO put, Video video) {
        video.setVideoLink(put.getVideoLink());
    }

    //Rate
    public RateReadDTO toReadRate(Rate rate) {

        return objectTranslator.translate(rate, RateReadDTO.class);
    }

    public Rate toEntityRate(RateCreateDTO create) {
        Rate rate = new Rate();
        rate.setCustomer(repositoryHelper.getReferenceIfExist(Customer.class, create.getCustomerId()));
        rate.setRate(create.getRate());
        rate.setRatedObjectId(create.getRatedObjectId());
        rate.setType(create.getType());
        return rate;
    }

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
