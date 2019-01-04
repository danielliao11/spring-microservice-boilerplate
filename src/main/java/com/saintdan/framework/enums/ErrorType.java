package com.saintdan.framework.enums;

/**
 * Error type enums.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 7/21/15
 * @since JDK1.8
 */
public enum ErrorType implements EnumWithCodeAndDescription {

  CLIENT_ERROR(40000, "Client error."),
  ILLEGAL_PARAM_ERROR(42200, "Illegal param error."),
  CLIENT_REGISTER_ERROR(42201, "Client register error."),
  ILLEGAL_TOKEN_TYPE_ERROR(42202, "Illegal token type error."),
  ILLEGAL_EXT_TYPE_ERROR(42203, "Illegal ext type error."),

  SERVER_ERROR(50000, "Server error"),
  SIGN_FAILED(50001, "Sign failed."),

  UNKNOWN_ERROR(99999, "Unknown error.");

  private int code;
  private String msg;

  ErrorType(int code, String msg) {
    this.code = code;
    this.msg = msg;
  }


  @Override public int code() {
    return 0;
  }

  @Override public String msg() {
    return null;
  }
}
