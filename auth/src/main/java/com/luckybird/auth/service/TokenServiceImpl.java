package com.luckybird.auth.service;

import com.luckybird.auth.base.TokenInfo;
import com.luckybird.common.base.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Token服务实现
 *
 * @author 新云鸟
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final int EXPIRE_TIME = 900;

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public TokenInfo generateToken(Long userId, UserInfo userInfo) {
        UUID uuid = UUID.randomUUID();
        String accessToken = uuid.toString();
        TokenInfo tokenInfo = new TokenInfo(accessToken, userId);
        redisTemplate.opsForValue().set(accessToken, tokenInfo, EXPIRE_TIME, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(userId.toString(), userInfo, EXPIRE_TIME, TimeUnit.SECONDS);
        return tokenInfo;
    }

    @Override
    public boolean deleteTokenByUserId(Long userId) {
        UserInfo userInfo = (UserInfo) redisTemplate.opsForValue().get(userId.toString());
        if (userInfo == null) {
            return false;
        }
        redisTemplate.delete(userId.toString());
        return true;
    }

    @Override
    public UserInfo verifyToken(String token) {
        if (token == null) {
            return null;
        }
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        TokenInfo tokenInfo = (TokenInfo) redisTemplate.opsForValue().get(token);
        if (tokenInfo == null) {
            return null;
        }
        UserInfo userInfo = (UserInfo) redisTemplate.opsForValue().get(tokenInfo.getUserId().toString());
        if (userInfo == null) {
            return null;
        }
        redisTemplate.expire(token, EXPIRE_TIME, TimeUnit.SECONDS);
        redisTemplate.expire(tokenInfo.getUserId().toString(), EXPIRE_TIME, TimeUnit.SECONDS);
        return userInfo;
    }

}
