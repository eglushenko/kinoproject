package com.solvve.lab.kinoproject.repository;

import com.solvve.lab.kinoproject.domain.Film;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;


@Repository
public interface FilmRepository extends CrudRepository<Film, UUID>, FilmRepositoryCustom {

    List<Film> findByRateGreaterThan(float rate);

    @Query("select f from Film f where f.lang = :lang and f.rate >= :rate"
            + " and f.lastUpdate >= :lastUpdate and f.realiseYear >= :realiseYear order by f.lastUpdate asc")
    List<Film> findFilmSortedByRealiseYearAndlastUpdate(
            String lang, float rate, Instant lastUpdate, Instant realiseYear);

}
