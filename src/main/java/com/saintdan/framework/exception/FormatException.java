package com.saintdan.framework.exception;


import com.saintdan.framework.enums.ErrorType;

import java.io.Serializable;

/**
 * Format exception.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 9/10/15
 * @since JDK1.8
 */
public class FormatException extends SystemException implements Serializable {

    private static final long serialVersionUID = 5032136765337850160L;

    private final static ErrorType ERROR_TYPE = ErrorType.FMT0001;

    public FormatException(ErrorType msg, Throwable t) {
        super(msg, t);
    }

    public FormatException(ErrorType msg) {
        super(msg);
    }

    public FormatException() {
        super(ERROR_TYPE);
    }
}
