package com.luckybird.springbackend.interceptor;

import com.luckybird.springbackend.common.annotation.NoAuth;
import com.luckybird.springbackend.common.base.UserInfo;
import com.luckybird.springbackend.common.util.ContextUtil;
import com.luckybird.springbackend.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 用户鉴权拦截器
 *
 * @author 新云鸟
 */
@RequiredArgsConstructor
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // 检查是否有 @NoAuth 注解
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        NoAuth noAuth = handlerMethod.getMethodAnnotation(NoAuth.class);
        if (noAuth != null) {
            return true;
        }

        // 从请求头中获取 token 并验证
        String token = request.getHeader("Authorization");
        UserInfo userInfo = tokenService.verifyToken(token);
        if (userInfo == null) {
            response.setStatus(401);
            return false;
        }

        // 将用户信息存入上下文
        ContextUtil.setUserInfo(userInfo);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ContextUtil.removeUserInfo();
    }
}
