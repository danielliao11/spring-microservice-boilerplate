package com.saintdan.framework.param;

import com.saintdan.framework.annotation.ParamField;

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

    private Long id;

    @ParamField
    private String clientIdAlias;

    @ParamField
    private String resourceIdStr;

    @ParamField
    private String clientSecretAlias;

    @ParamField
    private String scopeStr;

    @ParamField
    private String authorizedGrantTypeStr;

    private String registeredRedirectUriStr;

    @ParamField
    private String authoritieStr;

    private Integer accessTokenValiditySecondsAlias;

    private Integer refreshTokenValiditySecondsAlias;

    private String additionalInformationStr;

    public ClientParam() {

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

    public String getAuthoritieStr() {
        return authoritieStr;
    }

    public void setAuthoritieStr(String authoritieStr) {
        this.authoritieStr = authoritieStr;
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
