package com.solvve.lab.kinoproject.service;


import com.solvve.lab.kinoproject.domain.Customer;
import com.solvve.lab.kinoproject.domain.Film;
import com.solvve.lab.kinoproject.dto.FilmFilter;
import com.solvve.lab.kinoproject.dto.FilmReadExtendedDTO;
import com.solvve.lab.kinoproject.dto.film.FilmCreateDTO;
import com.solvve.lab.kinoproject.dto.film.FilmPatchDTO;
import com.solvve.lab.kinoproject.dto.film.FilmPutDTO;
import com.solvve.lab.kinoproject.dto.film.FilmReadDTO;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private TranslationService translationService;


    private Film getFilmRequired(UUID id) {
        return filmRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Customer.class, id));
    }

    public FilmReadDTO getFilm(UUID id) {
        Film film = getFilmRequired(id);
        return translationService.toReadFilm(film);
    }

    public List<FilmReadDTO> getFilms(FilmFilter filter) {
        List<Film> films = filmRepository.findByFilter(filter);
        return films.stream().map(translationService::toReadFilm).collect(Collectors.toList());
    }

    public FilmReadExtendedDTO getFilmExtended(UUID id) {
        Film film = getFilmRequired(id);
        return translationService.toReadExtendedFilm(film);
    }

    public FilmReadDTO createFilm(FilmCreateDTO create) {
        Film film = translationService.toEntityFilm(create);
        film = filmRepository.save(film);
        return translationService.toReadFilm(film);
    }

    public FilmReadDTO patchFilm(UUID id, FilmPatchDTO patch) {
        Film film = getFilmRequired(id);
        translationService.patchEntityFilm(patch, film);
        film = filmRepository.save(film);
        return translationService.toReadFilm(film);
    }

    public FilmReadDTO putFilm(UUID id, FilmPutDTO put) {
        Film film = getFilmRequired(id);
        translationService.putEntityFilm(put, film);
        film = filmRepository.save(film);
        return translationService.toReadFilm(film);
    }


    public void deleteFilm(UUID id) {
        filmRepository.delete(getFilmRequired(id));
    }
}
