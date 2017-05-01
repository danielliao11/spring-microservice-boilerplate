package com.saintdan.framework.param;

import com.saintdan.framework.annotation.NotNullField;
import com.saintdan.framework.annotation.SizeField;
import com.saintdan.framework.domain.ClientDomain;
import com.saintdan.framework.enums.OperationType;
import io.swagger.annotations.ApiModelProperty;

/**
 * Param bean for {@link ClientDomain}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/25/15
 * @since JDK1.8
 */
public class ClientParam extends BaseParam {

  private static final long serialVersionUID = 6065608866944007796L;

  @NotNullField(value = { OperationType.UPDATE, OperationType.DELETE }, message = "id cannot be null.")
  private Long id;

  @ApiModelProperty(value = "clientId", required = true,
      notes = "clientIdAlias cannot be null and must greater than or equal to 6 and less than or equal to 16.")
  @NotNullField(value = OperationType.CREATE, message = "clientIdAlias cannot be null.")
  @SizeField(min = 6, max = 16, value = OperationType.CREATE, message = "clientIdAlias must greater than or equal to 6 and less than or equal to 16.")
  private String clientIdAlias;

  @ApiModelProperty(value = "resourceId", required = true, notes = "resourceIdStr cannot be null.")
  @NotNullField(value = OperationType.CREATE, message = "resourceIdStr cannot be null.")
  private String resourceIdStr;

  @ApiModelProperty(value = "clientSecret", required = true, notes = "clientSecretAlias cannot be null.")
  @NotNullField(value = OperationType.CREATE,
      message = "clientSecretAlias cannot be null and must greater than or equal to 8 and less than or equal to 32.")
  @SizeField(min = 8, max = 32, value = OperationType.CREATE, message = "clientSecretAlias must greater than or equal to 8 and less than or equal to 32.")
  private String clientSecretAlias;

  @ApiModelProperty(value = "scope", required = true, notes = "scopeStr cannot be null.", example = "read")
  @NotNullField(value = OperationType.CREATE, message = "scopeStr cannot be null.")
  private String scopeStr;

  @ApiModelProperty(value = "authorizedGrantType", required = true, notes = "authorizedGrantType cannot be null.",
      example = "password,refresh_token,authorization_code")
  @NotNullField(value = OperationType.CREATE, message = "authorizedGrantTypeStr cannot be null.")
  private String authorizedGrantTypeStr;

  @ApiModelProperty(value = "registeredRedirectUri")
  private String registeredRedirectUriStr;

  @ApiModelProperty(value = "authorities", required = true, notes = "authoritiesStr cannot be null.", example = "USER")
  @NotNullField(value = OperationType.CREATE, message = "authoritiesStr cannot be null.")
  private String authoritiesStr;

  @ApiModelProperty(value = "accessTokenValiditySeconds")
  private Integer accessTokenValiditySecondsAlias = 1800;

  @ApiModelProperty(value = "refreshTokenValiditySeconds")
  private Integer refreshTokenValiditySecondsAlias = 1800;

  @ApiModelProperty(value = "additionalInformation")
  private String additionalInformationStr;

  private String publicKey;

  public ClientParam() {}

  public ClientParam(Long id) {
    this.id = id;
  }

  public ClientParam(String clientIdAlias) {
    this.clientIdAlias = clientIdAlias;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getClientIdAlias() {
    return clientIdAlias;
  }

  public void setClientIdAlias(String clientIdAlias) {
    this.clientIdAlias = clientIdAlias;
  }

  public String getResourceIdStr() {
    return resourceIdStr;
  }

  public void setResourceIdStr(String resourceIdStr) {
    this.resourceIdStr = resourceIdStr;
  }

  public String getClientSecretAlias() {
    return clientSecretAlias;
  }

  public void setClientSecretAlias(String clientSecretAlias) {
    this.clientSecretAlias = clientSecretAlias;
  }

  public String getScopeStr() {
    return scopeStr;
  }

  public void setScopeStr(String scopeStr) {
    this.scopeStr = scopeStr;
  }

  public String getAuthorizedGrantTypeStr() {
    return authorizedGrantTypeStr;
  }

  public void setAuthorizedGrantTypeStr(String authorizedGrantTypeStr) {
    this.authorizedGrantTypeStr = authorizedGrantTypeStr;
  }

  public String getRegisteredRedirectUriStr() {
    return registeredRedirectUriStr;
  }

  public void setRegisteredRedirectUriStr(String registeredRedirectUriStr) {
    this.registeredRedirectUriStr = registeredRedirectUriStr;
  }

  public String getAuthoritiesStr() {
    return authoritiesStr;
  }

  public void setAuthoritiesStr(String authoritiesStr) {
    this.authoritiesStr = authoritiesStr;
  }

  public Integer getAccessTokenValiditySecondsAlias() {
    return accessTokenValiditySecondsAlias;
  }

  public void setAccessTokenValiditySecondsAlias(Integer accessTokenValiditySecondsAlias) {
    this.accessTokenValiditySecondsAlias = accessTokenValiditySecondsAlias;
  }

  public Integer getRefreshTokenValiditySecondsAlias() {
    return refreshTokenValiditySecondsAlias;
  }

  public void setRefreshTokenValiditySecondsAlias(Integer refreshTokenValiditySecondsAlias) {
    this.refreshTokenValiditySecondsAlias = refreshTokenValiditySecondsAlias;
  }

  public String getAdditionalInformationStr() {
    return additionalInformationStr;
  }

  public void setAdditionalInformationStr(String additionalInformationStr) {
    this.additionalInformationStr = additionalInformationStr;
  }

  public String getPublicKey() {
    return publicKey;
  }

  public void setPublicKey(String publicKey) {
    this.publicKey = publicKey;
  }
}
