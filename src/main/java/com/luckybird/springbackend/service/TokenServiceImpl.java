package com.luckybird.springbackend.service;

import com.luckybird.springbackend.api.vo.TokenVO;
import com.luckybird.springbackend.api.vo.UserVO;
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

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public TokenVO generateToken(UserVO userVO) {
        UUID uuid = UUID.randomUUID();
        String accessToken = uuid.toString();
        Long userId = userVO.getId();
        int expiration = 900;
        // TODO: redis存储的value改为序列化用户信息
        redisTemplate.opsForValue().set(accessToken, userId, expiration, TimeUnit.SECONDS);
        TokenVO tokenVO = new TokenVO();
        tokenVO.setAccessToken(accessToken);
        tokenVO.setExpireTime(expiration);
        return tokenVO;
    }

    @Override
    public boolean verifyToken(String accessToken) {
        Long userId = (Long) redisTemplate.opsForValue().get(accessToken);
        if (userId == null) {
            return false;
        }
        redisTemplate.expire(accessToken, 900, TimeUnit.SECONDS);
        return true;
    }
}
