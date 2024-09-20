package com.luckybird.common.i18n.interceptor;

import com.luckybird.common.context.utils.ContextUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;
import java.util.Set;

/**
 * 用户鉴权拦截器
 *
 * @author 新云鸟
 */
@RequiredArgsConstructor
@Component
public class LocaleInterceptor implements HandlerInterceptor {

    private final static Set<String> LOCALE_LIST = Set.of("zh_CN", "en_US");

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        // 从请求头中获取语言设置并存入上下文
        String stringLocale = request.getHeader("Accept-Language");
        if (stringLocale == null) {
            ContextUtils.setLocale(Locale.getDefault());
        } else if (LOCALE_LIST.contains(stringLocale)) {
            String[] split = stringLocale.split("_");
            Locale locale = Locale.of(split[0], split[1]);
            ContextUtils.setLocale(locale);
        } else {
            ContextUtils.setLocale(Locale.getDefault());
        }
        return true;
    }

    @Override
    public void postHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, Exception ex) {
        ContextUtils.removeLocale();
    }
}
