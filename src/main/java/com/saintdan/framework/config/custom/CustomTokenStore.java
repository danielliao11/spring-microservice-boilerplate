package com.saintdan.framework.config.custom;

import com.saintdan.framework.enums.TokenStoreType;
import com.saintdan.framework.tools.Assert;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 2018/11/22
 * @since JDK1.8
 */
@Configuration
public class CustomTokenStore {

  // Return TokenStore by your config in application.yml -> token.store
  public TokenStore customTokenStore() {
    TokenStoreType type = TokenStoreType.valueOf(environment.getProperty(typeProp, defaultType));
    if (type.isRedis()) {
      return redisTokenStore();
    } else {
      return jdbcTokenStore();
    }
  }

  // JDBC token store type.
  private JdbcTokenStore jdbcTokenStore() {
    return new JdbcTokenStore(dataSource);
  }

  // Redis token store type.
  private RedisTokenStore redisTokenStore() {
    return new RedisTokenStore(redisConnectionFactory);
  }

  private final RedisConnectionFactory redisConnectionFactory;
  private final Environment environment;
  private final DataSource dataSource;
  private final static String typeProp = "token.store";
  private final static String defaultType = "REDIS";

  @Autowired public CustomTokenStore(RedisConnectionFactory redisConnectionFactory, Environment environment, DataSource dataSource) {
    Assert.defaultNotNull(redisConnectionFactory);
    Assert.defaultNotNull(environment);
    Assert.defaultNotNull(dataSource);
    this.redisConnectionFactory = redisConnectionFactory;
    this.environment = environment;
    this.dataSource = dataSource;
  }
}
