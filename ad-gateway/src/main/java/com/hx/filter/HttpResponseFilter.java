package com.hx.filter;

import com.hx.common.util.AESUtils;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @author zys
 * @description 响应过滤器
 * @date 2024/7/4
 */
@Component
public class HttpResponseFilter implements GlobalFilter, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponseFilter.class);
    @Value("${ad.gateway.secretKey}")
    private String secretKey;

    //获取环境
    @Value("${spring.profiles.active}")
    private String activeProfile;

    public HttpResponseFilter() {
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        logger.info("Executing ResponseEncryptionFilter");
        ServerHttpResponse originalResponse = exchange.getResponse();
        if ((exchange.getRequest().getURI().getPath()).contains("rrUrl")) {
            return chain.filter(exchange);
        }
        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                logger.info("writeWith called");
                logger.debug("Body type: " + body.getClass().getName());
                System.out.println(getStatusCode());
                System.out.println(body);
                if (getStatusCode().equals(HttpStatus.OK) && body instanceof Flux) {
                    logger.debug("Status is OK and body is Flux");
                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
                    return super.writeWith(fluxBody.buffer().map(dataBuffers -> {
                        DataBuffer joinedBuffer = originalResponse.bufferFactory().join(dataBuffers);
                        byte[] content = new byte[joinedBuffer.readableByteCount()];
                        joinedBuffer.read(content);
                        DataBufferUtils.release(joinedBuffer);

                        String responseData = new String(content, StandardCharsets.UTF_8);
                        logger.info("Original response data: " + responseData);
                        String encryptedResponse;
                        try {
                            encryptedResponse = AESUtils.encrypt(responseData, secretKey);
                        } catch (Exception e) {
                            logger.error("Failed to encrypt response data", e);
                            throw new RuntimeException("Failed to encrypt response data", e);
                        }

                        logger.info("Encrypted response data: " + encryptedResponse);
                        byte[] encryptedContent = encryptedResponse.getBytes(StandardCharsets.UTF_8);
                        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
                        DataBuffer encryptedBuffer = bufferFactory.wrap(encryptedContent);
                        originalResponse.getHeaders().setContentLength(encryptedContent.length);
                        return encryptedBuffer;
                    }));
                } else {
                    logger.error("Status is not OK or body is not Flux, proceeding with default behavior.");
                    return super.writeWith(body);
                }
            }

            @Override
            public Mono<Void> writeAndFlushWith(Publisher<? extends Publisher<? extends DataBuffer>> body) {
                logger.debug("writeAndFlushWith called");
                return writeWith(Flux.from(body).flatMapSequential(p -> p));
            }
        };

        logger.info("Decorated response created");
        return chain.filter(exchange.mutate().response(decoratedResponse).build());
    }

    @Override
    public int getOrder() {
        return -21;
    }
}
