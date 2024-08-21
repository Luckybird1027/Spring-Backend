package com.luckybird.springbackend.interceptor;

import com.luckybird.springbackend.exception.BizException;
import com.luckybird.springbackend.exception.ExceptionMessages;
import com.luckybird.springbackend.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 新云鸟
 */
@RequiredArgsConstructor
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String rawToken = request.getHeader("Authorization");
        String accessToken = tokenService.extractToken(rawToken);
        if (tokenService.verifyToken(accessToken) == null) {
            throw new BizException(ExceptionMessages.UNAUTHORIZED_ACCESS);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
