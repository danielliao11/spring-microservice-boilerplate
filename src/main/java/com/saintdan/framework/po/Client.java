package com.saintdan.framework.po;

import com.saintdan.framework.enums.ValidFlag;
import com.saintdan.framework.listener.CreatedAtPersistentListener;
import java.io.Serializable;
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
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Authorized client, provide for spring security.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/23/15
 * @since JDK1.8
 */
@Entity @EntityListeners(CreatedAtPersistentListener.class) @Table(name = "clients")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Client implements Serializable {

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
   * Default description is "authorization_code,refresh_token".
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
  @Builder.Default
  private int accessTokenValiditySecondsAlias = 1800;

  /**
   * The refresh token validity period in seconds (optional).
   * If unspecified a global default will  be applied by the token services.
   */
  @Builder.Default
  private int refreshTokenValiditySecondsAlias = 3600;

  /**
   * Additional information for this client, not needed by the vanilla OAuth protocol but might be useful, for example,
   * for storing descriptive information.
   */
  private String additionalInformationStr;

  @Column(nullable = false)
  @Builder.Default
  private ValidFlag validFlag = ValidFlag.VALID;

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
}
