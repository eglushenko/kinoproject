package com.solvve.lab.kinoproject.controller;


import com.solvve.lab.kinoproject.controller.validation.ControllerValidationUtil;
import com.solvve.lab.kinoproject.dto.FilmFilter;
import com.solvve.lab.kinoproject.dto.FilmReadExtendedDTO;
import com.solvve.lab.kinoproject.dto.PageResult;
import com.solvve.lab.kinoproject.dto.film.*;
import com.solvve.lab.kinoproject.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/films")
public class FilmController {

    @Autowired
    private FilmService filmService;

    @GetMapping
    public PageResult<FilmReadDTO> getFilms(FilmFilter filter, Pageable pageable) {
        return filmService.getFilms(filter, pageable);
    }

    @GetMapping("/top-rated")
    public List<FilmTopRatedReadDTO> getTopRatedFilms() {
        return filmService.getTopRatedFilms();
    }

    @GetMapping("/{id}")
    public FilmReadDTO getFilm(@PathVariable UUID id) {
        return filmService.getFilm(id);
    }

    @GetMapping("/info/{id}")
    public FilmReadExtendedDTO getFilmExtended(@PathVariable UUID id) {
        return filmService.getFilmExtended(id);
    }

    @PostMapping
    public FilmReadDTO createFilm(@RequestBody @Valid FilmCreateDTO create) {
        ControllerValidationUtil.validateNotEquals(create.getRealiseYear(), create.getLastUpdate(),
                "realise_year", "last_update");
        return filmService.createFilm(create);
    }

    @PatchMapping("/{id}")
    public FilmReadDTO patchFilm(@PathVariable UUID id, @RequestBody FilmPatchDTO patch) {
        return filmService.patchFilm(id, patch);
    }

    @PutMapping("/{id}")
    public FilmReadDTO updateFilm(@PathVariable UUID id, @RequestBody FilmPutDTO put) {
        return filmService.updateFilm(id, put);
    }

    @DeleteMapping("/{id}")
    public void deleteFilm(@PathVariable UUID id) {
        filmService.deleteFilm(id);
    }
}
