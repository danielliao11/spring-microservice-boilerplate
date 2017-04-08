package com.saintdan.framework.enums;

/**
 * Operation status.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 8/19/15
 * @since JDK1.8
 */
public enum GrantType implements IntentStateWithDescription {

  PASSWORD("password"),
  REFRESH_TOKEN("refresh_token"),
  AUTHORIZATION_CODE("authorization_code"),;

  private final String description;

  GrantType(String description) {
    this.description = description;
  }

  @Override public String description() {
    return this.description;
  }

}
