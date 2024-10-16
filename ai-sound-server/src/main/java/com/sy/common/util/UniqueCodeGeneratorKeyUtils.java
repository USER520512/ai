package com.sy.common.util;

import com.hx.common.config.RedisConfig;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Random;

@Component
public class UniqueCodeGeneratorKeyUtils {

    @Resource
    private RedisConfig redisConfig;

    @Resource
    private RedisUtils redisUtils;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int CODE_LENGTH = 6;
    private Random random = new Random();

    public  String generateUniqueCode() {
        String code;
        do {
            StringBuilder sb = new StringBuilder(CODE_LENGTH);
            for (int i = 0; i < CODE_LENGTH; i++) {
                int index = random.nextInt(CHARACTERS.length());
                sb.append(CHARACTERS.charAt(index));
            }
            code = sb.toString();
        }  while (Objects.nonNull(redisUtils.getMapValue(redisConfig.scRedisTemplate(), "bind_codes", code)));
        redisUtils.setMapValue(redisConfig.scRedisTemplate(), "bind_codes", code, "1");
        return code;
    }

}
