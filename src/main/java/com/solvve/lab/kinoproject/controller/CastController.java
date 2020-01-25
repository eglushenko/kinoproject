package com.solvve.lab.kinoproject.controller;


import com.solvve.lab.kinoproject.dto.cast.CastReadDTO;
import com.solvve.lab.kinoproject.service.CastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/casts")
public class CastController {

    @Autowired
    private CastService castService;

    @GetMapping("/{id}")
    public CastReadDTO getCast(@PathVariable UUID id) {
        return castService.getCast(id);
    }

}
