package com.saintdan.framework.exception;

import com.saintdan.framework.enums.ErrorType;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 2019/1/4
 * @since JDK1.8
 */
public class SignFailedException extends BaseException {

  private static final long serialVersionUID = 4854651877708473571L;

  public SignFailedException() {
    super(ErrorType.SIGN_FAILED);
  }
}
