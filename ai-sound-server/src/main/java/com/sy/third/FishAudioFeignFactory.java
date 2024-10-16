package com.sy.third;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class FishAudioFeignFactory implements FallbackFactory<FishAudioRemoteClient> {
    private final FishAudioRemoteClient fishAudioFeignFallback;

    public FishAudioFeignFactory(FishAudioFeignFallback fishAudioFeignFallback) {
        this.fishAudioFeignFallback = fishAudioFeignFallback;
    }

    @Override
    public FishAudioRemoteClient create(Throwable cause) {
        if (Objects.nonNull(cause.getCause())){
            log.error("远程调用FishAudioAPI发生错误:", cause.getCause().getMessage(),cause);
        }
        return this.fishAudioFeignFallback;
    }
}
