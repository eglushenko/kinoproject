package com.solvve.lab.kinoproject.repository;

import com.solvve.lab.kinoproject.domain.Film;
import com.solvve.lab.kinoproject.dto.FilmFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class FilmRepositoryCustomImpl implements FilmRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Film> findByFilter(FilmFilter filter, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append("select f from Film f where 1=1");
        Query query = createQueryApplyingFilter(filter, pageable.getSort(), sb);
        applyPaging(query, pageable);

        List<Film> data = query.getResultList();

        long count = getCountOfFilms(filter);

        return new PageImpl<>(data, pageable, count);
    }

    private long getCountOfFilms(FilmFilter filter) {
        StringBuilder sb = new StringBuilder();
        sb.append("select count(f) from Film f where 1=1");
        Query query = createQueryApplyingFilter(filter, null, sb);
        return ((Number) query.getResultList().get(0)).longValue();
    }

    private Query createQueryApplyingFilter(FilmFilter filter, Sort sort, StringBuilder sb) {
        if (filter.getLength() != null) {
            sb.append(" and f.length = :length");
        }
        if (filter.getLastUpdate() != null) {
            sb.append(" and f.lastUpdate >= (:update)");
        }
        if (filter.getRealiseYear() != null) {
            sb.append(" and f.realiseYear = (:realise)");
        }
        if (sort != null && sort.isSorted()) {
            sb.append(" order by ");
            for (Sort.Order order : sort.toList()) {
                sb.append("f.").append(order.getProperty()).append(" ").append(order.getDirection());
            }
        }

        Query query = entityManager.createQuery(sb.toString());

        if (filter.getLength() != null) {
            query.setParameter("length", filter.getLength());
        }
        if (filter.getLastUpdate() != null) {
            query.setParameter("update", filter.getLastUpdate());
        }
        if (filter.getRealiseYear() != null) {
            query.setParameter("realise", filter.getRealiseYear());
        }

        return query;
    }

    private void applyPaging(Query query, Pageable pageable) {
        if (pageable.isPaged()) {
            query.setMaxResults(pageable.getPageSize());
            query.setFirstResult((int) pageable.getOffset());
        }
    }


}
