package com.saintdan.framework.po;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
@EntityListeners({AuditingEntityListener.class})
@Table(name = "clients")
public class Client implements Serializable {

    private static final long serialVersionUID = 6500601540965188191L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    @Column(length = 50)
    private String clientIdAlias;

    @NotEmpty
    @Column(length = 100)
    private String resourceIdStr;

    @NotEmpty
    @Column(length = 100)
    private String clientSecretAlias;

    /**
     * Available values: read, write
     */
    @NotEmpty
    @Column(length = 100)
    private String scopeStr;

    /**
     * grant types include
     * "authorization_code", "password", "assertion", and "refresh_token".
     * Default value is "authorization_code,refresh_token".
     */
    @NotEmpty
    @Column(length = 100)
    private String authorizedGrantTypeStr;

    /**
     * The redirect URI(s) established during registration (optional, comma separated).
     */
    @Column(length = 1024)
    private String registeredRedirectUriStr;

    /**
     * Authorities that are granted to the client (comma-separated). Distinct from the authorities
     * granted to the user on behalf of whom the client is acting.
     * <pre>
     *     For example: USER
     * </pre>
     */
    @Column(length = 500)
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
}
