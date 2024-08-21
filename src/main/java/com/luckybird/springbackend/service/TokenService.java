package com.luckybird.springbackend.service;

import com.luckybird.springbackend.api.vo.TokenVO;

/**
 * Token服务接口
 *
 * @author 新云鸟
 */
public interface TokenService {

    /**
     * 生成token
     * @param userId 用户id
     * @return TokenVO
     */
    TokenVO generateToken(Long userId);

    /**
     * 验证token
     * @param accessToken 用于验证的token
     * @return 验证结果
     */
    TokenVO verifyToken(String accessToken);

}
