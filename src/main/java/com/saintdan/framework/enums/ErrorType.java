package com.saintdan.framework.enums;

/**
 * Error type enums.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 7/21/15
 * @since JDK1.8
 */
public enum ErrorType implements IntentStateWithDescription {

    // System
    SYS0001("System error."),
    SYS0002("Param cannot be null."),
    SYS0003("You need use 'bearer' token."),

    // Format
    FMT0001("Format error."),
    FMT0010("Format error, unknown format."),

    // LOG
    LOG0001("Log error."),
    LOG0010("Log find error."),
    LOG0011("Cannot find any log, no log exists."),
    LOG0020("Log create failed."),

    // Client
    CLT0001("Client error."),
    CLT0010("Client find error."),
    CLT0011("Cannot find any client, no client exists."),
    CLT0012("Cannot find any client by this id param."),
    CLT0013("Cannot find any client by this clientId param."),
    CLT0020("Create client failed."),
    CLT0030("Client already existing."),
    CLT0031("Client already existing, clientId taken."),

    // User
    USR0001("User error."),
    USR0002("Wrong password."),
    USR0010("User find error."),
    USR0011("Cannot find any user, no user exists."),
    USR0012("Cannot find any user by this id param."),
    USR0013("Cannot find any user by this usr param."),
    USR0020("Create user failed."),
    USR0030("User already existing."),
    USR0031("User already existing, usr taken."),
    USR0040("Update user failed."),
    USR0041("Update user's password failed."),

    // Role
    ROL0001("Role error."),
    ROL0010("Role find error."),
    ROL0011("Cannot find any role, no role exists."),
    ROL0012("Cannot find any role by this id param."),
    ROL0013("Cannot find any role by this name param."),
    ROL0020("Create role failed."),
    ROL0030("Role already existing."),
    ROL0031("Role already existing, name taken."),

    // Group
    GRP0001("Group error."),
    GRP0010("Group find error."),
    GRP0011("Cannot find any group, no group exists."),
    GRP0012("Cannot find any group by this id param."),
    GRP0013("Cannot find any group by this name param."),
    GRP0020("Create group failed."),
    GRP0030("Group already existing."),
    GRP0031("Group already existing, name taken."),

    // Resource
    RSC0001("Resource error."),
    RSC0010("Resource find error."),
    RSC0011("Cannot find any resource, no resource exists."),
    RSC0012("Cannot find any resource by this id param."),
    RSC0013("Cannot find any resource by this name param."),
    RSC0020("Create resource failed."),
    RSC0030("Resource already existing."),
    RSC0031("Resource already existing, name taken."),

    // Sign
    SGN0001("Signature error."),
    SGN0010("Signature failed"),
    SGN0020("Signature verification failed."),
    SGN0021("RSA signature verification failed."),

    // Unknown error.
    UNKNOWN("unknown error."),;

    /**
     * Description
     */
    private final String description;

    /**
     * Constructor
     *
     * @param description description
     */
    ErrorType(String description) {
        this.description = description;
    }

    @Override
    public String description() {
        return this.description;
    }
}
