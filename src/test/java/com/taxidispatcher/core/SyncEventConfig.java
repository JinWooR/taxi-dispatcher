package com.taxidispatcher.core;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

@TestConfiguration
public class SyncEventConfig {

    @Bean
    public TaskExecutor taskExecutor() {
        return new SyncTaskExecutor(); // 동기 실행
    }
}
