package com.solvve.lab.kinoproject.repository;

import com.solvve.lab.kinoproject.domain.Typo;
import com.solvve.lab.kinoproject.enums.TypoStatus;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(statements = "delete from typo", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class TypoRepositoryTest {

    @Autowired
    private TypoRepository typoRepository;

    private Typo createTypo() {
        Typo typo = new Typo();
        typo.setTypoMessege("some text");
        typo.setStatus(TypoStatus.OPEN);
        typo.setTypoLink("link");
        return typoRepository.save(typo);
    }

    @Test
    public void testTypoCreateDate() {
        Typo typo = createTypo();
        Assert.assertNotNull(typo.getCreatedAt());

        Instant createDateBeforeLoad = typo.getCreatedAt();

        typo = typoRepository.findById(typo.getId()).get();

        Instant createDateAfterLoad = typo.getCreatedAt();

        Assertions.assertThat(createDateBeforeLoad).isEqualTo(createDateAfterLoad);
    }

    @Test
    public void testTypoUpdateDate() {
        Typo typo = createTypo();
        Assert.assertNotNull(typo.getUpdatedAt());

        Instant updateDateBeforeLoad = typo.getUpdatedAt();

        typo.setTypoLink("https://link.com");
        typo = typoRepository.save(typo);
        typo = typoRepository.findById(typo.getId()).get();

        Instant updateDateAfterLoad = typo.getUpdatedAt();

        Assert.assertNotNull(updateDateAfterLoad);
        Assertions.assertThat(updateDateAfterLoad).isAfter(updateDateBeforeLoad);
    }


}