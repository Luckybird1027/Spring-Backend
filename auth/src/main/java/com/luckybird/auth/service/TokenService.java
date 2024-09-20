package com.luckybird.auth.service;

import com.luckybird.auth.base.TokenInfo;
import com.luckybird.common.base.UserInfo;

/**
 * Token服务接口
 *
 * @author 新云鸟
 */
public interface TokenService {

    /**
     * 根据用户id生成token，
     * redis创建(accessToken → tokenInfo)和(userId → userInfo)键值对
     *
     * @param userId 用户id
     * @return Token信息
     */
    TokenInfo generateToken(Long userId, UserInfo userInfo);

    /**
     * 根据用户id删除token，
     * redis删除(userId → userInfo)键值对
     *
     * @param userId 用户id
     * @return 删除结果 （true：存在，删除成功，false：不存在，删除失败）
     */
    boolean deleteTokenByUserId(Long userId);

    /**
     * 验证token是否有效,
     * redis分别验证(accessToken → tokenInfo)和(userId → userInfo)键值对是否存在
     *
     * @param token 用于验证的token
     * @return 验证结果
     */
    UserInfo verifyToken(String token);

}
