package com.luckybird.common.threadPool.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 线程池配置属性类
 *
 * @author 新云鸟
 */

@Data
@ConfigurationProperties(prefix = "thread-pool")
public class ThreadPoolProperties {

    /**
     * 最大线程数
     */
    int maxPoolSize = Runtime.getRuntime().availableProcessors();

    /**
     * 核心线程数
     */
    int corePoolSize = Runtime.getRuntime().availableProcessors() * 2;

    /**
     * 队列容量
     */
    int queueCapacity = 100;

    /**
     * 线程存活时间
     */
    int keepAliveSeconds = 60;

    /**
     * 线程名称前缀
     */
    String threadNamePrefix = "Thread-";

}
