package com.github.saintdan.enums;

/**
 * Error type enum.
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 7/21/15
 * @since JDK1.8
 */
public enum ErrorType implements IntentState {

    // System
    SYS0001("System error."),
    SYS0002("Param could not be null."),

    // User
    USR0001("User find error."),

    // Sign
    SGN0001("Sign error."),
    SGN0010("Sign failed"),
    SGN0020("Check sign failed."),

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
