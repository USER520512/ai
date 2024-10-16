package com.sy.strategy.login;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;

import com.sy.common.enums.StatusEnum;
import com.sy.common.util.IpUtils;
import com.sy.common.util.StringUtils;
import com.sy.common.util.UniqueCodeGeneratorKeyUtils;
import com.sy.domain.model.AppUser;
import com.sy.domain.request.InitRequest;
import com.sy.filter.LoginUserHolder;
import com.sy.manager.ScoreWallUserManager;
import com.sy.service.AppUserService;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;



/**
 * @author lwb
 * @description
 * @date 2024/7/29
 */
@Service
public class PasswordStrategy implements UserLoginStrategy{
    @Resource
    private AppUserService appUserService;
    @Resource
    private UniqueCodeGeneratorKeyUtils uniqueCodeGeneratorKeyUtils;

    @Resource
    private ScoreWallUserManager scoreWallUserManager;
    @Resource
    private TransactionTemplate transactionTemplate;
    @Override
    public Map<String,Object> login(InitRequest initRequest) {
        HashMap<String, Object> respMap = new HashMap<>();
        if (StringUtils.isEmpty(initRequest.getUserEmail())|| StringUtils.isEmpty(initRequest.getPassword())){
            throw new RuntimeException("The userEmail cannot be empty！");
        }
        //谷歌登录流程
        String loginIp = IpUtils.getIpAddr();
        AppUser appUser = new AppUser();
        BeanUtil.copyProperties(initRequest, appUser);
        appUser.setStatus(StatusEnum.ENABLE.getStatus())
                .setLoginIp(loginIp)
                .setLoginTime(new Date());
        LambdaUpdateWrapper<AppUser> updateWrapper = new LambdaUpdateWrapper();
        //谷歌用户校验谷歌唯一ID
        updateWrapper.eq(AppUser::getUserEmail, initRequest.getUserEmail());
        AppUser existAppUser = appUserService.getOne(updateWrapper);
        //登录流程
        ThreadPoolTaskExecutor usertaskExecutor = scoreWallUserManager.getUsertaskExecutor();
        if (Objects.nonNull(existAppUser)) {
            if (StatusEnum.ENABLE.getStatus().equals(existAppUser.getStatus())){
                if (!existAppUser.getPassword().equals(initRequest.getPassword())){
                    throw new RuntimeException("The password is incorrect！");
                }else {
                    respMap.put("userId",existAppUser.getUserId());
                    respMap.put("isNewUser", Boolean.FALSE);
                    return respMap;
                }
            }
            if (StatusEnum.WAIT_ACTIVE.getStatus().equals(existAppUser.getStatus())){
                String userId =  existAppUser.getUserId();
                appUser.setUserId(userId);
                if (StringUtils.isEmpty(initRequest.getPassword())){
                    throw new RuntimeException("The password cannot be empty！");
                }
                //激活用户，创建密码
                appUser.setPassword(initRequest.getPassword());
                //首次登录（注册）流程
                 appUser.setCreateTime(new Date());
                //异步编排
                transactionTemplate.execute(status -> {
                    try {
                        //生成用户绑定码
                        CompletableFuture<Void> thenAccept = CompletableFuture.supplyAsync(() -> {
                            return uniqueCodeGeneratorKeyUtils.generateUniqueCode();
                        },usertaskExecutor).thenAccept(code -> {
                            appUser.setBindCode(code);
                            appUserService.saveOrUpdate(appUser);
                        });
                        CompletableFuture<Void> future = CompletableFuture.allOf(thenAccept);
                        future.join();
                    }catch (Exception e){
                        status.setRollbackOnly();
                        throw new RuntimeException(e.getCause().getMessage());
                    }
                    return userId;
                });
                respMap.put("userId",userId);
                respMap.put("isNewUser", Boolean.TRUE);
                return respMap;
            }
        }else{
            String inheritUserId = LoginUserHolder.getUserId();
            if (StringUtils.isEmpty(inheritUserId)){
                throw new RuntimeException("The inheritUserId cannot be empty！");
            }
            //继承游客身份
            //继承
            LambdaUpdateWrapper<AppUser> wrapper = new LambdaUpdateWrapper();
            //谷歌用户校验谷歌唯一ID
            wrapper.eq(AppUser::getUserId, inheritUserId);
            AppUser inheritUser = appUserService.getOne(wrapper);
            if (Objects.isNull(inheritUser)) {
                throw new RuntimeException("The inheritUser is not exist！");
            }
            //关联邮箱和密码
            appUser.setUserEmail(initRequest.getUserEmail());
            appUser.setPassword(initRequest.getPassword());
            appUser.setUserId(inheritUserId);
            appUser.setId(inheritUser.getId());
            String userId = transactionTemplate.execute(status -> {
                try {
                    CompletableFuture.supplyAsync(() -> {
                        return uniqueCodeGeneratorKeyUtils.generateUniqueCode();
                    }, usertaskExecutor).thenAccept(code -> {
                        appUser.setBindCode(code);
                        appUserService.saveOrUpdate(appUser);
                    });
                } catch (Exception e) {
                    status.setRollbackOnly();
                    throw new RuntimeException(e.getCause().getMessage());
                }
                return appUser.getUserId();
            });
            respMap.put("userId",userId);
            respMap.put("isNewUser", Boolean.FALSE);
            return respMap;
        }
        return null;
    }
}
