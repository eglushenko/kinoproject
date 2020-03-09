package com.solvve.lab.kinoproject.job;


import com.solvve.lab.kinoproject.repository.FilmRepository;
import com.solvve.lab.kinoproject.service.FilmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Transactional(readOnly = true)
public class UpdateAverageRateOfFilmJob {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private FilmService filmService;

    @Scheduled(cron = "${update.average.rate.of.film.job.cron}")
    public void updateAverageRateOfFilm() {
        log.info("Job started");
        filmRepository.getIdsOfFilms().forEach(filmId -> {
            try {
                filmService.updateAverageRateOfFilm(filmId);
            } catch (Exception ex) {
                log.info("Filed to update rate at film: {}", filmId, ex);
            }
        });

        log.info("Job finished");
    }
}
