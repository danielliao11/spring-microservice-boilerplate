package com.saintdan.framework.common.enums;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 2019/1/4
 * @since JDK1.8
 */
public enum ObjectStatus implements EnumWithCode {

  INVALID(0),
  VALID(1);

  private final Integer code;

  ObjectStatus(Integer code) {
    this.code = code;
  }

  @Override public Integer code() {
    return code;
  }
}
