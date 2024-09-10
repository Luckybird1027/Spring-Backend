package com.luckybird.common.interceptor;

import com.luckybird.common.utils.ContextUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;

/**
 * 用户鉴权拦截器
 *
 * @author 新云鸟
 */
@RequiredArgsConstructor
@Component
public class LocaleInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // 从请求头中获取语言设置并存入上下文
        String stringLocale = request.getHeader("Accept-Language");
        if (StringUtils.hasText(stringLocale)) {
            String[] split = stringLocale.split("_");
            Locale locale = Locale.of(split[0], split[1]);
            ContextUtils.setLocale(locale);
        } else {
            ContextUtils.setLocale(Locale.getDefault());
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ContextUtils.removeLocale();
    }
}
