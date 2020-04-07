package com.solvve.lab.kinoproject.service;

import com.solvve.lab.kinoproject.domain.Customer;
import com.solvve.lab.kinoproject.domain.News;
import com.solvve.lab.kinoproject.domain.Typo;
import com.solvve.lab.kinoproject.dto.typo.TypoCreateDTO;
import com.solvve.lab.kinoproject.dto.typo.TypoPatchDTO;
import com.solvve.lab.kinoproject.dto.typo.TypoPutDTO;
import com.solvve.lab.kinoproject.dto.typo.TypoReadDTO;
import com.solvve.lab.kinoproject.enums.TypoStatus;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.exception.EntityWrongStatusException;
import com.solvve.lab.kinoproject.repository.NewsRepository;
import com.solvve.lab.kinoproject.repository.RepositoryHelper;
import com.solvve.lab.kinoproject.repository.TypoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class TypoService {

    @Autowired
    private TypoRepository typoRepository;

    @Autowired
    private TranslationService translationService;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private RepositoryHelper repositoryHelper;

    private Typo getTypoRequired(UUID id) {
        return typoRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Typo.class, id));
    }


    public TypoReadDTO getTypo(UUID id) {
        Typo typo = getTypoRequired(id);
        return translationService.toReadTypo(typo);
    }

    public TypoReadDTO createTypo(TypoCreateDTO create) {
        Typo typo = translationService.toEntityTypo(create);
        typo = typoRepository.save(typo);
        return translationService.toReadTypo(typo);
    }

    public TypoReadDTO patchTypo(UUID id, TypoPatchDTO patch) {
        Typo typo = getTypoRequired(id);
        translationService.patchEntityTypo(patch, typo);
        typo = typoRepository.save(typo);
        return translationService.toReadTypo(typo);
    }

    public TypoReadDTO updateTypo(UUID id, TypoPutDTO put) {
        Typo typo = getTypoRequired(id);
        translationService.updateEntityTypo(put, typo);
        typo = typoRepository.save(typo);
        return translationService.toReadTypo(typo);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void fixTypoNews(UUID id, UUID objectId, UUID customerId) {
        Typo typo = getTypoRequired(id);
        if (!typo.getStatus().equals(TypoStatus.OPEN)) {
            throw new EntityWrongStatusException(Typo.class, typo.getStatus().toString());
        }
        typo.setStatus(TypoStatus.CHECKING);
        typoRepository.save(typo);

        News news = repositoryHelper.getReferenceIfExist(News.class, objectId);

        news.setTextNews(typo.getRightText());
        List<Typo> typos = typoRepository
                .findAllByErrorTextAndStatus(typo.getErrorText(), TypoStatus.OPEN);

        typos.forEach(typo1 -> {
            if (typo1.getStatus().equals(TypoStatus.OPEN)) {
                typo1.setStatus(TypoStatus.CLOSED);
            } else {

                throw new EntityWrongStatusException(Typo.class, typo1.getStatus().toString());
            }
            typo1.setCustomer(repositoryHelper.getReferenceIfExist(Customer.class, customerId));
            typoRepository.save(typo1);
        });
        newsRepository.save(news);

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateStatusChecking(UUID id) {
        Typo typo = getTypoRequired(id);
        typo.setStatus(TypoStatus.OPEN);
        typoRepository.save(typo);
    }

    public void deleteTypo(UUID id) {
        typoRepository.delete(getTypoRequired(id));
    }
}
