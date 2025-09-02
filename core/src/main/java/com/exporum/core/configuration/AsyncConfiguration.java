package com.exporum.core.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 8.
 * @description :
 */

@Slf4j
@Configuration
@EnableAsync
@EnableScheduling
public class AsyncConfiguration {


    @Value("${application.task.scheduler.core-size}")
    private int schedulerTaskCoreSize;

    @Value("${application.task.scheduler.max-size}")
    private int schedulerTaskSize;

    private void logExecutor(final String taskName, ThreadPoolTaskExecutor executor) {
        log.info("Executor.config: {} core.size {}, max.size {}, queue.capacity {}, keep.alive {}",
                taskName,
                executor.getCorePoolSize(),
                executor.getMaxPoolSize(),
                executor.getQueueCapacity(),
                executor.getKeepAliveSeconds());
    }

    @Bean("MailAsyncTask")
    public Executor schedulerExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(schedulerTaskCoreSize);
        if (schedulerTaskSize > 0) {
            executor.setMaxPoolSize(schedulerTaskSize);
        }
        executor.initialize();

        logExecutor("AsyncTask", executor);

        return executor;
    }

}
