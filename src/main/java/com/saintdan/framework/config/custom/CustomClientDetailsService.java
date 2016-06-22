package com.saintdan.framework.config.custom;

import com.saintdan.framework.po.Client;
import com.saintdan.framework.repo.ClientRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

/**
 * Custom client service.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/23/15
 * @since JDK1.8
 */
@Service public class CustomClientDetailsService implements ClientDetailsService {

  private final ClientRepository clientRepository;

  private final static String COMMA = ",";

  @Autowired public CustomClientDetailsService(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @Override public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
    Client client = clientRepository.findByClientIdAlias(clientId).orElseThrow(
        () -> new ClientRegistrationException(String.format("Client %s does not exist!", clientId)));
    return new ClientRepositoryClientDetails(client);
  }

  private final static class ClientRepositoryClientDetails extends Client implements ClientDetails {

    private static final long serialVersionUID = -6463921194751090819L;

    private ClientRepositoryClientDetails(Client client) {
      super(client);
    }

    @Override public String getClientId() {
      return getClientIdAlias();
    }

    @Override public Set<String> getResourceIds() {
      String[] idArray = getResourceIdStr().split(COMMA);
      Set<String> resourceIds = new HashSet<>();
      Collections.addAll(resourceIds, idArray);
      return resourceIds;
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
      String[] scopeArray = getScopeStr().split(COMMA);
      Set<String> scopes = new HashSet<>();
      Collections.addAll(scopes, scopeArray);
      return scopes;
    }

    @Override public Set<String> getAuthorizedGrantTypes() {
      String[] authorizedGrantTypeArray = getAuthorizedGrantTypeStr().split(COMMA);
      Set<String> grantTypes = new HashSet<>();
      Collections.addAll(grantTypes, authorizedGrantTypeArray);
      return grantTypes;
    }

    @Override public Set<String> getRegisteredRedirectUri() {
      String[] redirectUriArray = getRegisteredRedirectUriStr().split(COMMA);
      Set<String> uris = new HashSet<>();
      Collections.addAll(uris, redirectUriArray);
      return uris;
    }

    @Override public Collection<GrantedAuthority> getAuthorities() {
      GrantedAuthority authority = new SimpleGrantedAuthority("USER");
      Collection<GrantedAuthority> authorities = new ArrayList<>();
      authorities.add(authority);
      return authorities;
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
  }

}
