package com.luckybird.springbackend.common.util;

import com.luckybird.springbackend.common.base.UserInfo;

/**
 * 上下文工具类
 *
 * @author 新云鸟
 */

public class ContextUtil {
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
     * 设置上下文用户ID
     *
     * @param id 用户ID
     */
    public static void setUserId(Long id) {
        UserInfo userInfo = THREAD_LOCAL.get();
        if (userInfo == null) {
            userInfo = new UserInfo();
            THREAD_LOCAL.set(userInfo);
        }
        userInfo.setId(id);
    }

    /**
     * 获取上下文用户ID
     *
     * @return 用户ID
     */
    public static Long getUserId() {
        UserInfo userInfo = THREAD_LOCAL.get();
        return userInfo != null ? userInfo.getId() : null;
    }

    /**
     * 移除上下文用户ID
     */
    public static void removeUserId() {
        THREAD_LOCAL.get().setId(null);
        // 如果THREAD_LOCAL中的UserInfo的所有字段值为空，则直接移除上下文UserInfo
        if (THREAD_LOCAL.get().isEmpty()) {
            THREAD_LOCAL.remove();
        }
    }

    /**
     * 移除上下文用户信息
     */
    public static void removeUserInfo() {
        THREAD_LOCAL.remove();
    }
}
