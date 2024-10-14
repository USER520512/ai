package com.hx.common.security.handler;

import com.alibaba.fastjson2.JSON;
import com.hx.common.util.AESUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.hx.common.constants.HttpStatus.UNAUTHORIZED;

/**
 * @author zys
 * @description 访问拒绝后执行的处理
 * @date 2024/7/10
 */
@Slf4j
@Component
public class DefaultAccessDeniedHandler implements ServerAccessDeniedHandler {

    @Value("${ad.gateway.secretKey}")
    private String secretKey;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.FORBIDDEN);
        response.getHeaders().add("Content-Type", "application/json; charset=UTF-8");
        String encryptedResponse;
        try {
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("code", UNAUTHORIZED);
            responseMap.put("message", "未授权禁止访问");
            responseMap.put("data", null);
            encryptedResponse = AESUtils.encrypt(JSON.toJSONString(responseMap), secretKey);
            log.error("[DefaultAccessDeniedHandler] handle  path={}", exchange.getRequest().getPath());
        } catch (Exception e) {
            log.error("DefaultAccessDeniedHandler handle encrypt response data", e);
            throw new RuntimeException("DefaultAccessDeniedHandler handle encrypt response data");
        }
        byte[] encryptedContent = encryptedResponse.getBytes(StandardCharsets.UTF_8);
        DataBuffer dataBuffer = response.bufferFactory().wrap(encryptedContent);
        return response.writeWith(Mono.just(dataBuffer));
    }

}
