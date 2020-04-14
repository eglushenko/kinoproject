package com.solvve.lab.kinoproject.repository;


import com.solvve.lab.kinoproject.domain.Film;
import com.solvve.lab.kinoproject.dto.FilmFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FilmRepositoryCustom {
    Page<Film> findByFilter(FilmFilter filter, Pageable pageable);
}
