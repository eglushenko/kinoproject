package com.solvve.lab.kinoproject.repository;

import com.solvve.lab.kinoproject.domain.Film;
import com.solvve.lab.kinoproject.dto.FilmFilter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class FilmRepositoryCustomImpl implements FilmRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Film> findByFilter(FilmFilter filter) {
        return null;
    }
}
