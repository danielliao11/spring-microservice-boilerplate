package com.saintdan.framework.enums;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 2018/11/21
 * @since JDK1.8
 */
public enum CacheType {

  REDIS("redis"),
  MAP("map");

  private String type;

  CacheType(String type) {
    this.type = type;
  }

  public boolean isRedis() {
    return type.equals(REDIS.type);
  }

  public boolean isMap() {
    return type.equals(MAP.type);
  }
}
