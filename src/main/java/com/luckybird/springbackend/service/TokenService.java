package com.luckybird.springbackend.service;

import com.luckybird.springbackend.api.vo.TokenVO;

/**
 * Token服务接口
 *
 * @author 新云鸟
 */
public interface TokenService {

    /**
     * TODO: 可抽成公共工具方法
     * 从请求头中提取token
     * @param rawToken 请求头中的token
     * @return token
     */
    String extractToken(String rawToken);

    /**
     * 生成token
     * @param userId 用户id
     * @return TokenVO
     */
    TokenVO generateToken(Long userId);

    /**
     * 根据用户id删除token
     * @param userId 用户id
     * @return 删除结果 （true：存在，删除成功，false：不存在，删除失败）
     */
    boolean deleteTokenByUserId(Long userId);

    /**
     * 验证token
     * @param accessToken 用于验证的token
     * @return 验证结果
     */
    TokenVO verifyToken(String accessToken);

    /**
     * 根据用户id查询token
     * @param userId 用户id
     * @return accessToken
     */
    String findTokenByUserId(Long userId);


}
