package com.saintdan.framework.starter.config.custom;

import com.saintdan.framework.common.tools.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 2018/11/22
 * @since JDK1.8
 */
@Configuration
public class CustomTokenStore {

  public TokenStore customTokenStore() {
    return redisTokenStore();
  }

  private RedisTokenStore redisTokenStore() {
    return new RedisTokenStore(redisConnectionFactory);
  }

  private final RedisConnectionFactory redisConnectionFactory;

  @Autowired public CustomTokenStore(RedisConnectionFactory redisConnectionFactory) {
    Assert.defaultNotNull(redisConnectionFactory);
    this.redisConnectionFactory = redisConnectionFactory;
  }
}
