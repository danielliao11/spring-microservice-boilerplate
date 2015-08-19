package com.github.saintdan.config;

import com.github.saintdan.constant.Resource;
import com.github.saintdan.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
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

    private static final String RESOURCE_ID = "rest_api";

    private static final String WELCOME_URL = "/welcome";
    private static final StringBuilder USER_URL = new StringBuilder(Resource.RESOURCES).append(Resource.USERS);

    /**
     * OAuth2 resource server configuration.
     */
    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends
            ResourceServerConfigurerAdapter {

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources
                    .resourceId(RESOURCE_ID);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .and()
                        .authorizeRequests()
                            // Since resources can be used as its authorities, therefore, hasRole utilize resource as its' param.
                            .antMatchers(HttpMethod.GET, USER_URL.toString()).access("hasAnyRole('root', 'admin', 'resources')")
                            .antMatchers(HttpMethod.GET, WELCOME_URL).access("#oauth2.hasScope('read')");
        }

    }

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

        @Autowired
        private CustomUserDetailsService userDetailsService;

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints)
                throws Exception {
            endpoints
                    .tokenStore(this.tokenStore)
                    .authenticationManager(this.authenticationManager)
                    .userDetailsService(userDetailsService);
        }


        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients
                    .inMemory()
                    .withClient("ios_app")
                    .authorizedGrantTypes("password", "refresh_token")
                    .authorities("USER")
                    .scopes("read")
                    .resourceIds(RESOURCE_ID)
                    .secret("123456");
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

        @Bean
        @Primary
        public DefaultTokenServices tokenServices() {
            DefaultTokenServices tokenServices = new DefaultTokenServices();
            tokenServices.setSupportRefreshToken(true);
            tokenServices.setTokenStore(this.tokenStore);
            return tokenServices;
        }

    }

}
