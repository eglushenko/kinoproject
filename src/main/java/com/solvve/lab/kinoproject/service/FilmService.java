package com.solvve.lab.kinoproject.service;


import com.solvve.lab.kinoproject.domain.Film;
import com.solvve.lab.kinoproject.dto.FilmFilter;
import com.solvve.lab.kinoproject.dto.FilmReadExtendedDTO;
import com.solvve.lab.kinoproject.dto.film.FilmCreateDTO;
import com.solvve.lab.kinoproject.dto.film.FilmPatchDTO;
import com.solvve.lab.kinoproject.dto.film.FilmPutDTO;
import com.solvve.lab.kinoproject.dto.film.FilmReadDTO;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.repository.FilmRepository;
import com.solvve.lab.kinoproject.repository.RateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public List<FilmReadDTO> getFilms(FilmFilter filter) {
        List<Film> films = filmRepository.findByFilter(filter);
        return films.stream().map(translationService::toReadFilm).collect(Collectors.toList());
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
}
