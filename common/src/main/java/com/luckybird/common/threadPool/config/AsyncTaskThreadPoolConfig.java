package com.luckybird.common.threadPool.config;

import com.luckybird.common.threadPool.decorator.ContextCopyDecorator;
import com.luckybird.common.threadPool.properties.ThreadPoolProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步任务线程池配置
 *
 * @author 新云鸟
 */
@Configuration
@EnableConfigurationProperties(ThreadPoolProperties.class)
public class AsyncTaskThreadPoolConfig {

    @Bean
    public TaskExecutor asyncTaskExecutor(ThreadPoolProperties properties) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        // 最大线程数
        executor.setMaxPoolSize(properties.getMaxPoolSize());
        // 核心线程数
        executor.setCorePoolSize(properties.getCorePoolSize());
        // 队列容量
        executor.setQueueCapacity(properties.getQueueCapacity());
        // 线程存活时间
        executor.setKeepAliveSeconds(properties.getKeepAliveSeconds());
        // 线程名字前缀
        executor.setThreadNamePrefix(properties.getThreadNamePrefix());
        // 关机时等待任务完成
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 线程池对拒绝任务的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 任务装饰器
        executor.setTaskDecorator(new ContextCopyDecorator());

        executor.initialize();
        return executor;
    }


}
