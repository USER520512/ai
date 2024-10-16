package com.sy.strategy.login;


import com.sy.common.constants.LoginType;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lwb
 * @description 获取策略工厂
 * @date 2024/7/26
 */
@Component
public class UserLoginStrategyFactory {


    @Resource
    private PasswordStrategy passwordStrategy;

    @Resource
    private VisitorStrategy visitorStrategy;

    //放到这个Map
    private final Map<String, UserLoginStrategy> loginStrategyHashMap = new HashMap<>();
    @PostConstruct
    public void init() {
        loginStrategyHashMap.put(LoginType.EMAIL, passwordStrategy);
        loginStrategyHashMap.put(LoginType.VISITOR, visitorStrategy);
    }
    public UserLoginStrategy getStrategy(String reason) {
        return loginStrategyHashMap.get(reason);
    }
}
