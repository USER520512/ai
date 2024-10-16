package com.sy.filter;


import java.util.concurrent.atomic.AtomicReference;

public class LoginUserHolder {

    private static final AtomicReference<ThreadLocal<String>> threadLocalRef = new AtomicReference<>();

    private LoginUserHolder() {
        // 私有构造函数，防止外部实例化
    }

    /**
     * 获取单例的ThreadLocal实例
     *
     * @return ThreadLocal实例
     */
    public static ThreadLocal<String> getInstance() {
        while (true) {
            ThreadLocal<String> current = threadLocalRef.get();
            if (current != null) {
                return current;
            }
            ThreadLocal<String> newLocal = new ThreadLocal<>();
            if (threadLocalRef.compareAndSet(null, newLocal)) {
                return newLocal;
            }
        }
    }

    /**
     * 设置登录用户信息
     *
     * @param loginUser 登录用户信息
     */
    public static void setUserId(String loginUser) {
        getInstance().set(loginUser);
    }

    /**
     * 获取登录用户信息
     *
     * @return 登录用户信息
     */
    public static String getUserId() {
        return getInstance().get();
    }


    public static void clearUserId() {
        getInstance().remove();
    }
}
