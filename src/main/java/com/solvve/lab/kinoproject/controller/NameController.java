package com.solvve.lab.kinoproject.controller;

import com.solvve.lab.kinoproject.dto.name.NameCreateDTO;
import com.solvve.lab.kinoproject.dto.name.NamePatchDTO;
import com.solvve.lab.kinoproject.dto.name.NamePutDTO;
import com.solvve.lab.kinoproject.dto.name.NameReadDTO;
import com.solvve.lab.kinoproject.service.NameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/names")
public class NameController {
    @Autowired
    private NameService nameService;

    @GetMapping("/{id}")
    public NameReadDTO getActor(@PathVariable UUID id) {
        return nameService.getName(id);
    }

    @PostMapping
    public NameReadDTO createActor(@RequestBody NameCreateDTO createDTO) {
        return nameService.createName(createDTO);
    }

    @PatchMapping("/{id}")
    public NameReadDTO patchName(@PathVariable UUID id, @RequestBody NamePatchDTO patch) {
        return nameService.patchName(id, patch);
    }

    @PutMapping("/{id}")
    public NameReadDTO putName(@PathVariable UUID id, @RequestBody NamePutDTO put) {
        return nameService.putName(id, put);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteName(@PathVariable UUID id) {
        nameService.deleteName(id);
    }
}
