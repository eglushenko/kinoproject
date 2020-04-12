package com.solvve.lab.kinoproject.event.listener;

import com.solvve.lab.kinoproject.enums.TypoStatus;
import com.solvve.lab.kinoproject.event.TypoStatusChangedEvent;
import com.solvve.lab.kinoproject.service.CustomerNotifyService;
import org.junit.Assert;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import static org.mockito.ArgumentMatchers.any;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class NotifyCustomerOnChangeStatusTypoListenerTest {

    @MockBean
    private CustomerNotifyService customerNotifyService;

    @SpyBean
    private NotifyCustomerOnChangeStatusTypoListener notifyCustomerOnChangeStatusTypoListener;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Test
    public void testOnEvent() {
        TypoStatusChangedEvent event = new TypoStatusChangedEvent();
        event.setTypoId(UUID.randomUUID());
        event.setNewStatus(TypoStatus.CLOSED);
        applicationEventPublisher.publishEvent(event);

        Mockito.verify(notifyCustomerOnChangeStatusTypoListener, Mockito.timeout(500)).onEvent(event);
        Mockito.verify(customerNotifyService, Mockito.timeout(500))
                .notifyOnTypoStatusChangedToClosed(event.getTypoId());
    }

    @Test
    public void testEventIfNotClosed() {
        for (TypoStatus typoStatus : TypoStatus.values()) {
            if (typoStatus == TypoStatus.CLOSED) {
                continue;
            }

            TypoStatusChangedEvent event = new TypoStatusChangedEvent();
            event.setTypoId(UUID.randomUUID());
            event.setNewStatus(typoStatus);
            applicationEventPublisher.publishEvent(event);

            Mockito.verify(notifyCustomerOnChangeStatusTypoListener, Mockito.never()).onEvent(any());
            Mockito.verify(customerNotifyService, Mockito.never()).notifyOnTypoStatusChangedToClosed(any());
        }
    }

    @Test
    public void testEventAsync() throws InterruptedException {
        TypoStatusChangedEvent event = new TypoStatusChangedEvent();
        event.setTypoId(UUID.randomUUID());
        event.setNewStatus(TypoStatus.CLOSED);

        List<Integer> checkList = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);
        Mockito.doAnswer(invocationOnMock -> {
            Thread.sleep(500);
            checkList.add(2);
            latch.countDown();
            return null;
        }).when(customerNotifyService).notifyOnTypoStatusChangedToClosed(event.getTypoId());

        applicationEventPublisher.publishEvent(event);
        checkList.add(1);

        latch.await();
        Mockito.verify(notifyCustomerOnChangeStatusTypoListener).onEvent(event);
        Mockito.verify(customerNotifyService).notifyOnTypoStatusChangedToClosed(event.getTypoId());
        Assert.assertEquals(Arrays.asList(1, 2), checkList);
    }
}