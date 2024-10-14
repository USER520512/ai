package com.hx.common.config;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zys
 * @description 定义放开配置的URL
 * @date 2024/7/10
 */
@Component
public class PermitUrlConfig {

    public static final String CONTEXT_PATH = "/gateway";

    /**
     * 需要访问的url
     */
    private final String[] permitUrl = {
            "/actuator/**",
            "/api-account/account/**"
    };

    private final String[] heartbeatUrl = {
            "/heartbeat/**",
    };

    /**
     * 额外放开权限的url
     *
     * @param urls 自定义的url
     * @return 自定义的url和监控中心需要访问的url集合
     */
    public String[] permit(String... urls) {
        Set<String> set = new HashSet<>();
        if (urls.length > 0) {
            Collections.addAll(set, addContextPath(urls));
        }

        //放开权限的地址
        Collections.addAll(set, addContextPath(permitUrl));
        Collections.addAll(set, heartbeatUrl);

        return set.toArray(new String[0]);
    }

    /**
     * 地址加访问前缀
     *
     * @return
     */
    private String[] addContextPath(String[] urls) {
        for (int i = 0; i < urls.length; i++) {
            urls[i] = CONTEXT_PATH + urls[i];
        }

        return urls;
    }
}
