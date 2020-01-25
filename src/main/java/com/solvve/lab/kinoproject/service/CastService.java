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

    public CastReadDTO createCast(CastCreateDTO create) {
        Cast cast = new Cast();
        cast.setName(create.getName());
        cast.setNameRoleInFilm(create.getNameRoleInFilm());
        cast.setRoleInFilm(create.getRoleInFilm());
        cast = castRepository.save(cast);
        return toRead(cast);
    }

    public CastReadDTO patchCast(UUID id, CastPatchDTO patch) {
        Cast cast = getCastRequired(id);
        if (patch.getName() != null) {
            cast.setName(patch.getName());
        }
        if (patch.getNameRoleInFilm() != null) {
            cast.setNameRoleInFilm(patch.getNameRoleInFilm());
        }
        if (patch.getRoleInFilm() != null) {
            cast.setRoleInFilm(patch.getRoleInFilm());
        }
        cast = castRepository.save(cast);
        return toRead(cast);
    }

    public CastReadDTO putCast(UUID id, CastPutDTO put) {
        Cast cast = getCastRequired(id);
        cast.setName(put.getName());
        cast.setNameRoleInFilm(put.getNameRoleInFilm());
        cast.setRoleInFilm(put.getRoleInFilm());
        cast = castRepository.save(cast);
        return toRead(cast);
    }

    public void deleteCast(UUID id) {
        castRepository.delete(getCastRequired(id));
    }

}
