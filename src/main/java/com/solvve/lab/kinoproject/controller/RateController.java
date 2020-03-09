package com.solvve.lab.kinoproject.controller;


import com.solvve.lab.kinoproject.dto.rate.RateCreateDTO;
import com.solvve.lab.kinoproject.dto.rate.RatePatchDTO;
import com.solvve.lab.kinoproject.dto.rate.RatePutDTO;
import com.solvve.lab.kinoproject.dto.rate.RateReadDTO;
import com.solvve.lab.kinoproject.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/rates")
public class RateController {

    @Autowired
    private RateService rateService;

    @GetMapping("/{id}")
    public RateReadDTO getRate(@PathVariable UUID id) {
        return rateService.getRate(id);
    }

    @PostMapping
    public RateReadDTO createRate(@RequestBody RateCreateDTO create) {
        return rateService.createRate(create);
    }

    @PatchMapping("/{id}")
    public RateReadDTO patchRate(@PathVariable UUID id, @RequestBody RatePatchDTO patch) {
        return rateService.patchRate(id, patch);
    }

    @PutMapping("/{id}")
    public RateReadDTO updateRate(@PathVariable UUID id, @RequestBody RatePutDTO put) {
        return rateService.updateRate(id, put);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteRate(@PathVariable UUID id) {
        rateService.deleteRate(id);
    }
}
