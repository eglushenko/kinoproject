package com.solvve.lab.kinoproject.service;

import com.solvve.lab.kinoproject.domain.Film;
import com.solvve.lab.kinoproject.domain.Genere;
import com.solvve.lab.kinoproject.dto.genere.GenereReadDTO;
import com.solvve.lab.kinoproject.exception.hander.LinkDuplicatedException;
import com.solvve.lab.kinoproject.repository.FilmRepository;
import com.solvve.lab.kinoproject.repository.RepositoryHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;


@Service
public class GenereService {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private RepositoryHelper repositoryHelper;

    @Autowired
    private TranslationService translationService;

    @Transactional
    public List<GenereReadDTO> addGenereToFilm(UUID filmId, UUID id) {
        Film film = repositoryHelper.getByIdRequired(Film.class, filmId);
        Genere genere = repositoryHelper.getByIdRequired(Genere.class, id);

        if (film.getFilmGeneres().stream().anyMatch(fl -> fl.getId().equals(id))) {
            throw new LinkDuplicatedException(String.format("Film %s already has genere %s", filmId, id));
        }

        film.getFilmGeneres().add(genere);
        film = filmRepository.save(film);

        return translationService.translateList(film.getFilmGeneres(), GenereReadDTO.class);
    }

    @Transactional
    public List<GenereReadDTO> removeGenereFromFilm(UUID filmId, UUID id) {
        Film film = repositoryHelper.getByIdRequired(Film.class, filmId);

        boolean removed = film.getFilmGeneres().removeIf(fl -> fl.getId().equals(id));
        if (!removed) {
            throw new EntityNotFoundException("Film " + filmId + "has no genere" + id);
        }

        film = filmRepository.save(film);
        return translationService.translateList(film.getFilmGeneres(), GenereReadDTO.class);
    }
}
