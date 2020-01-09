package com.solvve.lab.kinoproject.controller;

import com.solvve.lab.kinoproject.dto.NameCreateDTO;
import com.solvve.lab.kinoproject.dto.NameReadDTO;
import com.solvve.lab.kinoproject.service.NameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/names")
public class NameController {
    @Autowired
    NameService actorService;

    @GetMapping("/{id}")
    public NameReadDTO getActor(@PathVariable UUID id) {
        return actorService.getName(id);
    }

    @PostMapping
    public NameReadDTO createActor(@RequestBody NameCreateDTO createDTO) {
        return actorService.createName(createDTO);
    }
}
