package com.saintdan.framework.exception;


import com.saintdan.framework.enums.ErrorType;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 11/02/2017
 * @since JDK1.8
 */
public class IllegalExtTypeException extends BaseException {

  private static final long serialVersionUID = -6654473594499368732L;

  public IllegalExtTypeException() {
    super(ErrorType.ILLEGAL_TOKEN_TYPE_ERROR);
  }

  public IllegalExtTypeException(String msg) {
    super(ErrorType.ILLEGAL_EXT_TYPE_ERROR.code(), msg);
  }
}
