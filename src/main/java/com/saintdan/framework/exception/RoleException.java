package com.saintdan.framework.exception;

import com.saintdan.framework.enums.ErrorType;

import java.io.Serializable;

/**
 * Role's exception.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 7/21/15
 * @since JDK1.8
 */
public class RoleException extends SystemException implements Serializable {

    private static final long serialVersionUID = 7128012525948994112L;

    private final static ErrorType ERROR_TYPE = ErrorType.ROL0001;

    public RoleException(ErrorType msg, Throwable t) {
        super(msg, t);
    }

    public RoleException(ErrorType msg) {
        super(msg);
    }

    public RoleException() {
        super(ERROR_TYPE);
    }
}
