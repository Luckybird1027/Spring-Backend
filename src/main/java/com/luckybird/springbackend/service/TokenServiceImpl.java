package com.luckybird.springbackend.service;

import com.luckybird.springbackend.api.vo.TokenVO;
import com.luckybird.springbackend.exception.BizException;
import com.luckybird.springbackend.exception.ExceptionMessages;
import com.luckybird.springbackend.po.TokenPO;
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

    private final RedisTemplate<String, TokenPO> tokenRedisTemplate;
    private final RedisTemplate<String, String> stringRedisTemplate;

    private TokenVO toVO(TokenPO tokenpo, int expireTime) {
        TokenVO tokenVO = new TokenVO();
        tokenVO.setAccessToken(tokenpo.getAccessToken());
        tokenVO.setUserId(tokenpo.getUserId());
        tokenVO.setExpireTime(expireTime);
        return tokenVO;
    }

    @Override
    public String extractToken(String rawToken){
        if (rawToken == null || !rawToken.startsWith("Bearer ")) {
            throw new BizException(ExceptionMessages.UNAUTHORIZED_ACCESS);
        }
       return rawToken.substring(7);
    }

    @Override
    public TokenVO generateToken(Long userId) {
        UUID uuid = UUID.randomUUID();
        String accessToken = uuid.toString();
        int expireTime = 900;
        TokenPO token = new TokenPO(accessToken, userId);
        tokenRedisTemplate.opsForValue().set(accessToken, token, expireTime, TimeUnit.SECONDS);
        stringRedisTemplate.opsForValue().set(userId.toString(), accessToken, expireTime, TimeUnit.SECONDS);
        return toVO(token, expireTime);
    }
    @Override
    public boolean deleteTokenByUserId(Long userId) {
        String accessToken = stringRedisTemplate.opsForValue().get(userId.toString());
        if (accessToken == null) {
            return false;
        }
        TokenPO token = tokenRedisTemplate.opsForValue().get(accessToken);
        if (token == null) {
            return false;
        }
        tokenRedisTemplate.delete(accessToken);
        stringRedisTemplate.delete(userId.toString());
        return true;
    }

    @Override
    public TokenVO verifyToken(String accessToken) {
        TokenPO token = tokenRedisTemplate.opsForValue().get(accessToken);
        if (token == null) {
            return null;
        }
        tokenRedisTemplate.expire(accessToken, 900, TimeUnit.SECONDS);
        stringRedisTemplate.expire(token.getUserId().toString(), 900, TimeUnit.SECONDS);
        return toVO(token, 900);
    }

    @Override
    public String findTokenByUserId(Long userId) {
        return stringRedisTemplate.opsForValue().get(userId.toString());
    }

}
