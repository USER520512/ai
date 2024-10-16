package com.sy.strategy.login;

import cn.hutool.core.bean.BeanUtil;

import com.sy.common.enums.StatusEnum;
import com.sy.common.util.GenerateKeyUtils;
import com.sy.common.util.IpUtils;
import com.sy.common.util.UniqueCodeGeneratorKeyUtils;
import com.sy.domain.model.AppUser;
import com.sy.domain.request.InitRequest;
import com.sy.manager.ScoreWallUserManager;
import com.sy.service.AppUserService;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.sy.common.constants.Constants.DEFAULT_PASSWORD;
import static com.sy.common.constants.Constants.VISITOR_USER_NAME;


/**
 * @author lwb
 * @description
 * @date 2024/7/29
 */
@Service
public class VisitorStrategy implements UserLoginStrategy{
    @Resource
    private AppUserService appUserService;

    @Resource
    private ScoreWallUserManager scoreWallUserManager;
    @Resource
    private TransactionTemplate transactionTemplate;
    @Override
    public Map<String,Object> login(InitRequest initRequest) {
        HashMap<String, Object> respMap = new HashMap<>();
        //游客模式
        initRequest.setUserName(VISITOR_USER_NAME);
        initRequest.setUserEmail(VISITOR_USER_NAME);
        ThreadPoolTaskExecutor usertaskExecutor = scoreWallUserManager.getUsertaskExecutor();
        //谷歌登录流程
        String loginIp = IpUtils.getIpAddr();
        AppUser appUser = new AppUser();
        BeanUtil.copyProperties(initRequest, appUser);
        appUser.setStatus(StatusEnum.ENABLE.getStatus())
                .setLoginIp(loginIp)
                .setLoginTime(new Date());
            appUser.setCreateTime(new Date());
            //生成一个随机ID
            String userId = GenerateKeyUtils.generateUserId();
            appUser.setUserId(userId);
            appUser.setPassword(DEFAULT_PASSWORD);
        // 使用 TransactionTemplate 管理事务
         transactionTemplate.execute(status -> {
            try {
                CompletableFuture<Void> async = CompletableFuture.runAsync(() -> {
                    appUserService.saveOrUpdate(appUser);
                }, usertaskExecutor);
                CompletableFuture<Void> future = CompletableFuture.allOf(async);
                future.join();
                // 返回用户ID
                return appUser.getUserId();
            }catch (Exception e){
                status.setRollbackOnly();
                throw new RuntimeException(e.getCause().getMessage());
            }
        });
        respMap.put("userId",userId);
        respMap.put("isNewUser", Boolean.TRUE);
        return respMap;
    }
}
