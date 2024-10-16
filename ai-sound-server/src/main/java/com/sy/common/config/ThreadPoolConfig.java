package com.sy.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author lwb
 * @description TODO
 * @date 2024/8/28
 */
@Configuration
public class ThreadPoolConfig {
    private int corePoolSize = 5;
    private int maxPoolSize = 100;
    private int queueCapacity = 1000;
    private int keepAliveSeconds = 300;

    public ThreadPoolConfig() {
    }

    @Bean(
            name = {"threadPoolTaskExecutor"}
    )
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(this.maxPoolSize);
        executor.setCorePoolSize(this.corePoolSize);
        executor.setQueueCapacity(this.queueCapacity);
        executor.setKeepAliveSeconds(this.keepAliveSeconds);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }
}
