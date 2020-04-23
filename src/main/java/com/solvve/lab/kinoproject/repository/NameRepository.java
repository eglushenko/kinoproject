package com.solvve.lab.kinoproject.repository;

import com.solvve.lab.kinoproject.domain.Name;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface NameRepository extends CrudRepository<Name, UUID> {
    Name findByName(String name);
}
