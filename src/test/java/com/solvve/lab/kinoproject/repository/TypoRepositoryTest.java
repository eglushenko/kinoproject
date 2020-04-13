package com.solvve.lab.kinoproject.repository;

import com.solvve.lab.kinoproject.BaseTest;
import com.solvve.lab.kinoproject.domain.Typo;
import com.solvve.lab.kinoproject.enums.TypoStatus;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;


public class TypoRepositoryTest extends BaseTest {

    @Autowired
    private TypoRepository typoRepository;

    private Typo createTypo() {
        Typo typo = new Typo();
        typo.setTypoMessage("some text");
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