package com.solvve.lab.kinoproject.repository;

import com.solvve.lab.kinoproject.BaseTest;
import com.solvve.lab.kinoproject.domain.Cast;
import com.solvve.lab.kinoproject.domain.Film;
import com.solvve.lab.kinoproject.domain.Name;
import com.solvve.lab.kinoproject.enums.NameFilmRole;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;


public class RepositoryHelperTest extends BaseTest {

    @Autowired
    private RepositoryHelper repositoryHelper;

    @Test(expected = EntityNotFoundException.class)
    public void testGetEntytyIfExist() {
        Cast cast = new Cast();
        cast.setNameRoleInFilm("role");
        cast.setRoleInFilm(NameFilmRole.ACTOR);
        cast.setName(repositoryHelper.getReferenceIfExist(Name.class, UUID.randomUUID()));
        cast.setFilm(repositoryHelper.getReferenceIfExist(Film.class, UUID.randomUUID()));
    }
}