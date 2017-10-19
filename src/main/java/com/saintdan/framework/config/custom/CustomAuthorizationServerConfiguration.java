package com.saintdan.framework.config.custom;

import com.saintdan.framework.tools.Assert;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 08/02/2017
 * @since JDK1.8
 */
@Configuration
@EnableAuthorizationServer
public class CustomAuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

  @Override public void configure(AuthorizationServerEndpointsConfigurer endpoints)
      throws Exception {
    endpoints
        .tokenStore(tokenStore())
        .authenticationManager(authenticationManager)
        .userDetailsService(userDetailsService);
  }

  @Override public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    // Use JDBC client.
    clients.withClientDetails(clientDetailsService);
  }

  // Token store type.
  @Bean public JdbcTokenStore tokenStore() {
    return new JdbcTokenStore(dataSource);
  }

  private final DataSource dataSource;
  private final AuthenticationManager authenticationManager;

  // When you use memory client, you can comment the custom client details service.
  private final CustomClientDetailsService clientDetailsService;
  private final CustomUserDetailsService userDetailsService;

  @Autowired public CustomAuthorizationServerConfiguration(DataSource dataSource,
      AuthenticationManager authenticationManager, CustomClientDetailsService clientDetailsService,
      CustomUserDetailsService userDetailsService) {
    Assert.defaultNotNull(dataSource);
    Assert.defaultNotNull(authenticationManager);
    Assert.defaultNotNull(clientDetailsService);
    Assert.defaultNotNull(userDetailsService);
    this.dataSource = dataSource;
    this.authenticationManager = authenticationManager;
    this.clientDetailsService = clientDetailsService;
    this.userDetailsService = userDetailsService;
  }
}
