package com.hx.filter;

import com.hx.common.util.AESUtils;
import com.hx.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

import static com.hx.common.enums.UrlEnum.getUrlName;

/**
 * @author zys
 * @description 请求过滤器
 * @date 2024/7/4
 */
@Slf4j
@Component
public class HttpRequestFilter implements GlobalFilter, Ordered {

    @Value("${ad.gateway.secretKey}")
    private String secretKey;


    @Value("${spring.profiles.active}")
    private String activeProfile;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        log.info("Executing RequestDecryptionFilter");
        //判断如果是get请求则将路径进行替换处理
        if ("GET".equals(Objects.requireNonNull(request.getMethod()).name())) {
            ServerHttpRequest newRequest = request.mutate().path(getUrlName(request.getURI().getPath())).build();
            return chain.filter(exchange.mutate().request(newRequest).build());
        }
        if (request.getURI().getPath().contains("rrUrl")){
            return chain.filter(exchange);
        }
        if (!"POST".equals(Objects.requireNonNull(request.getMethod()).name())) {
            return chain.filter(exchange);
        }
        return DataBufferUtils.join(request.getBody())
                .flatMap(dataBuffer -> {
                    try {
                        // 获取请求参数
                        String originalRequestBody = getOriginalRequestBody(dataBuffer);
                        // 解密请求参数
                        String decryptRequestBody = decryptRequest(originalRequestBody);
                        // 装饰新的请求体
                        ServerHttpRequestDecorator requestDecorator = serverHttpRequestDecorator(request, decryptRequestBody);
                        // 使用新的请求和响应转发
                        ServerWebExchange serverWebExchange = exchange.mutate().request(requestDecorator).build();
                        // 放行拦截
                        return chain.filter(serverWebExchange);
                    } finally {
                        DataBufferUtils.release(dataBuffer);
                    }
                });
    }

    private ServerHttpRequestDecorator serverHttpRequestDecorator(ServerHttpRequest originalRequest, String decryptRequestBody) {
        return new ServerHttpRequestDecorator(originalRequest) {
            @Override
            public URI getURI() {
                String path = super.getURI().getPath();
        /*    注释加密逻辑    if (StringUtils.startsWithIgnoreCase(path, "/engine")) {
                    try {
                        log.debug("Original path: {}", path);
                        newPath = "/engine" + AESUtils.decrypt(super.getURI().getPath().substring(8), secretKey);
                        log.debug("New path: {}", newPath);
                    } catch (Exception e) {
                        log.error("Failed to decrypt path: {}", path, e);
                    }
                } else {
                    newPath = path;
                }*/
                String urlName = getUrlName(path);
                log.info("Original path: {},{}", path, urlName);
                return URI.create(super.getURI().toString().replace(path,urlName ));
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = super.getHeaders();
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.putAll(headers);
                httpHeaders.remove(HttpHeaders.CONTENT_LENGTH);
                httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
                List<String> deviceX = headers.get("device-x");
                //积分墙相关数据特殊处理
                if (!CollectionUtils.isEmpty(deviceX)){
                    String string = deviceX.get(0);
                    String decryptRequest = decryptRequest(string);
                    httpHeaders.set("device-x", decryptRequest);
                }
                return httpHeaders;
            }

            @Override
            public Flux<DataBuffer> getBody() {
                byte[] bytes = decryptRequestBody.getBytes(StandardCharsets.UTF_8);
                return Flux.just(new DefaultDataBufferFactory().wrap(bytes));
            }
        };
    }

    private String decryptRequest(String originalRequestBody) {
        String decryptedBody = "";
        try {
            log.info("Original request body: {}", originalRequestBody);
            decryptedBody = AESUtils.decrypt(originalRequestBody, secretKey);
            log.info("Decrypted request body: {}", decryptedBody);
        } catch (Exception e) {
            log.error("Failed to decrypt request body", e);
        }
        return decryptedBody;
    }

    private String getOriginalRequestBody(DataBuffer dataBuffer) {
        byte[] bytes = new byte[dataBuffer.readableByteCount()];
        dataBuffer.read(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    @Override
    public int getOrder() {
        return -222;
    }

}
