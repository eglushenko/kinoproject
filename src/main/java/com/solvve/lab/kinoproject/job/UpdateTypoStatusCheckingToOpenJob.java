package com.solvve.lab.kinoproject.job;

import com.solvve.lab.kinoproject.repository.TypoRepository;
import com.solvve.lab.kinoproject.service.TypoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;


@Slf4j
@Component
@Transactional(readOnly = true)
public class UpdateTypoStatusCheckingToOpenJob {

    @Autowired
    private TypoRepository typoRepository;

    @Autowired
    private TypoService typoService;

    @Scheduled(cron = "${update.typo.status.checking.to.open.job.cron}")
    public void updateTypoStatusCheckingToOpen() {
        log.info("Job started");
        typoRepository.findAllByStatusCheckingAndNotSaved(Instant.now()
                .truncatedTo(ChronoUnit.MICROS).minus(1, ChronoUnit.HOURS)).forEach(typo -> {
                    try {
                        typoService.updateStatusChecking(typo);
                    } catch (Exception e) {
                        log.info("Filed to update status at typo: {}", typo, e);
                    }
                }
        );

        log.info("Job finished");
    }
}
