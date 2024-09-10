package com.luckybird.common.utils;

import com.luckybird.common.base.UserInfo;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * 上下文工具类
 *
 * @author 新云鸟
 */

@Component
public class ContextUtils {
    private static final ThreadLocal<UserInfo> USER_THREAD_LOCAL = new ThreadLocal<>();
    private static final ThreadLocal<Locale> LOCALE_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 设置上下文用户信息
     *
     * @param userInfo 用户信息
     */
    public static void setUserInfo(UserInfo userInfo) {
        USER_THREAD_LOCAL.set(userInfo);
    }

    /**
     * 获取上下文用户信息
     *
     * @return 用户信息
     */
    public static UserInfo getUserInfo() {
        if (USER_THREAD_LOCAL.get() == null) {
            USER_THREAD_LOCAL.set(new UserInfo());
        }
        return USER_THREAD_LOCAL.get();
    }

    /**
     * 移除上下文用户信息
     */
    public static void removeUserInfo() {
        USER_THREAD_LOCAL.remove();
    }

    /**
     * 设置上下文语言环境
     *
     * @param locale 语言环境
     */
    public static void setLocale(Locale locale) {
        LOCALE_THREAD_LOCAL.set(locale);
    }

    /**
     * 获取上下文语言环境
     *
     * @return 语言环境
     */
    public static Locale getLocale() {
        if (LOCALE_THREAD_LOCAL.get() == null) {
            LOCALE_THREAD_LOCAL.set(Locale.getDefault());
        }
        return LOCALE_THREAD_LOCAL.get();
    }

    /**
     * 移除上下文语言环境
     */
    public static void removeLocale() {
        LOCALE_THREAD_LOCAL.remove();
    }
}
