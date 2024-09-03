package com.luckybird.springbackend.common.utils;

import jakarta.annotation.Resource;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * 字符串资源工具类
 *
 * @author 新云鸟
 */

@Component
public class StringResourceUtils {
    private static MessageSource messageSource;

    /**
     * 获取字符串资源
     *
     * @param code 字符串资源编码
     * @return 字符串资源
     */
    public static String format(String code) {
        return messageSource.getMessage(code, null, Locale.getDefault());
    }

    /**
     * 获取字符串资源
     *
     * @param code 字符串资源编码
     * @param args 参数
     * @return 字符串资源
     */
    public static String format(String code, Object[] args) {
        return messageSource.getMessage(code, args, Locale.getDefault());
    }

    /**
     * 获取字符串资源
     *
     * @param code   字符串资源编码
     * @param locale 语言
     * @return 字符串资源
     */
    public static String format(String code, Locale locale) {
        return messageSource.getMessage(code, null, locale);
    }

    @Resource
    public void setMessageSource(MessageSource messageSource) {
        StringResourceUtils.messageSource = messageSource;
    }
}