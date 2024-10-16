package com.sy.strategy.login;



import com.sy.domain.request.InitRequest;

import java.util.Map;

/**
 * @author lwb
 * @description 金币策略模式
 * @date 2024/7/26
 */

public interface UserLoginStrategy {

    Map<String,Object> login(InitRequest initRequest);
}
