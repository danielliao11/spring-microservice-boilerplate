package com.saintdan.framework.exception;

import com.saintdan.framework.enums.ErrorType;

import java.io.Serializable;

/**
 * Log's exception.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 7/21/15
 * @since JDK1.8
 */
public class LogException extends SystemException implements Serializable {

    private static final long serialVersionUID = -5350698020163580712L;

    private final static ErrorType ERROR_TYPE = ErrorType.LOG0001;

    public LogException(ErrorType msg, Throwable t) {
        super(msg, t);
    }

    public LogException(ErrorType msg) {
        super(msg);
    }

    public LogException() {
        super(ERROR_TYPE);
    }
}
