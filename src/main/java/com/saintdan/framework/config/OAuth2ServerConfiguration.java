package com.saintdan.framework.config;

import com.saintdan.framework.config.custom.CustomClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * OAuth2 server configuration.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/30/15
 * @since JDK1.8
 */
@Configuration
public class OAuth2ServerConfiguration {

    /**
     * OAuth2 authorization server configuration.
     */
    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends
            AuthorizationServerConfigurerAdapter {

        // Token store type.
        private TokenStore tokenStore = new InMemoryTokenStore();

        @Autowired
        @Qualifier("authenticationManagerBean")
        private AuthenticationManager authenticationManager;

        // When you use memory client, you can comment the custom client details service.
        @Autowired
        private CustomClientDetailsService clientDetailsService;

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints)
                throws Exception {
            endpoints
                    .tokenStore(this.tokenStore)
                    .authenticationManager(this.authenticationManager);
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            // Use JDBC client.
            // If you have many clients, you can use JDBC client.
            clients.withClientDetails(clientDetailsService);

            // Use memory client.
            // Or you can config them here use memory client.
            /*
            clients
                    .inMemory()
                    .withClient("ios_app")
                    .authorizedGrantTypes("password", "refresh_token")
                    .authorities("USER")
                    .scopes("read")
                    .resourceIds(RESOURCE_ID)
                    .secret("123456");
            */
            // You can add other clients like:
            /*
            clients
                    .inMemory()
                    .withClient("android_app")
                    .authorizedGrantTypes("password", "refresh_token")
                    .authorities("USER")
                    .scopes("read")
                    .resourceIds(RESOURCE_ID)
                    .secret("654321");
            */
        }
    }

}
