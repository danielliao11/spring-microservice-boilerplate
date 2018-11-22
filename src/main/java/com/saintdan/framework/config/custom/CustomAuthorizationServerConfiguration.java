package com.saintdan.framework.config.custom;

import com.saintdan.framework.tools.Assert;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 08/02/2017
 * @since JDK1.8
 */
@Configuration
@EnableAuthorizationServer
public class CustomAuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

  @Override public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
    endpoints
        .tokenStore(customTokenStore.customTokenStore()) // You can use redis / db token store.
        .authenticationManager(authenticationManager)
        .userDetailsService(userDetailsService);
  }

  @Override public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    // Use JDBC client.
    clients.withClientDetails(clientDetailsService);
  }

  @Bean(name = "customTokenServices")
  @Primary
  public DefaultTokenServices customTokenServices() {
    DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
    defaultTokenServices.setTokenStore(customTokenStore.customTokenStore());
    defaultTokenServices.setSupportRefreshToken(true);
    return defaultTokenServices;
  }



  private final AuthenticationManager authenticationManager;


  // When you use memory client, you can comment the custom client details service.
  private final CustomClientDetailsService clientDetailsService;
  private final CustomUserDetailsService userDetailsService;
  private final CustomTokenStore customTokenStore;

  @Autowired public CustomAuthorizationServerConfiguration(
      AuthenticationManager authenticationManager,
      CustomTokenStore customTokenStore,
      CustomClientDetailsService clientDetailsService,
      CustomUserDetailsService userDetailsService) {

    Assert.defaultNotNull(authenticationManager);
    Assert.defaultNotNull(clientDetailsService);
    Assert.defaultNotNull(userDetailsService);
    Assert.defaultNotNull(customTokenStore);
    this.authenticationManager = authenticationManager;
    this.clientDetailsService = clientDetailsService;
    this.userDetailsService = userDetailsService;
    this.customTokenStore = customTokenStore;
  }
}
