package com.sy.common.config;

/**
 * @author zys
 * @description TODO
 * @date 2024/7/3
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ContextInspector implements CommandLineRunner {

    @Autowired
    private ApplicationContext context;

    @Override
    public void run(String... args) {
        String[] beans = context.getBeanNamesForType(GlobalFilter.class);
        for (String bean : beans) {
            System.out.println("GlobalFilter bean: " + bean);
        }
    }
}
