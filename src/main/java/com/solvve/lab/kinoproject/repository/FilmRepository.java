package com.solvve.lab.kinoproject.repository;

import com.solvve.lab.kinoproject.domain.Film;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface FilmRepository extends CrudRepository<Film, UUID> {
    List<Film> findByRateGreaterThan(float rate);

}
