package com.solvve.lab.kinoproject.service;

import com.solvve.lab.kinoproject.domain.Typo;
import com.solvve.lab.kinoproject.dto.typo.TypoCreateDTO;
import com.solvve.lab.kinoproject.dto.typo.TypoPatchDTO;
import com.solvve.lab.kinoproject.dto.typo.TypoPutDTO;
import com.solvve.lab.kinoproject.dto.typo.TypoReadDTO;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.repository.TypoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TypoService {

    @Autowired
    private TypoRepository typoRepository;

    @Autowired
    private TranslationService translationService;

    private Typo getTypoRequired(UUID id) {
        return typoRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Typo.class, id));
    }

    public TypoReadDTO getTypo(UUID id) {
        Typo typo = getTypoRequired(id);
        return translationService.toReadTypo(typo);
    }

    public TypoReadDTO createTypo(TypoCreateDTO create) {
        Typo typo = translationService.toEntityTypo(create);
        typo = typoRepository.save(typo);
        return translationService.toReadTypo(typo);
    }

    public TypoReadDTO patchTypo(UUID id, TypoPatchDTO patch) {
        Typo typo = getTypoRequired(id);
        translationService.patchEntityTypo(patch, typo);
        typo = typoRepository.save(typo);
        return translationService.toReadTypo(typo);
    }

    public TypoReadDTO putTypo(UUID id, TypoPutDTO put) {
        Typo typo = getTypoRequired(id);
        translationService.putEntityTypo(put, typo);
        typo = typoRepository.save(typo);
        return translationService.toReadTypo(typo);
    }

    public void deleteTypo(UUID id) {
        typoRepository.delete(getTypoRequired(id));
    }
}
