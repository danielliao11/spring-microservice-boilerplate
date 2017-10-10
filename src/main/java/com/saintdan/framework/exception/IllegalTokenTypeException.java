package com.saintdan.framework.exception;


import com.saintdan.framework.enums.ErrorType;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 11/02/2017
 * @since JDK1.8
 */
public class IllegalTokenTypeException extends SystemException {

  private static final long serialVersionUID = -6654473594499368732L;

  public IllegalTokenTypeException(ErrorType type, Throwable t, String msg) {
    super(type, t, msg);
  }

  public IllegalTokenTypeException(ErrorType type, String msg) {
    super(type, msg);
  }

  public IllegalTokenTypeException(ErrorType type) {
    super(type);
  }

  public IllegalTokenTypeException(String msg) {
    super(msg);
  }

  public IllegalTokenTypeException() {
    super(ErrorType.SYS0007);
  }
}
