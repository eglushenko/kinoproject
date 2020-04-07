package com.solvve.lab.kinoproject.controller;


import com.solvve.lab.kinoproject.dto.typo.TypoCreateDTO;
import com.solvve.lab.kinoproject.dto.typo.TypoPatchDTO;
import com.solvve.lab.kinoproject.dto.typo.TypoPutDTO;
import com.solvve.lab.kinoproject.dto.typo.TypoReadDTO;
import com.solvve.lab.kinoproject.service.TypoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/typos")
public class TypoController {

    @Autowired
    private TypoService typoService;

    @GetMapping("/{id}")
    public TypoReadDTO getTypo(@PathVariable UUID id) {
        return typoService.getTypo(id);
    }

    @PostMapping
    public TypoReadDTO createTypo(@RequestBody TypoCreateDTO create) {
        return typoService.createTypo(create);
    }

    @PostMapping("/{id}/fix/{objectId}/")
    public void fixTypo(@PathVariable UUID id, UUID objectId, String status, @RequestParam UUID customerId) {
        typoService.fixTypoNews(id, objectId, customerId);
    }

    @PatchMapping("/{id}")
    public TypoReadDTO patchTypo(@PathVariable UUID id, @RequestBody TypoPatchDTO patch) {
        return typoService.patchTypo(id, patch);
    }

    @PutMapping("/{id}")
    public TypoReadDTO updateTypo(@PathVariable UUID id, @RequestBody TypoPutDTO put) {
        return typoService.updateTypo(id, put);
    }

    @DeleteMapping("/{id}")
    public void deleteTypo(@PathVariable UUID id) {
        typoService.deleteTypo(id);
    }
}
