package com.saintdan.framework.common.exception;


import com.saintdan.framework.common.enums.ErrorType;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 11/02/2017
 * @since JDK1.8
 */
public class IllegalTokenTypeException extends BaseException {

  private static final long serialVersionUID = -8476571357699322239L;

  public IllegalTokenTypeException() {
    super(ErrorType.ILLEGAL_TOKEN_TYPE_ERROR);
  }
}
