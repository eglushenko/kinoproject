package com.solvve.lab.kinoproject.service;

import com.solvve.lab.kinoproject.BaseTest;
import com.solvve.lab.kinoproject.domain.Customer;
import com.solvve.lab.kinoproject.domain.Review;
import com.solvve.lab.kinoproject.domain.Typo;
import com.solvve.lab.kinoproject.enums.ReviewStatus;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.repository.CustomerRepository;
import com.solvve.lab.kinoproject.repository.ReviewRepository;
import com.solvve.lab.kinoproject.repository.TypoRepository;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.UUID;

public class CustomerNotifyServiceTest extends BaseTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TypoRepository typoRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @SpyBean
    private CustomerNotifyService customerNotifyService;

    private Typo createTypo() {
        Typo typo = generateFlatEntityWithoutId(Typo.class);
        return typoRepository.save(typo);
    }

    private Customer createCustomer() {
        Customer customer = generateFlatEntityWithoutId(Customer.class);
        return customerRepository.save(customer);
    }

    private Review createReview() {
        Review review = generateFlatEntityWithoutId(Review.class);
        return reviewRepository.save(review);
    }


    @Test
    public void testNotifyUserTypoChangeStatusErrorTypoId() {
        UUID typo = UUID.randomUUID();
        Mockito.doThrow(new EntityNotFoundException(Typo.class, typo))
                .when(customerNotifyService).notifyOnTypoStatusChangedToClosed(typo);

    }

    @Test
    public void testNotifyUserTypoChangeStatus() {
        Customer user = createCustomer();
        Typo typo = createTypo();
        typo.setUserId(user.getId());
        typo = typoRepository.save(typo);
        customerNotifyService.notifyOnTypoStatusChangedToClosed(typo.getId());
        Mockito.verify(customerNotifyService, Mockito.times(1))
                .notifyOnTypoStatusChangedToClosed(typo.getId());

    }

    @Test
    public void testNotifyUserTypoChangeStatusErrorUserId() {
        UUID user = UUID.randomUUID();
        Typo typo = createTypo();
        typo.setUserId(user);
        typo = typoRepository.save(typo);
        Mockito.doThrow(new EntityNotFoundException(Customer.class, user))
                .when(customerNotifyService).notifyOnTypoStatusChangedToClosed(typo.getId());

    }

    @Test
    public void testNotifyUserReviewChangeStatusErrorReviewId() {
        UUID r = UUID.randomUUID();
        Mockito.doThrow(new EntityNotFoundException(Review.class, r))
                .when(customerNotifyService).notifyOnReviewStatusChangedToPublished(r);

    }

    @Test
    public void testNotifyUserReviewChangeStatus() {
        Customer customer = createCustomer();
        Review review = createReview();
        review.setCustomer(customer);
        review.setStatus(ReviewStatus.PUBLISHED);
        review = reviewRepository.save(review);
        customerNotifyService.notifyOnReviewStatusChangedToPublished(review.getId());
        Mockito.verify(customerNotifyService, Mockito.times(1))
                .notifyOnReviewStatusChangedToPublished(review.getId());

    }

}