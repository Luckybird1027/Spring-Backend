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
     * 根据用户id删除token
     * @param userId 用户id
     * @return 删除结果 （true：存在，删除成功，false：不存在，删除失败）
     */
    boolean deleteTokenByUserId(Long userId);

    /**
     * 验证token
     * @param token 用于验证的token
     * @return 验证结果
     */
    TokenVO verifyToken(String token);

    /**
     * 根据用户id查询token
     * @param userId 用户id
     * @return accessToken
     */
    String findTokenByUserId(Long userId);


}
