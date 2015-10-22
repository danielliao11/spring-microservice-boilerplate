package com.saintdan.framework.exception;

import com.saintdan.framework.enums.ErrorType;

import java.io.Serializable;

/**
 * Group's exception.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 7/21/15
 * @since JDK1.8
 */
public class GroupException extends SystemException implements Serializable {

    private static final long serialVersionUID = -1633597218346582580L;

    private final static ErrorType ERROR_TYPE = ErrorType.GRP0001;

    public GroupException(ErrorType msg, Throwable t) {
        super(msg, t);
    }

    public GroupException(ErrorType msg) {
        super(msg);
    }

    public GroupException() {
        super(ERROR_TYPE);
    }
}
