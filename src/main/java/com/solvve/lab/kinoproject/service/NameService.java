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
    NameRepository nameRepository;

    public Name getNameRequired(UUID id) {
        return nameRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(Name.class, id));
    }

    public NameReadDTO getName(UUID id) {
        Name name = getNameRequired(id);
        return toRead(name);
    }

    public NameReadDTO toRead(Name name) {
        NameReadDTO nameReadDTO = new NameReadDTO();
        nameReadDTO.setId(name.getId());
        nameReadDTO.setFirstName(name.getFirstName());
        nameReadDTO.setLastName(name.getLastName());
        return nameReadDTO;
    }

    public NameReadDTO createName(NameCreateDTO create) {
        Name name = new Name();
        name.setFirstName(create.getFirstName());
        name.setLastName(create.getLastName());
        name = nameRepository.save(name);
        return toRead(name);
    }

    public NameReadDTO patchName(UUID id, NamePatchDTO patch) {
        Name name = getNameRequired(id);
        if (patch.getFirstName() != null) {
            name.setFirstName(patch.getFirstName());
        }
        if (patch.getLastName() != null) {
            name.setLastName(patch.getLastName());
        }
        name = nameRepository.save(name);
        return toRead(name);
    }

    public NameReadDTO putName(UUID id, NamePutDTO put) {
        Name name = getNameRequired(id);
        name.setFirstName(put.getFirstName());
        name.setLastName(put.getLastName());
        return toRead(name);
    }

    public void deleteName(UUID id) {
        nameRepository.delete(getNameRequired(id));
    }
}
