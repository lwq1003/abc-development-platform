package tech.abc.platform.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * 缓存工具类
 *
 * @author wqliu
 * @date 2023-03-06
 */
@Component
@Slf4j
public class CacheUtil {


    @Autowired
    public RedisTemplate redisTemplate;


    /**
     * 设置缓存对象
     *
     * @param key   缓存的键
     * @param value 缓存的值
     */
    public <T> void set(String key, T value) {

        redisTemplate.opsForValue().set(key, value);

    }

    /**
     * 设置缓存对象，附带设定有效期
     *
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param timeout  时间
     * @param timeUnit 时间单位
     */
    public <T> void set(String key, T value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }


    /**
     * 获取缓存对象
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T get(String key) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }


    /**
     * 是否存在键
     *
     * @param key 缓存键
     * @return 是否存在
     */
    public boolean hasKey(String key) {

        return redisTemplate.hasKey(key);
    }


    /**
     * 删除缓存对象
     *
     * @param key 缓存的键
     */
    public boolean remove(String key) {
        return redisTemplate.delete(key);
    }


    /**
     * 从redis缓存中移除指定前缀的所有值
     */
    public void removePrefix(String prefix) {

        Set keys = redisTemplate.keys(prefix + "*");
        redisTemplate.delete(keys);
    }

    /**
     * 设置缓存对象的有效期
     *
     * @param key      缓存的键值
     * @param timeout  时间
     * @param timeUnit 时间单位
     */
    public <T> void expire(String key, Integer timeout, TimeUnit timeUnit) {
        redisTemplate.expire(key, timeout, timeUnit);
    }


    /**
     * 批量存入缓存
     *
     * @param cachedMap
     */
    public void setBatch(Map<String, String> cachedMap) {
        for (String key : cachedMap.keySet()) {
            set(key, cachedMap.get(key));
        }
    }


}
