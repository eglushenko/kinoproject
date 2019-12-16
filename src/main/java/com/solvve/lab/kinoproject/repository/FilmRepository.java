package com.solvve.lab.kinoproject.repository;

import com.solvve.lab.kinoproject.domain.Film;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface FilmRepository extends CrudRepository<Film, UUID> {
}
