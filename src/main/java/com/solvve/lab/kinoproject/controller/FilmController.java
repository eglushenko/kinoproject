package com.solvve.lab.kinoproject.controller;


import com.solvve.lab.kinoproject.dto.FilmCreateDTO;
import com.solvve.lab.kinoproject.dto.FilmReadDTO;
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

    @PostMapping
    public FilmReadDTO createFilm(@RequestBody FilmCreateDTO createDTO) {
        return filmService.createFilm(createDTO);
    }

}
