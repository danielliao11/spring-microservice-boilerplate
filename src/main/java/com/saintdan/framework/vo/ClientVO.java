package com.saintdan.framework.vo;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;

/**
 * Client's VO.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/25/15
 * @since JDK1.8
 */
public class ClientVO extends ResultVO {

    private static final long serialVersionUID = -6088897333280284093L;

    private Long id;

    private String clientIdAlias;

    private Set<String> resourceIds;

    private String clientSecretAlias;

    private Set<String> scope;

    private Set<String> authorizedGrantTypes;

    private Set<String> registeredRedirectUri;

    private Integer accessTokenValiditySecondsAlias;

    private Integer refreshTokenValiditySecondsAlias;

    private Collection<GrantedAuthority> grantedAuthorities;

    private String publicKey;

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

    public Set<String> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(Set<String> resourceIds) {
        this.resourceIds = resourceIds;
    }

    public String getClientSecretAlias() {
        return clientSecretAlias;
    }

    public void setClientSecretAlias(String clientSecretAlias) {
        this.clientSecretAlias = clientSecretAlias;
    }

    public Set<String> getScope() {
        return scope;
    }

    public void setScope(Set<String> scope) {
        this.scope = scope;
    }

    public Set<String> getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public void setAuthorizedGrantTypes(Set<String> authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    public Set<String> getRegisteredRedirectUri() {
        return registeredRedirectUri;
    }

    public void setRegisteredRedirectUri(Set<String> registeredRedirectUri) {
        this.registeredRedirectUri = registeredRedirectUri;
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

    public Collection<GrantedAuthority> getGrantedAuthorities() {
        return grantedAuthorities;
    }

    public void setGrantedAuthorities(Collection<GrantedAuthority> grantedAuthorities) {
        this.grantedAuthorities = grantedAuthorities;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
