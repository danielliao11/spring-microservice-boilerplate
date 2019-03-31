package com.saintdan.framework.starter.config.custom;

import com.saintdan.framework.common.enums.ObjectStatus;
import com.saintdan.framework.starter.mapper.ClientMapper;
import com.saintdan.framework.starter.po.Client;
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

  @Override public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
    Client client = clientMapper.findByClient(clientId, ObjectStatus.VALID.code());
    if (client == null) {
      throw new ClientRegistrationException(String.format("Client %s hasn't registered!", clientId));
    }
    return client;
  }

  private final ClientMapper clientMapper;

  @Autowired public CustomClientDetailsService(ClientMapper clientMapper) {
    this.clientMapper = clientMapper;
  }
}
