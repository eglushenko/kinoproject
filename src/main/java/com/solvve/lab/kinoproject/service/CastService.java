package com.solvve.lab.kinoproject.service;


import com.solvve.lab.kinoproject.domain.Cast;
import com.solvve.lab.kinoproject.dto.cast.CastCreateDTO;
import com.solvve.lab.kinoproject.dto.cast.CastPatchDTO;
import com.solvve.lab.kinoproject.dto.cast.CastPutDTO;
import com.solvve.lab.kinoproject.dto.cast.CastReadDTO;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.repository.CastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CastService {

    @Autowired
    private CastRepository castRepository;

    @Autowired
    private TranslationService translationService;

    private Cast getCastRequired(UUID id) {
        return castRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Cast.class, id));
    }

    public CastReadDTO getCast(UUID id) {
        Cast cast = getCastRequired(id);
        return translationService.translate(cast, CastReadDTO.class);

    }


    public CastReadDTO createCast(CastCreateDTO create) {
        Cast cast = translationService.translate(create, Cast.class);
        cast = castRepository.save(cast);
        return translationService.translate(cast, CastReadDTO.class);
    }

    public CastReadDTO patchCast(UUID id, CastPatchDTO patch) {
        Cast cast = getCastRequired(id);
        translationService.map(patch, cast);
        cast = castRepository.save(cast);
        return translationService.translate(cast, CastReadDTO.class);
    }

    public CastReadDTO updateCast(UUID id, CastPutDTO put) {
        Cast cast = getCastRequired(id);
        translationService.map(put, cast);
        cast = castRepository.save(cast);
        return translationService.translate(cast, CastReadDTO.class);
    }


    public void deleteCast(UUID id) {
        castRepository.delete(getCastRequired(id));
    }

}
