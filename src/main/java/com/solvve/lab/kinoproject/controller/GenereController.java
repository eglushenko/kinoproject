package com.solvve.lab.kinoproject.controller;


import com.solvve.lab.kinoproject.dto.genere.GenereReadDTO;
import com.solvve.lab.kinoproject.service.GenereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class GenereController {

    @Autowired
    private GenereService genereService;

    @PostMapping("/films/{filmId}/generes/{id}")
    public List<GenereReadDTO> addGenereToFilm(@PathVariable UUID filmId, @PathVariable UUID id) {
        return genereService.addGenereToFilm(filmId, id);
    }

    @DeleteMapping("/films/{filmId}/generes/{id}")
    public List<GenereReadDTO> removeGenereFromFilm(@PathVariable UUID filmId, @PathVariable UUID id) {
        return genereService.removeGenereFromFilm(filmId, id);
    }
}
