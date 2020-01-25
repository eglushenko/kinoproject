package com.solvve.lab.kinoproject.service;


import com.solvve.lab.kinoproject.domain.Cast;
import com.solvve.lab.kinoproject.dto.cast.CastReadDTO;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.repository.CastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CastService {

    @Autowired
    CastRepository castRepository;

    private Cast getCastRequired(UUID id) {
        return castRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Cast.class, id));
    }

    public CastReadDTO getCast(UUID id) {
        Cast cast = getCastRequired(id);
        return toRead(cast);

    }

    public CastReadDTO toRead(Cast cast) {
        CastReadDTO castReadDTO = new CastReadDTO();
        castReadDTO.setId(cast.getId());
        castReadDTO.setName(cast.getName());
        castReadDTO.setNameRoleInFilm(cast.getNameRoleInFilm());
        castReadDTO.setRoleInFilm(cast.getRoleInFilm());
        return castReadDTO;
    }

}
