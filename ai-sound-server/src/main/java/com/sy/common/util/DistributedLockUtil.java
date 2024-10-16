package com.sy.common.util;


import com.sy.common.config.RedisConfig;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author lwb
 * @description
 * @date 2024/9/4
 */

@Component
public class DistributedLockUtil {

    @Resource
    private RedisConfig redisConfig;


    /**
     * 尝试获取分布式锁
     *
     * @param lockKey 锁的键
     * @param timeout 过期时间（单位：秒）
     * @return 返回锁的唯一标识符，如果获取失败则返回 null
     */
    public String tryLock(String lockKey, long timeout) {
        String lockValue = UUID.randomUUID().toString();
        Boolean success = redisConfig.scRedisTemplate().opsForValue().setIfAbsent(lockKey, lockValue, timeout, TimeUnit.SECONDS);
        return Boolean.TRUE.equals(success) ? lockValue : null;
    }

    /**
     * 释放分布式锁
     *
     * @param lockKey  锁的键
     * @param lockValue 锁的值
     * @return 是否成功释放锁
     */
    public boolean releaseLock(String lockKey, String lockValue) {
        String currentValue = (String) redisConfig.scRedisTemplate().opsForValue().get(lockKey);
        if (lockValue.equals(currentValue)) {
            return redisConfig.scRedisTemplate().delete(lockKey) != null;
        }
        return false;
    }
}
