package pers.syq.fastadmin.backstage.common.utils;

import cn.hutool.crypto.SecureUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import pers.syq.fastadmin.backstage.common.constants.SecurityConstants;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    public <T> Optional<T> getValue(String key,Class<T> clazz){
        return Optional.ofNullable((T)redisTemplate.opsForValue().get(key));
    }

    public Optional<String> getStringValue(String key){
        return this.getValue(key,String.class);
    }

    public Optional<Object> getValue(String key){
        return this.getValue(key,Object.class);
    }

    public void setToken(String token,Long userId){
        String tokenKey = SecureUtil.md5(SecurityConstants.REDIS_TOKEN_PREFIX + userId);
        redisTemplate.opsForValue().set(tokenKey,token,SecurityConstants.TOKEN_EXPIRATION, TimeUnit.SECONDS);
    }

    public void set(String key,Object value){
        redisTemplate.opsForValue().set(key,value);
    }

    public void set(String key,Object value,long timeout,TimeUnit unit){
        redisTemplate.opsForValue().set(key,value,timeout,unit);
    }

    public void delete(String key){
        redisTemplate.delete(key);
    }

    public void delete(Collection<String> keys){
        redisTemplate.delete(keys);
    }

    public void deleteTokenByUserId(Long userId){
        String tokenKey = SecureUtil.md5(SecurityConstants.REDIS_TOKEN_PREFIX + userId);
        redisTemplate.delete(tokenKey);
    }

    public void deleteTokenByUserIds(Collection<Long> userIds){
        Set<String> keys = new HashSet<>();
        for (Long userId : userIds) {
            keys.add(SecureUtil.md5(SecurityConstants.REDIS_TOKEN_PREFIX + userId));
        }
        redisTemplate.delete(keys);
    }

    public Long getExpire(String key){
        return redisTemplate.getExpire(key);
    }

    public void expire(String key,long timeout,TimeUnit unit){
        redisTemplate.expire(key,timeout,unit);
    }
}
