package com.saintdan.framework.po;

import com.google.common.collect.Sets;
import com.saintdan.framework.constant.CommonsConstant;
import com.saintdan.framework.listener.PersistentListener;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

/**
 * Authorized client, provide for spring security.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/23/15
 * @since JDK1.8
 */
@Entity
@EntityListeners(PersistentListener.class)
@Table(name = "clients")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client implements ClientDetails {

  private static final long serialVersionUID = 6500601540965188191L;

  @GenericGenerator(
      name = "clientSequenceGenerator",
      strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
      parameters = {
          @Parameter(name = "sequence_name", value = "clients_seq"),
          @Parameter(name = "initial_value", value = "1"),
          @Parameter(name = "increment_size", value = "1")
      }
  )
  @Id
  @GeneratedValue(generator = "clientSequenceGenerator")
  @Column(updatable = false)
  private long id;

  @Column(length = 50)
  private String clientIdAlias;

  @Column(length = 100)
  private String resourceIdStr;

  @Column(length = 100)
  private String clientSecretAlias;

  /**
   * Available values: read, write
   */
  @Column(length = 100)
  private String scopeStr;

  /**
   * grant types include "authorization_code", "password", "assertion", and "refresh_token". Default
   * description is "authorization_code,refresh_token".
   */
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
   * The access token validity period in seconds (optional). If unspecified a global default will be
   * applied by the token services.
   */
  @Builder.Default
  private int accessTokenValiditySecondsAlias = 1800;

  /**
   * The refresh token validity period in seconds (optional). If unspecified a global default will
   * be applied by the token services.
   */
  @Builder.Default
  private int refreshTokenValiditySecondsAlias = 3600;

  /**
   * Additional information for this client, not needed by the vanilla OAuth protocol but might be
   * useful, for example, for storing descriptive information.
   */
  private String additionalInformationStr;

  @Column(nullable = false, updatable = false)
  private long createdAt;

  @Column(nullable = false, updatable = false)
  private long createdBy;

  @Column(nullable = false)
  private long lastModifiedAt;

  @Column(nullable = false)
  private long lastModifiedBy;

  @Version
  @Column(nullable = false)
  private int version;

  @Column(nullable = false, length = 5000)
  private String publicKey;

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

  @Override public String getClientId() {
    return getClientIdAlias();
  }

  @Override public Set<String> getResourceIds() {
    return str2Set(getResourceIdStr());
  }

  @Override public boolean isSecretRequired() {
    return true;
  }

  @Override public String getClientSecret() {
    return getClientSecretAlias();
  }

  @Override public boolean isScoped() {
    return true;
  }

  @Override public Set<String> getScope() {
    return str2Set(getScopeStr());
  }

  @Override public Set<String> getAuthorizedGrantTypes() {
    return str2Set(getAuthorizedGrantTypeStr());
  }

  @Override public Set<String> getRegisteredRedirectUri() {
    return str2Set(getRegisteredRedirectUriStr());
  }

  @Override public Collection<GrantedAuthority> getAuthorities() {
    return Arrays.stream(getAuthorizedGrantTypeStr().split(CommonsConstant.COMMA))
        .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
  }

  @Override public Integer getAccessTokenValiditySeconds() {
    return getAccessTokenValiditySecondsAlias();
  }

  @Override public Integer getRefreshTokenValiditySeconds() {
    return null;
  }

  @Override public boolean isAutoApprove(String scope) {
    return false;
  }

  @Override public Map<String, Object> getAdditionalInformation() {
    return null;
  }

  private Set<String> str2Set(String str) {
    if (StringUtils.isBlank(str)) {
      return new HashSet<>();
    }
    return Sets
        .newHashSet(Arrays.stream(str.split(CommonsConstant.COMMA)).collect(Collectors.toList()));
  }
}
