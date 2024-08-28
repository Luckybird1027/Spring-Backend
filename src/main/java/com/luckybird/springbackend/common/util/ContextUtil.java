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
     * 设置上下文用户ID
     * @param id 用户ID
     */
    public static void setUserId(Long id) {
        THREAD_LOCAL.get().setId(id);
    }

    /**
     * 获取上下文用户ID
     * @return 用户ID
     */
    public static Long getUserId() {
        return THREAD_LOCAL.get().getId();
    }

    /**
     * 移除上下文用户ID
     */
    public static void removeUserId(){
        THREAD_LOCAL.get().setId(null);
        // 如果THREAD_LOCAL中的UserInfo的所有字段值为空，则直接移除上下文UserInfo
        if (THREAD_LOCAL.get().getId() == null){
            THREAD_LOCAL.remove();
        }
    }

    /**
     * 直接移除上下文UserInfo
     * 当前线程进程结束前必须执行，防止内存泄露
     */
    public static void removeUserInfo(){
        THREAD_LOCAL.remove();
    }
}
