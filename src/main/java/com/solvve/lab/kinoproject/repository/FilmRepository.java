package com.solvve.lab.kinoproject.repository;

import com.solvve.lab.kinoproject.domain.Film;
import com.solvve.lab.kinoproject.dto.film.FilmTopRatedReadDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;


@Repository
public interface FilmRepository extends CrudRepository<Film, UUID>, FilmRepositoryCustom {

    List<Film> findByAverageRateGreaterThan(Double rate);

    @Query("select new com.solvve.lab.kinoproject.dto.film.FilmTopRatedReadDTO(f.id, f.title, f.averageRate,"
            + "(select count(v) from Rate v where v.ratedObjectId = f.id and v.rate is not null))" +
            " from Film f order by f.averageRate desc")
    List<FilmTopRatedReadDTO> getTopRatedFilms();

    @Query("select f from Film f where f.lang = :lang and f.averageRate >= :averageRate"
            + " and f.lastUpdate >= :lastUpdate and f.realiseYear >= :realiseYear order by f.lastUpdate asc")
    List<Film> findFilmSortedByRealiseYearAndLastUpdate(
            String lang, Double averageRate, Instant lastUpdate, Instant realiseYear);

    @Query("select f.id from Film f")
    Stream<UUID> getIdsOfFilms();


}
