package com.saintdan.framework.vo;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * VO for {@link com.saintdan.framework.po.Client}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/25/15
 * @since JDK1.8
 */
public class ClientVO implements Serializable {

  private static final long serialVersionUID = -6088897333280284093L;

  private Long id;

  private String clientId;

  private Set<String> resourceIds;

  private String clientSecret;

  private Set<String> scope;

  private Set<String> authorizedGrantTypes;

  private Set<String> registeredRedirectUri;

  private Integer accessTokenValiditySeconds;

  private Integer refreshTokenValiditySeconds;

  private Collection<GrantedAuthority> grantedAuthorities;

  private String publicKey;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public Set<String> getResourceIds() {
    return resourceIds;
  }

  public void setResourceIds(Set<String> resourceIds) {
    this.resourceIds = resourceIds;
  }

  public String getClientSecret() {
    return clientSecret;
  }

  public void setClientSecret(String clientSecret) {
    this.clientSecret = clientSecret;
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

  public Integer getAccessTokenValiditySeconds() {
    return accessTokenValiditySeconds;
  }

  public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
    this.accessTokenValiditySeconds = accessTokenValiditySeconds;
  }

  public Integer getRefreshTokenValiditySeconds() {
    return refreshTokenValiditySeconds;
  }

  public void setRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
    this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
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
