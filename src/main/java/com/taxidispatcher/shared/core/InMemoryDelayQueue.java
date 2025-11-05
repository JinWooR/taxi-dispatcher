package com.taxidispatcher.shared.core;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class InMemoryDelayQueue {
    private final TaskScheduler taskScheduler;

    public void schedule(Runnable task, long seconds) {
        taskScheduler.schedule(task, Instant.now().plus(Duration.ofSeconds(seconds)));
    }
}
