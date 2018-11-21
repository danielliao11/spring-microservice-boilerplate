package com.saintdan.framework.config;

import com.saintdan.framework.param.RequestCount;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis for caching config.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 2018/11/20
 * @since JDK1.8
 */
@Configuration
@EnableCaching
@Order(1)
public class RedisConfig extends CachingConfigurerSupport {

  @Bean(name = "limitRedisTemplate")
  public RedisTemplate<String, RequestCount> limitRedisTemplate(RedisConnectionFactory cf) {
    RedisTemplate<String, RequestCount> redisTemplate = new RedisTemplate<>();
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(RequestCount.class));
    redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer(RequestCount.class));
    redisTemplate.setEnableTransactionSupport(true);
    redisTemplate.setConnectionFactory(cf);
    return redisTemplate;
  }
}
