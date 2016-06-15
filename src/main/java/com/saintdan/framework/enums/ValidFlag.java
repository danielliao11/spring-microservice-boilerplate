package com.saintdan.framework.enums;

/**
 * Valid flag.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/29/15
 * @since JDK1.8
 */
public enum ValidFlag implements IntentStateWithCodeAndDescription {

  INVALID(0, "invalid status"),
  VALID(1, "valid status"),

  UNKNOWN(-1, "unknown status"),;

  private int code;

  private String description;

  ValidFlag(int code, String description) {
    this.code = code;
    this.description = description;
  }

  @Override public int code() {
    return code;
  }

  @Override public String description() {
    return description;
  }

  public boolean isInvalid() {
    return code == INVALID.code();
  }

  public boolean isValid() {
    return code == VALID.code();
  }

  public boolean isUnknown() {
    return code == UNKNOWN.code();
  }

  public static ValidFlag parse(int code) {
    ValidFlag[] validFlags = ValidFlag.values();
    for (ValidFlag validFlag : validFlags) {
      if (validFlag.code() == code) {
        return validFlag;
      }
    }
    return UNKNOWN;
  }
}
