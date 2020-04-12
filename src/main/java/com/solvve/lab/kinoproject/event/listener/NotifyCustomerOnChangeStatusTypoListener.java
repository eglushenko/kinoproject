package com.solvve.lab.kinoproject.event.listener;


import com.solvve.lab.kinoproject.event.TypoStatusChangedEvent;
import com.solvve.lab.kinoproject.service.CustomerNotifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotifyCustomerOnChangeStatusTypoListener {

    @Autowired
    private CustomerNotifyService customerNotifyService;

    @Async
    @EventListener(condition = "#event.newStatus == T(com.solvve.lab.kinoproject.enums.TypoStatus).CLOSED")
    public void onEvent(TypoStatusChangedEvent event) {
        log.info("handling {}", event);
        customerNotifyService.notifyOnTypoStatusChangedToClosed(event.getTypoId());
    }
}
