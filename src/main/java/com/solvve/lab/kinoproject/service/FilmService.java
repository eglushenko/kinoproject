package com.solvve.lab.kinoproject.service;


import com.solvve.lab.kinoproject.domain.Customer;
import com.solvve.lab.kinoproject.domain.Film;
import com.solvve.lab.kinoproject.dto.FilmCreateDTO;
import com.solvve.lab.kinoproject.dto.FilmReadDTO;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;

    public FilmReadDTO getFilm(UUID uuid) {
        Film film = filmRepository.findById(uuid)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException(Customer.class, uuid);
                });
        return readFilmByUUID(film);
    }

    public FilmReadDTO readFilmByUUID(Film film) {
        FilmReadDTO filmReadDTO = new FilmReadDTO();
        filmReadDTO.setId(film.getId());
        filmReadDTO.setTitle(film.getTitle());
        filmReadDTO.setCategory(film.getCategory());
        filmReadDTO.setCountry(film.getCountry());
        filmReadDTO.setLang(film.getLang());
        filmReadDTO.setRate(film.getRate());
        filmReadDTO.setLength(film.getLength());
        filmReadDTO.setFilmText(film.getFilmText());
        filmReadDTO.setActor(film.getActor());
        filmReadDTO.setLastUpdate(film.getLastUpdate());
        return filmReadDTO;
    }

    public FilmReadDTO createFilm(FilmCreateDTO create) {
        Film film = new Film();
        film.setActor(create.getActor());
        film.setCategory(create.getCategory());
        film.setCountry(create.getCountry());
        film.setFilmText(create.getFilmText());
        film.setLang(create.getLang());
        film.setLength(create.getLength());
        film.setRate(create.getRate());
        film.setTitle(create.getTitle());
        film.setLastUpdate(create.getLastUpdate());
        film = filmRepository.save(film);

        return readFilmByUUID(film);
    }
}
