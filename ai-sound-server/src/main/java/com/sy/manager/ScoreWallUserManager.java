package com.sy.manager;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * @author lwb
 * @description TODO
 * @date 2024/8/28
 */
@Component
public class ScoreWallUserManager {
    private  static ThreadPoolTaskExecutor taskExecutor = SpringUtil.getBean("threadPoolTaskExecutor");

    public ThreadPoolTaskExecutor getUsertaskExecutor() {
        return this.taskExecutor;
    }
    @PreDestroy
    public void shutdown() {
        this.taskExecutor.shutdown();
    }
}
