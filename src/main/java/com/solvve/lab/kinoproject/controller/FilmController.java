package com.solvve.lab.kinoproject.controller;


import com.solvve.lab.kinoproject.dto.FilmReadExtendedDTO;
import com.solvve.lab.kinoproject.dto.film.FilmCreateDTO;
import com.solvve.lab.kinoproject.dto.film.FilmPatchDTO;
import com.solvve.lab.kinoproject.dto.film.FilmPutDTO;
import com.solvve.lab.kinoproject.dto.film.FilmReadDTO;
import com.solvve.lab.kinoproject.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/films")
public class FilmController {

    @Autowired
    private FilmService filmService;

    @GetMapping("/{id}")
    public FilmReadDTO getFilm(@PathVariable UUID id) {
        return filmService.getFilm(id);
    }

    @GetMapping("/info/{id}")
    public FilmReadExtendedDTO getFilmExtended(@PathVariable UUID id) {
        return filmService.getFilmExtended(id);
    }

    @PostMapping
    public FilmReadDTO createFilm(@RequestBody FilmCreateDTO createDTO) {
        return filmService.createFilm(createDTO);
    }

    @PatchMapping("/{id}")
    public FilmReadDTO patchFilm(@PathVariable UUID id, @RequestBody FilmPatchDTO patch) {
        return filmService.patchFilm(id, patch);
    }

    @PutMapping("/{id}")
    public FilmReadDTO putFilm(@PathVariable UUID id, @RequestBody FilmPutDTO put) {
        return filmService.putFilm(id, put);
    }

    @DeleteMapping("/{id}")
    public void deleteFilm(@PathVariable UUID id) {
        filmService.deleteFilm(id);
    }
}
