package com.saintdan.framework.config.custom;

import com.saintdan.framework.repo.ClientRepository;
import com.saintdan.framework.tools.Assert;
import org.springframework.beans.factory.annotation.Autowired;
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
@Service
public class CustomClientDetailsService implements ClientDetailsService {

  @Override public ClientDetails loadClientByClientId(String clientId)
      throws ClientRegistrationException {
    return clientRepository.findByClientIdAlias(clientId).orElseThrow(
        () -> new ClientRegistrationException(
            String.format("Client %s does not exist!", clientId)));
  }

  private final ClientRepository clientRepository;

  @Autowired public CustomClientDetailsService(ClientRepository clientRepository) {
    Assert.defaultNotNull(clientRepository);
    this.clientRepository = clientRepository;
  }
}
