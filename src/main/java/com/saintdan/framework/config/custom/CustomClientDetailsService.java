package com.saintdan.framework.config.custom;

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
    // get client
    return null;
  }
}
