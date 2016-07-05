package com.saintdan.framework.po;

import com.saintdan.framework.enums.ValidFlag;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Authorized client, provide for spring security.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/23/15
 * @since JDK1.8
 */
@Entity
@EntityListeners( {AuditingEntityListener.class} )
@Table( name = "clients" )
public class Client implements Serializable {

  private static final long serialVersionUID = 6500601540965188191L;

  @Id
  @SequenceGenerator( name = "clients_seq", sequenceName = "clients_seq", allocationSize = 1 )
  @GeneratedValue( generator = "clients_seq", strategy = GenerationType.SEQUENCE )
  @Column(updatable = false)
  private Long id;

  @NotEmpty
  @Column( length = 50 )
  private String clientIdAlias;

  @NotEmpty
  @Column( length = 100 )
  private String resourceIdStr;

  @NotEmpty
  @Column( length = 100 )
  private String clientSecretAlias;

  /**
   * Available values: read, write
   */
  @NotEmpty
  @Column( length = 100 )
  private String scopeStr;

  /**
   * grant types include
   * "authorization_code", "password", "assertion", and "refresh_token".
   * Default description is "authorization_code,refresh_token".
   */
  @NotEmpty
  @Column( length = 100 )
  private String authorizedGrantTypeStr;

  /**
   * The redirect URI(s) established during registration (optional, comma separated).
   */
  @Column( length = 1024 )
  private String registeredRedirectUriStr;

  /**
   * Authorities that are granted to the client (comma-separated). Distinct from the authorities
   * granted to the user on behalf of whom the client is acting.
   * <pre>
   *     For example: USER
   * </pre>
   */
  @Column( length = 500 )
  private String authoritiesStr;

  /**
   * The access token validity period in seconds (optional).
   * If unspecified a global default will be applied by the token services.
   */
  private Integer accessTokenValiditySecondsAlias;

  /**
   * The refresh token validity period in seconds (optional).
   * If unspecified a global default will  be applied by the token services.
   */
  private Integer refreshTokenValiditySecondsAlias;

  /**
   * Additional information for this client, not needed by the vanilla OAuth protocol but might be useful, for example,
   * for storing descriptive information.
   */
  private String additionalInformationStr;

  @Column( nullable = false )
  private ValidFlag validFlag = ValidFlag.VALID;

  @CreatedDate
  @Column( nullable = false )
  private Date createdDate = new Date();

  @CreatedBy
  @Column( nullable = false )
  private Long createdBy;

  @LastModifiedDate
  @Column( nullable = false )
  private Date lastModifiedDate = new Date();

  @LastModifiedBy
  @Column( nullable = false )
  private Long lastModifiedBy;

  @Version
  @Column( nullable = false )
  private int version;

  @Column( nullable = false, length = 5000 )
  private String publicKey;

  public Client() {
  }

  public Client(Client client) {
    super();
    this.clientIdAlias = client.getClientIdAlias();
    this.resourceIdStr = client.getResourceIdStr();
    this.clientSecretAlias = client.getClientSecretAlias();
    this.scopeStr = client.getScopeStr();
    this.authorizedGrantTypeStr = client.getAuthorizedGrantTypeStr();
    this.registeredRedirectUriStr = client.getRegisteredRedirectUriStr();
    this.authoritiesStr = client.getAuthoritiesStr();
    this.accessTokenValiditySecondsAlias = client.getAccessTokenValiditySecondsAlias();
    this.refreshTokenValiditySecondsAlias = client.getRefreshTokenValiditySecondsAlias();
    this.additionalInformationStr = client.getAdditionalInformationStr();
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

  public ValidFlag getValidFlag() {
    return validFlag;
  }

  public void setValidFlag(ValidFlag validFlag) {
    this.validFlag = validFlag;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public Long getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(Long createdBy) {
    this.createdBy = createdBy;
  }

  public Date getLastModifiedDate() {
    return lastModifiedDate;
  }

  public void setLastModifiedDate(Date lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
  }

  public Long getLastModifiedBy() {
    return lastModifiedBy;
  }

  public void setLastModifiedBy(Long lastModifiedBy) {
    this.lastModifiedBy = lastModifiedBy;
  }

  public int getVersion() {
    return version;
  }

  public void setVersion(int version) {
    this.version = version;
  }

  public String getPublicKey() {
    return publicKey;
  }

  public void setPublicKey(String publicKey) {
    this.publicKey = publicKey;
  }
}
