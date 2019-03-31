package com.saintdan.framework.common.exception;

import com.saintdan.framework.common.enums.ErrorType;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 2019/1/4
 * @since JDK1.8
 */
public class IllegalParamException extends BaseException {

  private static final long serialVersionUID = 2180099635704949784L;

  public IllegalParamException() {
    super(ErrorType.ILLEGAL_PARAM_ERROR);
  }

  public IllegalParamException(String msg) {
    super(ErrorType.ILLEGAL_PARAM_ERROR.code(), msg);
  }
}
