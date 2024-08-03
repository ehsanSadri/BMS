package com.sadri.bms.common.config;

import jakarta.annotation.PreDestroy;
import lombok.AllArgsConstructor;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

@AllArgsConstructor
@Component
public class ExecutorServiceShutdown {

    private final ExecutorService executorService;

    @PreDestroy
    public void onApplicationEvent() {
        executorService.shutdown();
    }
}