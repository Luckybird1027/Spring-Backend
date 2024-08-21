package com.luckybird.springbackend.service;

import com.luckybird.springbackend.api.vo.TokenVO;
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

    private final RedisTemplate<String, TokenPO> redisTemplate;

    private TokenVO toVO(TokenPO tokenpo, int expireTime){
        TokenVO tokenVO = new TokenVO();
        tokenVO.setAccessToken(tokenpo.getAccessToken());
        tokenVO.setUserId(tokenpo.getUserId());
        tokenVO.setExpireTime(expireTime);
        return tokenVO;
    }

    @Override
    public TokenVO generateToken(Long userId) {
        UUID uuid = UUID.randomUUID();
        String accessToken = uuid.toString();
        int expireTime = 900;
        TokenPO token = new TokenPO(accessToken, userId);
        // TODO: redis存储的value改为序列化用户信息
        redisTemplate.opsForValue().set(accessToken, token, expireTime, TimeUnit.SECONDS);
        return toVO(token, expireTime);
    }

    @Override
    public TokenVO verifyToken(String accessToken) {
        TokenPO token = redisTemplate.opsForValue().get(accessToken);
        if (token == null) {
            return null;
        }
        redisTemplate.expire(accessToken, 900, TimeUnit.SECONDS);
        return toVO(token, 900);
    }
}
