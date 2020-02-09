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
        if (filter.getLength() != null) {
            stringBuilder.append(" and f.length = :length");
        }
        if (filter.getLastUpdate() != null) {
            stringBuilder.append(" and f.lastUpdate >= (:update)");
        }
        if (filter.getRealiseYear() != null) {
            stringBuilder.append(" and f.realiseYear = (:realise)");
        }
        TypedQuery<Film> query = entityManager.createQuery(stringBuilder.toString(), Film.class);
        if (filter.getLength() != null) {
            query.setParameter("length", filter.getLength());
        }
        if (filter.getLastUpdate() != null) {
            query.setParameter("update", filter.getLastUpdate());
        }
        if (filter.getRealiseYear() != null) {
            query.setParameter("realise", filter.getRealiseYear());
        }
        return query.getResultList();
    }
}
