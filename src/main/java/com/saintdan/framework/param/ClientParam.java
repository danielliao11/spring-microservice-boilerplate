package com.saintdan.framework.param;

import com.saintdan.framework.annotation.SignField;
import com.saintdan.framework.annotation.ValidationField;

import java.io.Serializable;

/**
 * Client's RESTFul param bean.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/25/15
 * @since JDK1.8
 */
public class ClientParam extends BaseParam implements Serializable {

    private static final long serialVersionUID = 6065608866944007796L;

    @SignField
    private Long id;

    @SignField
    @ValidationField
    private String clientIdAlias;

    @SignField
    @ValidationField
    private String resourceIdStr;

    @SignField
    @ValidationField
    private String clientSecretAlias;

    @SignField
    @ValidationField
    private String scopeStr;

    @SignField
    @ValidationField
    private String authorizedGrantTypeStr;

    @SignField
    private String registeredRedirectUriStr;

    @SignField
    @ValidationField
    private String authoritiesStr;

    @SignField
    private Integer accessTokenValiditySecondsAlias;

    @SignField
    private Integer refreshTokenValiditySecondsAlias;

    @SignField
    private String additionalInformationStr;

    public ClientParam() {

    }

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
