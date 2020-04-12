package com.solvve.lab.kinoproject.repository;

import com.solvve.lab.kinoproject.domain.Cast;
import com.solvve.lab.kinoproject.enums.NameFilmRole;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import java.time.Instant;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(statements =
        "delete from cast",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CastRepositoryTest {

    @Autowired
    private CastRepository castRepository;

    private Cast createCast() {
        Cast cast = new Cast();
        cast.setRoleInFilm(NameFilmRole.ACTOR);
        cast.setNameRoleInFilm("Jhon Dou");
        return castRepository.save(cast);
    }

    @Test
    public void testCastCreateDate() {
        Cast cast = createCast();
        Assert.assertNotNull(cast.getCreatedAt());

        Instant createDateBeforeLoad = cast.getCreatedAt();

        cast = castRepository.findById(cast.getId()).get();

        Instant createDateAfterLoad = cast.getCreatedAt();

        Assertions.assertThat(createDateBeforeLoad).isEqualTo(createDateAfterLoad);
    }

    @Test
    public void testCastUpdateDate() {
        Cast cast = createCast();
        Assert.assertNotNull(cast.getUpdatedAt());

        Instant updateDateBeforeLoad = cast.getUpdatedAt();

        cast.setRoleInFilm(NameFilmRole.PRODUSER);
        cast = castRepository.save(cast);
        cast = castRepository.findById(cast.getId()).get();

        Instant updateDateAfterLoad = cast.getUpdatedAt();

        Assert.assertNotNull(updateDateAfterLoad);
        Assertions.assertThat(updateDateAfterLoad).isAfter(updateDateBeforeLoad);
    }

    @Test(expected = TransactionSystemException.class)
    public void testSaveCastValidation() {
        Cast cast = new Cast();
        castRepository.save(cast);
    }

}