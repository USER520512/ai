package com.hx.common.security;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @author zys
 * @description 其他的地址走后续验证
 * @date 2024/7/10
 */
@Component
public class DefaultAccessDecisionManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext object) {
        Mono<AuthorizationDecision> mono = authentication.map(auth -> {
            Object authResult = auth.getPrincipal();
            //数据读取非空，说明前期在auth的时候，jwt认证返回非空
            if (Objects.nonNull(authResult)) {
                return new AuthorizationDecision(true);
            }
            return new AuthorizationDecision(false);
        });
        return mono;
    }

}
