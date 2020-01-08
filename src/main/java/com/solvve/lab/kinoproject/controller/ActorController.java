package com.solvve.lab.kinoproject.controller;

import com.solvve.lab.kinoproject.dto.ActorCreateDTO;
import com.solvve.lab.kinoproject.dto.ActorReadDTO;
import com.solvve.lab.kinoproject.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/actors")
public class ActorController {
    @Autowired
    ActorService actorService;

    @GetMapping("/{id}")
    public ActorReadDTO getActor(@PathVariable UUID id) {
        return actorService.getActor(id);
    }

    @PostMapping
    public ActorReadDTO createActor(@RequestBody ActorCreateDTO createDTO) {
        return actorService.createActor(createDTO);
    }
}
