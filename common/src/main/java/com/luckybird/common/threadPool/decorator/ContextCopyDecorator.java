package com.luckybird.common.threadPool.decorator;

import com.luckybird.common.base.UserInfo;
import com.luckybird.common.context.utils.ContextUtils;
import lombok.NonNull;
import org.springframework.core.task.TaskDecorator;

import java.util.Locale;

/**
 * 上下文拷贝装饰器
 *
 * @author 新云鸟
 */
public class ContextCopyDecorator implements TaskDecorator {

    @NonNull
    @Override
    public Runnable decorate(@NonNull Runnable runnable) {

        final UserInfo userInfo = ContextUtils.getUserInfo();
        final Locale locale = ContextUtils.getLocale();

        return () -> {
            try {
                ContextUtils.setUserInfo(userInfo);
                ContextUtils.setLocale(locale);
                runnable.run();
            } finally {
                ContextUtils.removeUserInfo();
                ContextUtils.removeLocale();
            }
        };
    }
}
