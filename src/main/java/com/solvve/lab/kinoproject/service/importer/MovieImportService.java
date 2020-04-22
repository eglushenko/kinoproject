package com.solvve.lab.kinoproject.service.importer;


import com.solvve.lab.kinoproject.client.themoviedb.TheMovieDbClient;
import com.solvve.lab.kinoproject.client.themoviedb.dto.MovieReadDTO;
import com.solvve.lab.kinoproject.domain.Film;
import com.solvve.lab.kinoproject.exception.ImportAlreadyPerformedException;
import com.solvve.lab.kinoproject.exception.ImportedEntityAlreadyExistException;
import com.solvve.lab.kinoproject.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class MovieImportService {

    @Autowired
    private TheMovieDbClient movieDbClient;

    @Autowired
    private ExternalSystemImportService externalSystemImportService;

    @Autowired
    private FilmRepository filmRepository;

    @Transactional
    public UUID importMovie(String externalId) throws ImportedEntityAlreadyExistException,
            ImportAlreadyPerformedException {

        externalSystemImportService.validateNotImported(Film.class, externalId);

        MovieReadDTO read = movieDbClient.getMovie(externalId);
        Film existingFilm = filmRepository.findByOriginalTitle(read.getOriginalTitle());
        if (existingFilm != null) {
            throw new ImportedEntityAlreadyExistException(Film.class, existingFilm.getId(),
                    "Movie with title = " + read.getOriginalTitle() + " already exist");
        }
        Film film = new Film();
        film.setTitle(read.getTitle());
        filmRepository.save(film);

        externalSystemImportService.createExternalSystemImport(film, externalId);

        return film.getId();

    }


}
