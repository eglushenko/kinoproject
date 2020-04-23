package com.solvve.lab.kinoproject.service.importer;


import com.solvve.lab.kinoproject.client.themoviedb.TheMovieDbClient;
import com.solvve.lab.kinoproject.client.themoviedb.dto.PersonReadDTO;
import com.solvve.lab.kinoproject.domain.Name;
import com.solvve.lab.kinoproject.exception.ImportAlreadyPerformedException;
import com.solvve.lab.kinoproject.exception.ImportedEntityAlreadyExistException;
import com.solvve.lab.kinoproject.repository.NameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
public class PersonImportService {

    @Autowired
    private TheMovieDbClient movieDbClient;

    @Autowired
    private ExternalSystemImportService externalSystemImportService;

    @Autowired
    private NameRepository nameRepository;

    @Transactional
    public UUID importPerson(String externalId) throws ImportedEntityAlreadyExistException,
            ImportAlreadyPerformedException {

        externalSystemImportService.validateNotImported(Name.class, externalId);

        PersonReadDTO read = movieDbClient.getPerson(externalId);
        Name existingName = nameRepository.findByName(read.getName());
        if (existingName != null) {
            throw new ImportedEntityAlreadyExistException(Name.class, existingName.getId(),
                    "Person with name = " + read.getName() + " already exist");
        }
        Name name = new Name();
        name.setName(read.getName());
        nameRepository.save(name);

        externalSystemImportService.createExternalSystemImport(name, externalId);

        return name.getId();

    }
}
