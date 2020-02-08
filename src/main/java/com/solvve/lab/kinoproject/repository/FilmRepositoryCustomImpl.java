package com.solvve.lab.kinoproject.repository;

import com.solvve.lab.kinoproject.domain.Film;
import com.solvve.lab.kinoproject.dto.FilmFilter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class FilmRepositoryCustomImpl implements FilmRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Film> findByFilter(FilmFilter filter) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select f from Film f where 1=1");
        if (filter.getLength() > 0) {
            stringBuilder.append(" and f.film.length = :length");
        }
        if (filter.getLastUpdate() != null) {
            stringBuilder.append(" and f.lastUpdate = :lastUpdate");
        }
        if (filter.getRealiseYear() != null) {
            stringBuilder.append(" and f.realiseYear = :realiseYear");
        }
        TypedQuery<Film> query = entityManager.createQuery(stringBuilder.toString(), Film.class);
        if (filter.getLength() > 0) {
            query.setParameter("lenght", filter.getLength());
        }
        if (filter.getRealiseYear() != null) {
            query.setParameter("lastUpdate", filter.getLastUpdate());
        }
        if (filter.getRealiseYear() != null) {
            query.setParameter("realiseYear", filter.getRealiseYear());
        }
        return query.getResultList();
    }
}
