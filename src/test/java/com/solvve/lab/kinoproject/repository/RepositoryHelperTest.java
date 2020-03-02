package com.solvve.lab.kinoproject.repository;

import com.solvve.lab.kinoproject.domain.Cast;
import com.solvve.lab.kinoproject.domain.Film;
import com.solvve.lab.kinoproject.domain.Name;
import com.solvve.lab.kinoproject.enums.NameFilmRole;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class RepositoryHelperTest {

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