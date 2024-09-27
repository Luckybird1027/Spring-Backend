package com.luckybird.common.i18n.utils;

import com.luckybird.common.context.utils.ContextUtils;
import com.luckybird.common.i18n.other.StringResource;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * 字符串资源工具类
 *
 * @author 新云鸟
 */

@Component
public class StringResourceUtils {
    private static StringResource stringResource;

    @Resource
    public void setStringResource(StringResource stringResource) {
        StringResourceUtils.stringResource = stringResource;
    }

    /**
     * 获取字符串资源
     *
     * @param code 字符串资源编码
     * @return 字符串资源
     */
    public static String format(String code) {
        return stringResource.getMessage(code, null, code, ContextUtils.getLocale());
    }

    /**
     * 获取字符串资源
     *
     * @param code 字符串资源编码
     * @param args 参数
     * @return 字符串资源
     */
    public static String format(String code, Object[] args) {
        return stringResource.getMessage(code, args, code, ContextUtils.getLocale());
    }

    /**
     * 获取字符串资源
     *
     * @param code   字符串资源编码
     * @param locale 语言
     * @return 字符串资源
     */
    public static String format(String code, Locale locale) {
        return stringResource.getMessage(code, null, code, locale);
    }

    /**
     * 获取字符串资源
     *
     * @param code   字符串资源编码
     * @param args   参数
     * @param locale 语言
     * @return 字符串资源
     */
    public static String format(String code, Object[] args, Locale locale) {
        return stringResource.getMessage(code, args, code, locale);
    }

}