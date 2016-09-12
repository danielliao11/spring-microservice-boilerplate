package com.saintdan.framework.param;

import com.saintdan.framework.annotation.NotNullField;
import com.saintdan.framework.annotation.SignField;
import com.saintdan.framework.domain.ClientDomain;
import com.saintdan.framework.enums.OperationType;
import javax.validation.constraints.Size;

/**
 * Param bean for {@link ClientDomain}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/25/15
 * @since JDK1.8
 */
public class ClientParam extends BaseParam {

  private static final long serialVersionUID = 6065608866944007796L;

  @SignField
  @NotNullField(value = {OperationType.UPDATE, OperationType.DELETE}, message = "id cannot be null.")
  private Long id;

  @SignField
  @NotNullField(value = OperationType.CREATE, message = "clientIdAlias cannot be null.")
  @Size(min = 6, max = 50)
  private String clientIdAlias;

  @SignField
  @NotNullField(value = OperationType.CREATE, message = "resourceIdStr cannot be null.")
  private String resourceIdStr;

  @SignField
  @NotNullField(value = OperationType.CREATE, message = "clientSecretAlias cannot be null.")
  @Size(min = 8, max = 50)
  private String clientSecretAlias;

  @SignField
  @NotNullField(value = OperationType.CREATE, message = "scopeStr cannot be null.")
  private String scopeStr;

  @SignField
  @NotNullField(value = OperationType.CREATE, message = "authorizedGrantTypeStr cannot be null.")
  private String authorizedGrantTypeStr;

  @SignField
  private String registeredRedirectUriStr;

  @SignField
  @NotNullField(value = OperationType.CREATE, message = "authoritiesStr cannot be null.")
  private String authoritiesStr;

  @SignField private Integer accessTokenValiditySecondsAlias;

  @SignField private Integer refreshTokenValiditySecondsAlias;

  @SignField private String additionalInformationStr;

  public ClientParam() {}

  public ClientParam(String clientIdAlias) {
    this.clientIdAlias = clientIdAlias;
  }

  public ClientParam(Long id) {
    this.id = id;
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
}
