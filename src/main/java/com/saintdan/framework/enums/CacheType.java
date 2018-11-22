package com.saintdan.framework.enums;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 2018/11/21
 * @since JDK1.8
 */
public enum CacheType {
  REDIS,
  MAP;

  public boolean isRedis() {
    return name().equals(REDIS.name());
  }

  public boolean isMap() {
    return name().equals(MAP.name());
  }
}
