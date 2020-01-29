package com.solvve.lab.kinoproject.repository;


import com.solvve.lab.kinoproject.domain.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CountryRepository extends CrudRepository<Country, UUID> {
}
