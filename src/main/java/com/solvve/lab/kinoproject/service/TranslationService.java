package com.solvve.lab.kinoproject.service;

import com.solvve.lab.kinoproject.domain.*;
import com.solvve.lab.kinoproject.dto.FilmReadExtendedDTO;
import com.solvve.lab.kinoproject.dto.cast.CastCreateDTO;
import com.solvve.lab.kinoproject.dto.cast.CastPatchDTO;
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
import com.solvve.lab.kinoproject.dto.typo.TypoCreateDTO;
import com.solvve.lab.kinoproject.dto.typo.TypoPatchDTO;
import com.solvve.lab.kinoproject.dto.typo.TypoPutDTO;
import com.solvve.lab.kinoproject.dto.typo.TypoReadDTO;
import org.springframework.stereotype.Service;

@Service
public class TranslationService {


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

    public Customer toEntytyCustomer(CustomerCreateDTO create) {
        Customer customer = new Customer();
        customer.setLogin(create.getLogin());
        customer.setFirstName(create.getFirstName());
        customer.setLastName(create.getLastName());
        customer.setEmail(create.getEmail());
        customer.setRole(create.getRole());
        return customer;
    }

    public void patchEntytyCustomer(CustomerPatchDTO patch, Customer customer) {
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

    public void putEntytyCustomer(CustomerPutDTO put, Customer customer) {
        customer.setLogin(put.getLogin());
        customer.setFirstName(put.getFirstName());
        customer.setLastName(put.getLastName());
        customer.setEmail(put.getEmail());
        customer.setRole(put.getRole());
    }

    //Cast
    public Cast toEntytyCast(CastCreateDTO create) {
        Cast cast = new Cast();
        //cast.setName(create.getName());
        cast.setNameRoleInFilm(create.getNameRoleInFilm());
        cast.setRoleInFilm(create.getRoleInFilm());
        return cast;
    }

    public CastReadDTO toReadCast(Cast cast) {
        CastReadDTO castReadDTO = new CastReadDTO();
        castReadDTO.setId(cast.getId());
        //castReadDTO.setName(cast.getName());
        castReadDTO.setNameRoleInFilm(cast.getNameRoleInFilm());
        castReadDTO.setRoleInFilm(cast.getRoleInFilm());
        return castReadDTO;
    }

    public void patchEntytyCast(CastPatchDTO patch, Cast cast) {
//        if (patch.getName() != null) {
//            cast.setName(patch.getName());
//        }
        if (patch.getNameRoleInFilm() != null) {
            cast.setNameRoleInFilm(patch.getNameRoleInFilm());
        }
        if (patch.getRoleInFilm() != null) {
            cast.setRoleInFilm(patch.getRoleInFilm());
        }
    }

//Comment

    public Comment toEntytyComment(CommentCreateDTO create) {
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

    public void patchEntytyComment(CommentPatchDTO patch, Comment comment) {
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
    }

    public void putEntytyComment(CommentPutDTO put, Comment comment) {
        comment.setCommentText(put.getCommentText());
        comment.setCommentStatus(put.getCommentStatus());
        comment.setPostedDate(put.getPostedDate());
        comment.setRate(put.getRate());
    }

//Name

    public Name toEntytyName(NameCreateDTO create) {
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

    public void patchEntytyName(NamePatchDTO patch, Name name) {
        if (patch.getFirstName() != null) {
            name.setFirstName(patch.getFirstName());
        }
        if (patch.getLastName() != null) {
            name.setLastName(patch.getLastName());
        }
    }

    public void putEntytyName(NamePutDTO put, Name name) {
        name.setFirstName(put.getFirstName());
        name.setLastName(put.getLastName());
    }

//Film

    public Film toEntytyFilm(FilmCreateDTO create) {
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

    public void patchEntytyFilm(FilmPatchDTO patch, Film film) {
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
        if (patch.getLength() > 0) {
            film.setLength(patch.getLength());
        }
        if (patch.getLastUpdate() != null) {
            film.setLastUpdate(patch.getLastUpdate());
        }
        if (patch.getRate() > 0.0) {
            film.setRate(patch.getRate());
        }
    }

    public void putEntytyFilm(FilmPutDTO put, Film film) {
        film.setTitle(put.getTitle());
        film.setFilmText(put.getFilmText());
        film.setCategory(put.getCategory());
        film.setCountry(put.getCountry());
        film.setLang(put.getLang());
        film.setLength(put.getLength());
        film.setLastUpdate(put.getLastUpdate());
        film.setRate(put.getRate());
    }

    public FilmReadExtendedDTO toReadExtendadFilm(Film film) {
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

    public Typo toEntytyTypo(TypoCreateDTO create) {
        Typo typo = new Typo();
        typo.setTypoMessege(create.getTypoMessege());
        typo.setTypoLink(create.getTypoLink());
        typo.setStatus(create.getStatus());
        return typo;
    }

    public void patchEntytyTypo(TypoPatchDTO patch, Typo typo) {
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

    public void putEntytyTypo(TypoPutDTO put, Typo typo) {
        typo.setTypoMessege(put.getTypoMessege());
        typo.setTypoLink(put.getTypoLink());
        typo.setStatus(put.getStatus());
    }

}
