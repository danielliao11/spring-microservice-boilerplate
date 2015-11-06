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
    SYS0004("Signature error."),
    SYS0005("Format error."),

    // COMMONS
    SYS0100("%s error."),
    SYS0110("%s create failed."),
    SYS0111("%s already existing, %s taken"),
    SYS0120("%s find error."),
    SYS0121("No %s exists."),
    SYS0122("Cannot find any %s by this param."),
    SYS0130("%s update failed."),
    SYS0131("%s's %s update failed."),
    SYS0140("%s delete failed."),

    // Unknown error.
    UNKNOWN("unknown error.");

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
