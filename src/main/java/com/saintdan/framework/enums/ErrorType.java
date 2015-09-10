package com.saintdan.framework.enums;

/**
 * Error type enums.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 7/21/15
 * @since JDK1.8
 */
public enum ErrorType implements IntentState {

    // System
    SYS0001("System error."),
    SYS0002("Param could not be null."),

    // Format
    FMT0001("Format error."),
    FMT0010("Format error, unknown format."),

    // User
    USR0001("User error."),
    USR0010("User find error."),
    USR0011("Can not find User by the usr param."),

    // Sign
    SGN0001("Signature error."),
    SGN0010("Signature failed"),
    SGN0020("Signature verification failed."),
    SGN0021("RSA signature verification failed."),

    // Unknown error.
    UNKNOWN("unknown error.");

    /**
     * Value
     */
    private final String val;

    /**
     * Constructor
     *
     * @param val value
     */
    ErrorType(String val) {
        this.val = val;
    }

    @Override
    public String value() {
        return this.val;
    }
}
