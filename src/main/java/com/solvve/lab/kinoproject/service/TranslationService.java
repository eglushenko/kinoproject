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
import com.solvve.lab.kinoproject.dto.name.NameCreateDTO;
import com.solvve.lab.kinoproject.dto.name.NamePatchDTO;
import com.solvve.lab.kinoproject.dto.name.NamePutDTO;
import com.solvve.lab.kinoproject.dto.name.NameReadDTO;
import com.solvve.lab.kinoproject.dto.review.ReviewCreateDTO;
import com.solvve.lab.kinoproject.dto.review.ReviewPatchDTO;
import com.solvve.lab.kinoproject.dto.review.ReviewPutDTO;
import com.solvve.lab.kinoproject.dto.review.ReviewReadDTO;
import com.solvve.lab.kinoproject.dto.typo.TypoCreateDTO;
import com.solvve.lab.kinoproject.dto.typo.TypoPatchDTO;
import com.solvve.lab.kinoproject.dto.typo.TypoPutDTO;
import com.solvve.lab.kinoproject.dto.typo.TypoReadDTO;
import com.solvve.lab.kinoproject.repository.RepositoryHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TranslationService {

    @Autowired
    private RepositoryHelper repositoryHelper;

    //Customer
    public CustomerReadDTO toReadCustomer(Customer customer) {
        CustomerReadDTO customerReadDTO = new CustomerReadDTO();
        customerReadDTO.setId(customer.getId());
        customerReadDTO.setLogin(customer.getLogin());
        customerReadDTO.setFirstName(customer.getFirstName());
        customerReadDTO.setLastName(customer.getLastName());
        customerReadDTO.setEmail(customer.getEmail());
        customerReadDTO.setRole(customer.getRole());
        return customerReadDTO;
    }

    public Customer toEntityCustomer(CustomerCreateDTO create) {
        Customer customer = new Customer();
        customer.setLogin(create.getLogin());
        customer.setFirstName(create.getFirstName());
        customer.setLastName(create.getLastName());
        customer.setEmail(create.getEmail());
        customer.setRole(create.getRole());
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
    }

    public void updateEntityCustomer(CustomerPutDTO put, Customer customer) {
        customer.setLogin(put.getLogin());
        customer.setFirstName(put.getFirstName());
        customer.setLastName(put.getLastName());
        customer.setEmail(put.getEmail());
        customer.setRole(put.getRole());
    }

    //Cast
    public Cast toEntityCast(CastCreateDTO create) {
        Cast cast = new Cast();
        cast.setNameRoleInFilm(create.getNameRoleInFilm());
        cast.setRoleInFilm(create.getRoleInFilm());
        cast.setName(repositoryHelper.getReferenceIfExist(Name.class, create.getNameId())); //TODO
        return cast;
    }

    public CastReadDTO toReadCast(Cast cast) {
        CastReadDTO castReadDTO = new CastReadDTO();
        castReadDTO.setId(cast.getId());
        castReadDTO.setNameRoleInFilm(cast.getNameRoleInFilm());
        castReadDTO.setRoleInFilm(cast.getRoleInFilm());
        castReadDTO.setNameId(cast.getName().getId());
        return castReadDTO;
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
    }

    public void updateEntityCast(CastPutDTO put, Cast cast) {
        cast.setRoleInFilm(put.getRoleInFilm());
        cast.setNameRoleInFilm(put.getNameRoleInFilm());
        cast.setName(repositoryHelper.getReferenceIfExist(Name.class, put.getNameId()));
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
        NameReadDTO nameReadDTO = new NameReadDTO();
        nameReadDTO.setId(name.getId());
        nameReadDTO.setFirstName(name.getFirstName());
        nameReadDTO.setLastName(name.getLastName());
        return nameReadDTO;
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
        film.setRate(create.getRate());
        film.setTitle(create.getTitle());
        film.setLastUpdate(create.getLastUpdate());
        return film;
    }

    public FilmReadDTO toReadFilm(Film film) {
        FilmReadDTO filmReadDTO = new FilmReadDTO();
        filmReadDTO.setId(film.getId());
        filmReadDTO.setTitle(film.getTitle());
        filmReadDTO.setCategory(film.getCategory());
        filmReadDTO.setCountry(film.getCountry());
        filmReadDTO.setLang(film.getLang());
        filmReadDTO.setRate(film.getRate());
        filmReadDTO.setLength(film.getLength());
        filmReadDTO.setFilmText(film.getFilmText());
        filmReadDTO.setLastUpdate(film.getLastUpdate());
        return filmReadDTO;
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
        if (patch.getRate() != null) {
            film.setRate(patch.getRate());
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
        film.setRate(put.getRate());
    }

    public FilmReadExtendedDTO toReadExtendedFilm(Film film) {
        FilmReadExtendedDTO filmReadExtendedDTO = new FilmReadExtendedDTO();
        filmReadExtendedDTO.setId(film.getId());
        filmReadExtendedDTO.setTitle(film.getTitle());
        filmReadExtendedDTO.setCategory(film.getCategory());
        filmReadExtendedDTO.setCountry(film.getCountry());
        filmReadExtendedDTO.setLang(film.getLang());
        filmReadExtendedDTO.setRate(film.getRate());
        filmReadExtendedDTO.setLength(film.getLength());
        filmReadExtendedDTO.setFilmText(film.getFilmText());
        filmReadExtendedDTO.setLastUpdate(film.getLastUpdate());
        return filmReadExtendedDTO;
    }

//Typo

    public TypoReadDTO toReadTypo(Typo typo) {
        TypoReadDTO read = new TypoReadDTO();
        read.setId(typo.getId());
        read.setTypoMessege(typo.getTypoMessege());
        read.setStatus(typo.getStatus());
        read.setTypoLink(typo.getTypoLink());
        return read;
    }

    public Typo toEntityTypo(TypoCreateDTO create) {
        Typo typo = new Typo();
        typo.setTypoMessege(create.getTypoMessege());
        typo.setTypoLink(create.getTypoLink());
        typo.setStatus(create.getStatus());
        return typo;
    }

    public void patchEntityTypo(TypoPatchDTO patch, Typo typo) {
        if (patch.getTypoMessege() != null) {
            typo.setTypoMessege(patch.getTypoMessege());
        }
        if (patch.getTypoLink() != null) {
            typo.setTypoLink(patch.getTypoLink());
        }
        if (patch.getStatus() != null) {
            typo.setStatus(patch.getStatus());
        }

    }

    public void updateEntityTypo(TypoPutDTO put, Typo typo) {
        typo.setTypoMessege(put.getTypoMessege());
        typo.setTypoLink(put.getTypoLink());
        typo.setStatus(put.getStatus());
    }

    //Review
    public ReviewReadDTO toReadReview(Review review) {
        ReviewReadDTO read = new ReviewReadDTO();
        read.setId(review.getId());
        read.setReviewText(review.getReviewText());
        return read;
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
}
