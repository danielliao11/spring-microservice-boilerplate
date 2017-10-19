package com.saintdan.framework.vo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * VO for {@link com.saintdan.framework.po.Client}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/25/15
 * @since JDK1.8
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
}
