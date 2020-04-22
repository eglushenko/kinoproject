package com.solvve.lab.kinoproject.service;


import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {

    @Async
    public void executeAsync(Runnable runnable) {
        runnable.run();
    }
}
