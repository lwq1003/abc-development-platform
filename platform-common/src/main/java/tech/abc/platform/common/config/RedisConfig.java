package tech.abc.platform.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis 配置
 * <p>
 * 如不配置，默认
 * 使用JdkSerializationRedisSerializer作为序列化与反序列化的工具，将字符串转换为字节数组
 * 通过系统写入与读取没问题，但是通过redis客户端管理工具去查看值时，则会附加类似乱码的前缀（序列化产生）
 *
 * @author wqliu
 * @date 2023-03-08
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<Object, Object> redisStringTemplate(RedisTemplate<Object, Object> redisTemplate) {
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(stringRedisSerializer);
        return redisTemplate;
    }

}
