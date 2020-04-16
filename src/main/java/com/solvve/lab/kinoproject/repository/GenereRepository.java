package com.solvve.lab.kinoproject.repository;


import com.solvve.lab.kinoproject.domain.Genere;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus
public interface GenereRepository extends CrudRepository<Genere, UUID> {
}
