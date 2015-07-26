package com.github.saintdan.exception;


import com.github.saintdan.enums.ErrorType;

import java.io.Serializable;

/**
 * Unknown exception.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 7/26/15
 * @since JDK1.8
 */
public class UnknownException extends SystemRuntimeException implements Serializable {

    private static final long serialVersionUID = -7431810328087316293L;

    private final static ErrorType ERROR_TYPE = ErrorType.UNKNOWN;

    public UnknownException() {
        super(ERROR_TYPE);
    }
}
