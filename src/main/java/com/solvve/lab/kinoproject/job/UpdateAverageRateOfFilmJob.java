package com.solvve.lab.kinoproject.job;


import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UpdateAverageRateOfFilmJob {

    @Scheduled(cron = "${update.average.rate.of.film.job.cron}")
    public void updateAverageRateOfFilm() {
        log.info("Job started");
        //TODO
        log.info("Job finished");
    }
}
