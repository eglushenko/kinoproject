package com.solvve.lab.kinoproject.service;


import com.solvve.lab.kinoproject.domain.Customer;
import com.solvve.lab.kinoproject.domain.Review;
import com.solvve.lab.kinoproject.domain.Typo;
import com.solvve.lab.kinoproject.repository.RepositoryHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@Slf4j
public class CustomerNotifyService {

    @Autowired
    private RepositoryHelper repositoryHelper;

    private void emailSender(String email) {
        log.info("send email to {}", email);
        // EmailSender loghic
    }

    public void notifyOnTypoStatusChangedToClosed(UUID id) {
        if (id != null) {
            UUID customer = repositoryHelper.getReferenceIfExist(Typo.class, id).getCustomer().getId();
            String email = repositoryHelper.getReferenceIfExist(Customer.class, customer).getEmail();
            emailSender(email);
        }

    }

    public void notifyOnReviewStatusChangedToPublished(UUID id) {
        if (id != null) {
            UUID customer = repositoryHelper.getReferenceIfExist(Review.class, id).getCustomer().getId();
            String email = repositoryHelper.getReferenceIfExist(Customer.class, customer).getEmail();
            emailSender(email);
        }
    }

}
