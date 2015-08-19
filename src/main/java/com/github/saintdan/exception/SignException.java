package com.github.saintdan.exception;

import com.github.saintdan.enums.ErrorType;

import java.io.Serializable;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 8/19/15
 * @since JDK1.8
 */
public class SignException extends SystemException implements Serializable {

    private static final long serialVersionUID = 7181248738252641022L;

    private final static ErrorType ERROR_TYPE = ErrorType.SGN0001;

    public SignException(ErrorType msg, Throwable t) {
        super(msg, t);
    }

    public SignException(ErrorType msg) {
        super(msg);
    }

    public SignException() {
        super(ERROR_TYPE);
    }
}
