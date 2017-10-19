package com.saintdan.framework.enums;

/**
 * Account source.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/29/15
 * @since JDK1.8
 */
public enum AccountSourceType implements IntentStateWithCodeAndDescription {

  MOBILE(0, "mobile account"),
  EMAIL(1, "email account"),
  USERNAME(2, "username account"),
  GOOGLE(3, "google account"),
  FACEBOOK(4, "facebook account"),
  GITHUB(5, "github account"),
  TWITTER(6, "twitter account"),
  WECHAT(7, "wechat account"),
  QQ(8, "qq account"),
  WEIBO(9, "weibo account"),

  UNKNOWN(-1, "unknown account"),;

  private int code;

  private String description;

  AccountSourceType(int code, String description) {
    this.code = code;
    this.description = description;
  }

  @Override public int code() {
    return code;
  }

  @Override public String description() {
    return description;
  }

  public boolean isMobile() {
    return code == MOBILE.code();
  }

  public boolean isWechat() {
    return code == WECHAT.code();
  }

  public boolean isQq() {
    return code == QQ.code();
  }

  public boolean isWeibo() {
    return code == WEIBO.code();
  }

  public boolean isUnknown() {
    return code == UNKNOWN.code();
  }

  public static AccountSourceType parse(int code) {
    AccountSourceType[] validFlags = AccountSourceType.values();
    for (AccountSourceType validFlag : validFlags) {
      if (validFlag.code() == code) {
        return validFlag;
      }
    }
    return UNKNOWN;
  }
}
