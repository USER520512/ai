package com.sy.common.config;

/**
 * @author zys
 * @description TODO
 * @date 2024/7/10
 */

import com.sy.common.enums.UrlEnum;
import com.sy.common.security.DefaultAccessDecisionManager;
import com.sy.common.security.DefaultAuthManager;
import com.sy.common.security.context.DefaultSecurityContext;
import com.sy.common.security.handler.DefaultAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.filter.CorsFilter;


@EnableWebFluxSecurity
public class SecurityConfig {

    @Autowired
    private PermitUrlConfig permitUrlConfig;

    @Autowired
    private DefaultSecurityContext defaultSecurityContext;

    @Autowired
    private DefaultAuthManager defaultAuthManager;

    @Autowired
    private DefaultAccessDecisionManager defaultAccessDecisionManager;

    @Autowired
    private DefaultAccessDeniedHandler defaultAccessDeniedHandler;

    /**
     * 跨域过滤器
     */
    @Autowired
    private CorsFilter corsFilter;

    /**
     * 访问权限授权
     *
     * @param http
     * @return
     */
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        String initialize = "/engine/user/*" + UrlEnum.INITIALIZE.getEncrypt() + "*";
        String login = "/score/user/*" + UrlEnum.LOGIN.getEncrypt() + "*";
        String config = "/score/user/*" + UrlEnum.CONFIG.getEncrypt() + "*";
        //禁用csrf
        http.csrf().disable()
                //认证启动
                .securityContextRepository(defaultSecurityContext)
                //认证管理
                .authenticationManager(defaultAuthManager)
                //请求拦截处理
                .authorizeExchange(exchange -> exchange
                        //默认放开的地址
                        .pathMatchers("/engine/user/initialize", "/score/user/login", "/score/user/config",initialize,login,config,"/score/payout/rrUrl").permitAll()
                        //放开的请求方法
                        .pathMatchers(HttpMethod.OPTIONS).permitAll()
                        //其他的地址走后续验证
                        .anyExchange().access(defaultAccessDecisionManager)
                )
                //访问拒绝后执行的处理
                .exceptionHandling().accessDeniedHandler(defaultAccessDeniedHandler);
        return http.build();
    }

}

