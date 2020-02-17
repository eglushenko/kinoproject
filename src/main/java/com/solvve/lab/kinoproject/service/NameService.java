package com.solvve.lab.kinoproject.service;

import com.solvve.lab.kinoproject.domain.Name;
import com.solvve.lab.kinoproject.dto.name.NameCreateDTO;
import com.solvve.lab.kinoproject.dto.name.NamePatchDTO;
import com.solvve.lab.kinoproject.dto.name.NamePutDTO;
import com.solvve.lab.kinoproject.dto.name.NameReadDTO;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.repository.NameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NameService {

    @Autowired
    private NameRepository nameRepository;

    @Autowired
    private TranslationService translationService;

    public Name getNameRequired(UUID id) {
        return nameRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(Name.class, id));
    }

    public NameReadDTO getName(UUID id) {
        Name name = getNameRequired(id);
        return translationService.toReadName(name);
    }

    public NameReadDTO createName(NameCreateDTO create) {
        Name name = translationService.toEntityName(create);
        name = nameRepository.save(name);
        return translationService.toReadName(name);
    }

    public NameReadDTO patchName(UUID id, NamePatchDTO patch) {
        Name name = getNameRequired(id);
        translationService.patchEntityName(patch, name);
        name = nameRepository.save(name);
        return translationService.toReadName(name);
    }

    public NameReadDTO updateName(UUID id, NamePutDTO put) {
        Name name = getNameRequired(id);
        translationService.updateEntityName(put, name);
        return translationService.toReadName(name);
    }

    public void deleteName(UUID id) {
        nameRepository.delete(getNameRequired(id));
    }
}
