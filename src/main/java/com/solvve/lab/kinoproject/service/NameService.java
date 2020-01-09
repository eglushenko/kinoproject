package com.solvve.lab.kinoproject.service;

import com.solvve.lab.kinoproject.domain.Name;
import com.solvve.lab.kinoproject.dto.NameCreateDTO;
import com.solvve.lab.kinoproject.dto.NameReadDTO;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.repository.NameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NameService {
    @Autowired
    NameRepository nameRepository;

    public NameReadDTO getName(UUID uuid) {
        Name name = nameRepository.findById(uuid)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException(Name.class, uuid);
                });
        return readDTObyUUID(name);
    }

    public NameReadDTO readDTObyUUID(Name name) {
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
        return readDTObyUUID(name);
    }
}
