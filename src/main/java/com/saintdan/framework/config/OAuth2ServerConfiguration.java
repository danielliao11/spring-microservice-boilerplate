package com.saintdan.framework.config;

import com.saintdan.framework.config.custom.CustomClientDetailsService;
import com.saintdan.framework.config.custom.CustomUserDetailsService;
import com.saintdan.framework.constant.ResourceURL;
import com.saintdan.framework.constant.VersionConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.*;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import java.util.Arrays;
import java.util.List;

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
     * Resource of admin
     *
     * @return      {@link ResourceServerConfiguration}
     */
    @Bean
    protected ResourceServerConfiguration adminResources() {

        ResourceServerConfiguration resource = new org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfiguration() {
            // Switch off the Spring Boot @Autowired configurers
            public void setConfigurers(List<ResourceServerConfigurer> configurers) {
                super.setConfigurers(configurers);
            }
        };

        resource.setConfigurers(Arrays.<ResourceServerConfigurer> asList(new ResourceServerConfigurerAdapter() {

            @Override
            public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
                resources.resourceId(ADMIN_RESOURCE_ID);
            }

            @Override
            public void configure(HttpSecurity http) throws Exception {
                http
                        .csrf().disable()
                        .authorizeRequests()
                        .antMatchers(CLIENT_URL).hasAnyAuthority("root", "client")
                        .antMatchers(USER_URL).hasAnyAuthority("root", "user")
                        .antMatchers(ROLE_URL).hasAnyAuthority("root", "role")
                        .antMatchers(GROUP_URL).hasAnyAuthority("root", "group")
                        .antMatchers(RESOURCE_URL).hasAnyAuthority("root", "resource");
            }

        }));

        resource.setOrder(3);

        return resource;
    }

    /**
     * Resource of wechat
     *
     * @return      {@link ResourceServerConfiguration}
     */
    @Bean
    protected ResourceServerConfiguration wechatResources() {

        ResourceServerConfiguration resource = new org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfiguration() {
            // Switch off the Spring Boot @Autowired configurers
            public void setConfigurers(List<ResourceServerConfigurer> configurers) {
                super.setConfigurers(configurers);
            }
        };

        resource.setConfigurers(Arrays.<ResourceServerConfigurer> asList(new ResourceServerConfigurerAdapter() {

            @Override
            public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
                resources.resourceId(IOS_RESOURCE_ID);
            }

            @Override
            public void configure(HttpSecurity http) throws Exception {
                http
                        .csrf().disable()
                        .authorizeRequests()
                        .antMatchers(HttpMethod.GET, IOS_URL).hasAnyAuthority("root", "management", "ios");
            }

        }));

        resource.setOrder(4);

        return resource;

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
        private AuthenticationManager authenticationManager;

        // When you use memory client, you can comment the custom client details service.
        @Autowired
        private CustomClientDetailsService clientDetailsService;

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

    private static final String ADMIN_RESOURCE_ID = "admin";
    private static final String IOS_RESOURCE_ID = "ios_app";
    private static final String CLIENT_URL = getURL(ResourceURL.CLIENTS);
    private static final String USER_URL = getURL(ResourceURL.USERS);
    private static final String ROLE_URL = getURL(ResourceURL.ROLES);
    private static final String GROUP_URL = getURL(ResourceURL.GROUPS);
    private static final String RESOURCE_URL = getURL(ResourceURL.RESOURCES);
    private static final String IOS_URL = getURL(ResourceURL.IOS);

    private static String getURL(CharSequence element) {
        return String.join(ResourceURL.FIX, ResourceURL.RESOURCES, VersionConstant.V1, element, ResourceURL.FIX);
    }
}
