package com.solvve.lab.kinoproject.service;

import com.solvve.lab.kinoproject.domain.Actor;
import com.solvve.lab.kinoproject.dto.ActorReadDTO;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ActorService {
    @Autowired
    ActorRepository actorRepository;

    public ActorReadDTO getActor(UUID uuid) {
        Actor actor = actorRepository.findById(uuid)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException(Actor.class, uuid);
                });
        return readDTObyUUID(actor);
    }

    public ActorReadDTO readDTObyUUID(Actor actor) {
        ActorReadDTO actorReadDTO = new ActorReadDTO();
        actorReadDTO.setId(actor.getId());
        actorReadDTO.setFirstName(actor.getFirstName());
        actorReadDTO.setLastName(actor.getLastName());
        return actorReadDTO;
    }
}
