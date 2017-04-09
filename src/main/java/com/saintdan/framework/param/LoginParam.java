package com.saintdan.framework.param;

import com.saintdan.framework.annotation.NotNullField;
import com.saintdan.framework.annotation.SizeField;
import com.saintdan.framework.enums.GrantType;
import com.saintdan.framework.enums.OperationType;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 15/02/2017
 * @since JDK1.8
 */
public class LoginParam extends BaseParam {

  private static final long serialVersionUID = 1148462952236125805L;

  @ApiModelProperty(value = "Username: required when grant type is PASSWORD.")
  @NotNullField(value = OperationType.LOGIN, message = "usr cannot be null.")
  @SizeField(min = 4, max = 50, value = OperationType.LOGIN, message = "usr must greater than or equal to 4 and less than or equal to 50.")
  private String usr;

  @ApiModelProperty(value = "Password: required when grant type is PASSWORD.")
  @NotNullField(value = OperationType.LOGIN, message = "pwd cannot be null.")
  @SizeField(min = 6, max = 16, value = OperationType.LOGIN, message = "pwd must greater than or equal to 6 and less than or equal to 16.")
  private String pwd;

  @ApiModelProperty(value = "refreshToken: required when grant type is REFRESH_TOKEN.")
  @NotNullField(value = OperationType.UPDATE, message = "refresh token cannot be null.")
  private String refreshToken;

  @ApiModelProperty(value = "grantType: default is PASSWORD.")
  private GrantType grantType;

  public LoginParam() {}

  public LoginParam(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  public String getUsr() {
    return usr;
  }

  public void setUsr(String usr) {
    this.usr = usr;
  }

  public String getPwd() {
    return pwd;
  }

  public void setPwd(String pwd) {
    this.pwd = pwd;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  public GrantType getGrantType() {
    return grantType;
  }

  public void setGrantType(GrantType grantType) {
    this.grantType = grantType;
  }
}
