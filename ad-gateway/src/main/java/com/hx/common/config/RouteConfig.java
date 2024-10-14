//package com.hx.common.config;
//
//import com.hx.filter.LoggingFilter;
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @author zys
// * @description 添加路由规则
// * @date 2024/7/3
// */
//@Configuration
//public class RouteConfig {
//    @Bean
////    @Order(1)
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, LoggingFilter loggingFilter) {
//        return builder.routes()
//                .route("ad-engine", r -> r.path("/engine/**")
//                        .filters(f -> {
//                            f.stripPrefix(1);
//                            f.filter(loggingFilter.apply(new LoggingFilter.Config())); // 添加自定义日志过滤器
//                            return f;
//                        })
//                        .uri("http://localhost:9082"))
//                .route("ad-engine-2", r -> r.path("/engidd/**")
//                        .filters(f -> {
//                            f.stripPrefix(1);
//                            f.filter(loggingFilter.apply(new LoggingFilter.Config())); // 添加自定义日志过滤器
//                            return f;
//                        })
//                        .uri("http://localhost:9083"))
//                .build();
//    }
//}
//
