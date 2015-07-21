package com.github.saintdan.exception;

import com.github.saintdan.enums.ErrorType;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 7/21/15
 * @since JDK1.8
 */
public class UserException extends SystemException {

    private final static ErrorType ERROR_TYPE= ErrorType.USR0001;

    public UserException(ErrorType msg, Throwable t) {
        super(msg, t);
    }

    public UserException(ErrorType msg) {
        super(msg);
    }

    public UserException() {
        super(ERROR_TYPE);
    }
}
