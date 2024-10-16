package com.sy.common.security.context;

import com.sy.common.security.DefaultAuthManager;
import com.sy.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author zys
 * @description 启动认证
 * @date 2024/7/10
 */
@Component
@Slf4j
public class DefaultSecurityContext implements ServerSecurityContextRepository {
    @Autowired
    private DefaultAuthManager defaultAuthManager;

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String token = request.getHeaders().getFirst("Authorization");
        log.info("Authorization  load:{}",token);
        if (StringUtils.isNotEmpty(token)) {
            //调用defaultAuthManager的方法
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(token, null);
            Mono<Authentication> authenticationMono = defaultAuthManager.authenticate(authenticationToken);

            return authenticationMono.map(SecurityContextImpl::new);
        }
        return Mono.empty();
    }
}
