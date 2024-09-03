package com.luckybird.springbackend.common.utils;

import com.luckybird.springbackend.common.base.UserInfo;
import org.springframework.stereotype.Component;

/**
 * 上下文工具类
 *
 * @author 新云鸟
 */

@Component
public class ContextUtils {
    private static final ThreadLocal<UserInfo> THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 设置上下文用户信息
     *
     * @param userInfo 用户信息
     */
    public static void setUserInfo(UserInfo userInfo) {
        THREAD_LOCAL.set(userInfo);
    }

    /**
     * 获取上下文用户信息
     *
     * @return 用户信息
     */
    public static UserInfo getUserInfo() {
        if (THREAD_LOCAL.get() == null) {
            THREAD_LOCAL.set(new UserInfo());
        }
        return THREAD_LOCAL.get();
    }

    /**
     * 移除上下文用户信息
     */
    public static void removeUserInfo() {
        THREAD_LOCAL.remove();
    }
}
