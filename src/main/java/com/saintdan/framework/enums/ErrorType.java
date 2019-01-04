package com.saintdan.framework.enums;

import java.util.Arrays;

/**
 * Error type enums.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 7/21/15
 * @since JDK1.8
 */
public enum ErrorType implements EnumWithCodeAndDescription {

  CLIENT_ERROR(40000, "Client error."),
  ILLEGAL_PARAM_ERROR(42200, "Illegal param."),
  CLIENT_REGISTER_ERROR(42201, "Client register error."),
  ILLEGAL_TOKEN_TYPE_ERROR(42202, "Illegal token type."),
  ILLEGAL_EXT_TYPE_ERROR(42203, "Illegal ext type."),
  NO_SUCH_ELEMENT_ERROR(42204, "No such element."),
  LOGIN_ERROR(42210, "Login error."),
  USERNAME_OR_PASSWORD_IS_WRONG_ERROR(42211, "Username or password is wrong."),
  INVALID_USER_ERROR(42212, "Invalid user."),

  SERVER_ERROR(50000, "Server error"),
  SIGN_FAILED(50001, "Sign failed."),

  UNKNOWN_ERROR(99999, "Unknown error.");

  private Integer code;
  private String msg;

  ErrorType(int code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  @Override public Integer code() {
    return code;
  }

  @Override public String msg() {
    return msg;
  }

  public static ErrorType parse(Integer code) {
    return Arrays.stream(ErrorType.values()).filter(e -> e.code.equals(code)).findFirst().orElse(null);
  }
}
