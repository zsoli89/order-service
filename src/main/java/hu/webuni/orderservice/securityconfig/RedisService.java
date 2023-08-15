package hu.webuni.orderservice.securityconfig;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String getValueFromRedis(String redisKey) {
        return redisTemplate.opsForValue().get(redisKey);
    }

}
