package com.saintdan.framework.config.custom;

import com.google.common.collect.Sets;
import com.saintdan.framework.constant.CommonsConstant;
import com.saintdan.framework.enums.ValidFlag;
import com.saintdan.framework.po.Client;
import com.saintdan.framework.repo.ClientRepository;
import com.saintdan.framework.tools.Assert;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
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

  @Override public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
    Client client = clientRepository.findByClientIdAliasAndValidFlag(clientId, ValidFlag.VALID).orElseThrow(
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
      return Sets.newHashSet(Arrays.stream(str.split(CommonsConstant.COMMA)).collect(Collectors.toList()));
    }
  }

  private final ClientRepository clientRepository;

  @Autowired public CustomClientDetailsService(ClientRepository clientRepository) {
    Assert.defaultNotNull(clientRepository);
    this.clientRepository = clientRepository;
  }

}
