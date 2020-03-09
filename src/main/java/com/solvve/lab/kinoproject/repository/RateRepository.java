package com.solvve.lab.kinoproject.repository;

import com.solvve.lab.kinoproject.domain.Rate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface RateRepository extends CrudRepository<Rate, UUID> {

    @Query("select avg(v.rate) from Rate v where v.ratedObjectId = :ratedObjectId")
    Double calcAverageMarkOfObjectId(UUID ratedObjectId);
}
