package com.solvve.lab.kinoproject.job.oneoff;


import com.solvve.lab.kinoproject.client.themoviedb.TheMovieDbClient;
import com.solvve.lab.kinoproject.client.themoviedb.dto.MovieReadShortDTO;
import com.solvve.lab.kinoproject.exception.ImportAlreadyPerformedException;
import com.solvve.lab.kinoproject.exception.ImportedEntityAlreadyExistException;
import com.solvve.lab.kinoproject.service.AsyncService;
import com.solvve.lab.kinoproject.service.importer.MovieImportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Component
public class TheMovieDbImportOneOffJob {

    @Autowired
    private AsyncService asyncService;

    @Autowired
    private TheMovieDbClient client;

    @Autowired
    private MovieImportService movieImportService;

    @Value("${themoviedb.import.job.enabled}")
    private boolean enabled;

    @PostConstruct
    void executeJob() {
        if (!enabled) {
            log.info("import is disabled");
            return;
        }
        asyncService.executeAsync(this::doImport);
    }


    public void doImport() {
        log.info("Starting import");
        try {
            List<MovieReadShortDTO> moviesToImport = client.getTopRatedMovies().getResults();
            int successfullyImportedFilm = 0;
            int skipped = 0;
            int failed = 0;
            for (MovieReadShortDTO m : moviesToImport) {
                try {
                    movieImportService.importMovie(m.getId());
                    successfullyImportedFilm++;
                } catch (ImportAlreadyPerformedException | ImportedEntityAlreadyExistException e) {
                    log.info("Can't import movie id={} , originalTitle={}: {}",
                            m.getId(), m.getOriginalTitle(), e.getMessage());
                    skipped++;
                } catch (Exception e) {
                    log.info("Filed to import movie id = {}", m.getId());
                    failed++;
                }
                log.info("Total movies to import: {} , successfully {} , skipped {}, failed {}",
                        moviesToImport.size(), successfullyImportedFilm, skipped, failed);
            }
        } catch (Exception e) {
            log.warn("Filed to perform import", e);
        }
        log.info("Import is finished");
    }
}
