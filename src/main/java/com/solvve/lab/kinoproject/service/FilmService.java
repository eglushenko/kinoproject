package com.solvve.lab.kinoproject.service;


import com.solvve.lab.kinoproject.domain.Film;
import com.solvve.lab.kinoproject.dto.FilmFilter;
import com.solvve.lab.kinoproject.dto.FilmReadExtendedDTO;
import com.solvve.lab.kinoproject.dto.PageResult;
import com.solvve.lab.kinoproject.dto.film.*;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.repository.FilmRepository;
import com.solvve.lab.kinoproject.repository.RateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private TranslationService translationService;

    private Film getFilmRequired(UUID id) {
        return filmRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Film.class, id));
    }

    public FilmReadDTO getFilm(UUID id) {
        Film film = getFilmRequired(id);
        return translationService.translate(film, FilmReadDTO.class);
    }

    public PageResult<FilmReadDTO> getFilms(FilmFilter filter, Pageable pageable) {
        Page<Film> films = filmRepository.findByFilter(filter, pageable);
        return translationService.toPageResult(films, FilmReadDTO.class);
    }

    public FilmReadExtendedDTO getFilmExtended(UUID id) {
        Film film = getFilmRequired(id);
        return translationService.translate(film, FilmReadExtendedDTO.class);
    }

    public FilmReadDTO createFilm(FilmCreateDTO create) {
        Film film = translationService.translate(create, Film.class);
        film = filmRepository.save(film);
        return translationService.translate(film, FilmReadDTO.class);
    }

    public FilmReadDTO patchFilm(UUID id, FilmPatchDTO patch) {
        Film film = getFilmRequired(id);
        translationService.map(patch, film);
        film = filmRepository.save(film);
        return translationService.translate(film, FilmReadDTO.class);
    }

    public FilmReadDTO updateFilm(UUID id, FilmPutDTO put) {
        Film film = getFilmRequired(id);
        translationService.map(put, film);
        film = filmRepository.save(film);
        return translationService.translate(film, FilmReadDTO.class);
    }

    public void deleteFilm(UUID id) {
        filmRepository.delete(getFilmRequired(id));
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateAverageRateOfFilm(UUID filmId) {
        Double averageRate = rateRepository.calcAverageMarkOfObjectId(filmId);
        Film film = filmRepository.findById(filmId).orElseThrow(() -> new EntityNotFoundException(Film.class, filmId));
        log.info("Setting avg rate film: {}. Old value: {}, new value: {}",
                filmId, film.getAverageRate(), averageRate);
        film.setAverageRate(averageRate);
        filmRepository.save(film);

    }

    public List<FilmTopRatedReadDTO> getTopRatedFilms() {
        return filmRepository.getTopRatedFilms();
    }
}
