package com.solvve.lab.kinoproject.repository;

import com.solvve.lab.kinoproject.domain.Film;
import com.solvve.lab.kinoproject.dto.FilmFilter;
import org.bitbucket.brunneng.qb.JpaQueryBuilder;
import org.bitbucket.brunneng.qb.SpringQueryBuilderUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class FilmRepositoryCustomImpl implements FilmRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Film> findByFilter(FilmFilter filter, Pageable pageable) {
        JpaQueryBuilder qb = new JpaQueryBuilder(entityManager);
        qb.append("select f from Film f where 1=1");
        qb.append("and f.length = :v", filter.getLength());
        qb.append("and f.lastUpdate >= :v", filter.getLastUpdate());
        qb.append("and f.realiseYear = :v", filter.getRealiseYear());
        return SpringQueryBuilderUtils.loadPage(qb, pageable, "id");
    }

}
