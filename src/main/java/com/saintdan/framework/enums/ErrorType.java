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
  SYS0002("Param is not acceptable."),
  SYS0003("You need use 'bearer' token."),
  SYS0004("Signature error."),
  SYS0005("Format error."),
  SYS0006("Invalid client"),
  SYS0007("Invalid token"),
  SYS0008("Request too frequently"),

  // COMMONS
  SYS0100("%s error."),
  SYS0110("%s create failed."),
  SYS0111("%s already existing, %s taken"),
  SYS0120("%s find error."),
  SYS0121("%s find error, no %s exists."),
  SYS0122("Cannot find any %s by %s param."),
  SYS0130("%s update failed."),
  SYS0131("%s's %s update failed."),
  SYS0140("%s delete failed."),

  // LOGIN
  LOG0001("User not exists."),
  LOG0002("Wrong password."),
  LOG0003("Disabled account."),
  LOG0004("Expired account."),
  LOG0005("Locked account."),
  LOG0006("Expired credentials."),
  LOG0007("Illegal token type."),

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

  @Override public String description() {
    return this.description;
  }

  public static ErrorType parse(String name) {
    ErrorType[] errorTypes = ErrorType.values();
    for (ErrorType errorType : errorTypes) {
      if (errorType.name().equals(name)) {
        return errorType;
      }
    }
    return UNKNOWN;
  }
}
