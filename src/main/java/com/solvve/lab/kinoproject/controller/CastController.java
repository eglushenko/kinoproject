package com.solvve.lab.kinoproject.controller;


import com.solvve.lab.kinoproject.dto.cast.CastCreateDTO;
import com.solvve.lab.kinoproject.dto.cast.CastPatchDTO;
import com.solvve.lab.kinoproject.dto.cast.CastPutDTO;
import com.solvve.lab.kinoproject.dto.cast.CastReadDTO;
import com.solvve.lab.kinoproject.service.CastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public CastReadDTO createCast(@RequestBody CastCreateDTO create) {
        return castService.createCast(create);
    }

    @PatchMapping("/{id}")
    public CastReadDTO patchCast(@PathVariable UUID id, @RequestBody CastPatchDTO patch) {
        return castService.patchCast(id, patch);
    }

    @PutMapping("/{id}")
    public CastReadDTO putCast(@PathVariable UUID id, @RequestBody CastPutDTO put) {
        return castService.putCast(id, put);
    }

    @DeleteMapping("/{id}")
    public void deleteCast(@PathVariable UUID id) {
        castService.deleteCast(id);
    }

}