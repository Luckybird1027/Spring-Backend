package com.luckybird.springbackend.interceptor;

import com.luckybird.springbackend.api.vo.TokenVO;
import com.luckybird.springbackend.api.vo.UserVO;
import com.luckybird.springbackend.common.annotation.NoAuth;
import com.luckybird.springbackend.common.util.ContextUtil;
import com.luckybird.springbackend.service.TokenService;
import com.luckybird.springbackend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 新云鸟
 */
@RequiredArgsConstructor
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final UserService userService;
    private final TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        NoAuth noAuth = handlerMethod.getMethodAnnotation(NoAuth.class);
        if (noAuth != null) {
            return true;
        }
        String token = request.getHeader("Authorization");
        TokenVO tokenVO = tokenService.verifyToken(token);
        if (tokenVO == null) {
            response.setStatus(401);
            return false;
        }
        UserVO user = userService.get(tokenVO.getUserId());
        if (user.getStatus() != 0){
            response.setStatus(401);
            return false;
        }
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
