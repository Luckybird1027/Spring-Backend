package com.luckybird.springbackend.service;

import com.luckybird.springbackend.api.vo.TokenVO;
import com.luckybird.springbackend.api.vo.UserVO;

/**
 * Token服务接口
 *
 * @author 新云鸟
 */
public interface TokenService {
    /**
     * 生成token
     * @param userVO 用户登录请求
     * @return TokenVO
     */
    TokenVO generateToken(UserVO userVO);

    /**
     * 验证token
     * @param accessToken 用于验证的token
     * @return 验证结果
     */
    boolean verifyToken(String accessToken);
}
