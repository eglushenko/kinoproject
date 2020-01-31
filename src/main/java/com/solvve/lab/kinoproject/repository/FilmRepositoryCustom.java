package com.solvve.lab.kinoproject.repository;


import com.solvve.lab.kinoproject.domain.Film;
import com.solvve.lab.kinoproject.dto.FilmFilter;

import java.util.List;

public interface FilmRepositoryCustom {
    List<Film> findByFilter(FilmFilter filter);
}
