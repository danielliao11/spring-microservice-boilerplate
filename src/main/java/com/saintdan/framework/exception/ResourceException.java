package com.saintdan.framework.exception;

import com.saintdan.framework.enums.ErrorType;

import java.io.Serializable;

/**
 * Resource's exception.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 7/21/15
 * @since JDK1.8
 */
public class ResourceException extends SystemException implements Serializable {


    private static final long serialVersionUID = -2806456935183534183L;

    private final static ErrorType ERROR_TYPE = ErrorType.RSC0001;

    public ResourceException(ErrorType msg, Throwable t) {
        super(msg, t);
    }

    public ResourceException(ErrorType msg) {
        super(msg);
    }

    public ResourceException() {
        super(ERROR_TYPE);
    }
}
