package com.saintdan.framework.common.exception;

import com.saintdan.framework.common.enums.ErrorType;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 2019/1/4
 * @since JDK1.8
 */
public abstract class BaseException extends RuntimeException {

  private static final long serialVersionUID = -6098936501337852285L;
  private int code;
  private String message;

  BaseException(ErrorType errorType) {
    super(errorType.msg());
    this.code = errorType.code();
    this.message = errorType.msg();
  }

  BaseException(int code, String msg) {
    super(msg);
    this.code = code;
    this.message = msg;
  }

  public int code() {
    return code;
  }

  public String message() {
    return message;
  }
}
