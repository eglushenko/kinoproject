package com.solvve.lab.kinoproject.service;


import com.solvve.lab.kinoproject.domain.Rate;
import com.solvve.lab.kinoproject.dto.rate.RateCreateDTO;
import com.solvve.lab.kinoproject.dto.rate.RatePatchDTO;
import com.solvve.lab.kinoproject.dto.rate.RatePutDTO;
import com.solvve.lab.kinoproject.dto.rate.RateReadDTO;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RateService {

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private TranslationService translationService;

    private Rate getRateRequired(UUID id) {
        return rateRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Rate.class, id));
    }

    public RateReadDTO getRate(UUID id) {
        Rate rate = getRateRequired(id);
        return translationService.translate(rate, RateReadDTO.class);

    }


    public RateReadDTO createRate(RateCreateDTO create) {
        Rate rate = translationService.translate(create, Rate.class);
        rate = rateRepository.save(rate);
        return translationService.translate(rate, RateReadDTO.class);
    }

    public RateReadDTO patchRate(UUID id, RatePatchDTO patch) {
        Rate rate = getRateRequired(id);
        translationService.map(patch, rate);
        rate = rateRepository.save(rate);
        return translationService.translate(rate, RateReadDTO.class);
    }

    public RateReadDTO updateRate(UUID id, RatePutDTO put) {
        Rate rate = getRateRequired(id);
        translationService.map(put, rate);
        rate = rateRepository.save(rate);
        return translationService.translate(rate, RateReadDTO.class);
    }


    public void deleteRate(UUID id) {
        rateRepository.delete(getRateRequired(id));
    }

}
