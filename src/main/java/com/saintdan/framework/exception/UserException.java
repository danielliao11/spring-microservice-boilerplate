package com.saintdan.framework.exception;

import com.saintdan.framework.enums.ErrorType;

import java.io.Serializable;

/**
 * User's exception.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 7/21/15
 * @since JDK1.8
 */
public class UserException extends SystemException implements Serializable {

    private static final long serialVersionUID = 389416492214970922L;

    private final static ErrorType ERROR_TYPE = ErrorType.USR0001;

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
