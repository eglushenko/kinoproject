package com.solvve.lab.kinoproject.service;

import com.solvve.lab.kinoproject.domain.News;
import com.solvve.lab.kinoproject.domain.Typo;
import com.solvve.lab.kinoproject.dto.typo.TypoCreateDTO;
import com.solvve.lab.kinoproject.dto.typo.TypoPatchDTO;
import com.solvve.lab.kinoproject.dto.typo.TypoPutDTO;
import com.solvve.lab.kinoproject.dto.typo.TypoReadDTO;
import com.solvve.lab.kinoproject.enums.TypoStatus;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.exception.EntityWrongStatusException;
import com.solvve.lab.kinoproject.repository.CustomerRepository;
import com.solvve.lab.kinoproject.repository.NewsRepository;
import com.solvve.lab.kinoproject.repository.TypoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
    private CustomerRepository customerRepository;

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

    public void fixTypoNews(UUID id, UUID objectId, String status, UUID customerId) {
        Typo typo = getTypoRequired(id);
        News news = newsRepository.findById(objectId).orElseThrow(() ->
                new EntityNotFoundException(News.class, id));
        int count = (int) Arrays.stream(TypoStatus.values()).filter(s -> s.toString().equals(status)).count();
        if (count == 1) {
            news.setTextNews(typo.getRightText());
            List<Typo> typos = typoRepository
                    .findAllByErrorTextAndStatus(typo.getErrorText(), TypoStatus.OPEN);

            typos.forEach(typo1 -> {
                typo1.setStatus(Enum.valueOf(TypoStatus.class, status));
                typo1.setCustomer(customerRepository.findById(customerId).get());
                typoRepository.save(typo1);
            });
            newsRepository.save(news);
        } else {
            throw new EntityWrongStatusException(Typo.class, status);
        }
    }

    public void deleteTypo(UUID id) {
        typoRepository.delete(getTypoRequired(id));
    }
}
