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
    SYS0002("Param cannot be null."),

    // Format
    FMT0001("Format error."),
    FMT0010("Format error, unknown format."),

    // User
    USR0001("User error."),
    USR0010("User find error."),
    USR0011("Cannot find any user, no user yet."),
    USR0012("Cannot find any user by this id param."),
    USR0013("Cannot find any user by this usr param."),
    USR0020("Create user failed."),
    USR0030("User already existing."),
    USR0031("User already existing, usr taken."),

    // Role
    ROL0001("Role error."),
    ROL0010("Role find error."),
    ROL0011("Cannot find any role, no role yet."),
    ROL0012("Cannot find any role by this id param."),
    ROL0020("Create role failed."),
    ROL0030("Role already existing."),
    ROL0031("Role already existing, name taken."),

    // Sign
    SGN0001("Signature error."),
    SGN0010("Signature failed"),
    SGN0020("Signature verification failed."),
    SGN0021("RSA signature verification failed."),

    // Unknown error.
    UNKNOWN("unknown error."),;

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
