package com.solvve.lab.kinoproject.event.listener;

import com.solvve.lab.kinoproject.enums.ReviewStatus;
import com.solvve.lab.kinoproject.event.ReviewStatusChangedEvent;
import com.solvve.lab.kinoproject.service.CustomerNotifyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class NotifyCustomerOnChangeStatusReviewListenerTest {

    @MockBean
    private CustomerNotifyService customerNotifyService;

    @SpyBean
    private NotifyCustomerOnChangeStatusReviewListener notifyCustomerOnChangeStatusReviewListener;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Test
    public void testOnEvent() {
        ReviewStatusChangedEvent event = new ReviewStatusChangedEvent();
        event.setReviewId(UUID.randomUUID());
        event.setNewStatus(ReviewStatus.PUBLISHED);
        applicationEventPublisher.publishEvent(event);

        Mockito.verify(notifyCustomerOnChangeStatusReviewListener, Mockito.timeout(500)).onEvent(event);
        Mockito.verify(customerNotifyService, Mockito.timeout(500))
                .notifyOnReviewStatusChangedToPublished(event.getReviewId());
    }

    @Test
    public void testEventIfNotPublished() {
        for (ReviewStatus reviewStatus : ReviewStatus.values()) {
            if (reviewStatus == ReviewStatus.PUBLISHED) {
                continue;
            }

            ReviewStatusChangedEvent event = new ReviewStatusChangedEvent();
            event.setReviewId(UUID.randomUUID());
            event.setNewStatus(reviewStatus);
            applicationEventPublisher.publishEvent(event);

            Mockito.verify(notifyCustomerOnChangeStatusReviewListener, Mockito.never()).onEvent(any());
            Mockito.verify(customerNotifyService, Mockito.never()).notifyOnReviewStatusChangedToPublished(any());
        }
    }

}