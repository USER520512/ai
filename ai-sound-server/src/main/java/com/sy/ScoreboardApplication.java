package com.sy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 启动程序
 *
 * @author zys
 */

@EnableAsync
@MapperScan("com.sy.mapper")
@SpringBootApplication(
        exclude = {
                RedisAutoConfiguration.class,
                RedisRepositoriesAutoConfiguration.class,
                RedisReactiveAutoConfiguration.class
        }
)
public class ScoreboardApplication {
    public static void main(String[] args) {
        SpringApplication.run(ScoreboardApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  APP启动成功   ლ(´ڡ`ლ)ﾞ ");
    }
}
