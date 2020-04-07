package com.solvve.lab.kinoproject.repository;

import com.solvve.lab.kinoproject.domain.Typo;
import com.solvve.lab.kinoproject.enums.TypoStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Repository
public interface TypoRepository extends CrudRepository<Typo, UUID> {

    List<Typo> findAllByErrorTextAndStatus(String errorText, TypoStatus status);

    @Query("select t.id from Typo t where t.updatedAt <= :data order by t.updatedAt asc")
    Stream<UUID> findAllByStatusCheckingAndNotSaved(Instant data);

}
