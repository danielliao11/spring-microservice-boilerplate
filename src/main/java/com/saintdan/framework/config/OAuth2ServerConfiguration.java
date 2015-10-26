package com.saintdan.framework.config;

import com.saintdan.framework.config.client.CustomClientDetailsService;
import com.saintdan.framework.config.user.CustomUserDetailsService;
import com.saintdan.framework.constant.ResourceURL;
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
    private static final String USER_URL = new String(new StringBuilder(ResourceURL.RESOURCES).append(ResourceURL.USERS));
    private static final String ROLE_URL = new String(new StringBuilder(ResourceURL.RESOURCES).append(ResourceURL.ROLES));
    private static final String GROUP_URL = new String(new StringBuilder(ResourceURL.RESOURCES).append(ResourceURL.GROUPS));
    private static final String RESOURCE_URL = new String(new StringBuilder(ResourceURL.RESOURCES).append(ResourceURL.RESOURCES));

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
                    // User resource
                    .antMatchers(HttpMethod.GET, USER_URL).hasAnyAuthority("root", "user")
                    .antMatchers(HttpMethod.POST, USER_URL).hasAnyAuthority("root", "user")
                    .antMatchers(HttpMethod.PUT, USER_URL).hasAnyAuthority("root", "user")
                    .antMatchers(HttpMethod.DELETE, USER_URL).hasAnyAuthority("root", "user")

                    // role resource
                    .antMatchers(HttpMethod.GET, ROLE_URL).hasAnyAuthority("root", "role")
                    .antMatchers(HttpMethod.POST, ROLE_URL).hasAnyAuthority("root", "role")
                    .antMatchers(HttpMethod.PUT, ROLE_URL).hasAnyAuthority("root", "role")
                    .antMatchers(HttpMethod.DELETE, ROLE_URL).hasAnyAuthority("root", "role")

                    // group resource
                    .antMatchers(HttpMethod.GET, GROUP_URL).hasAnyAuthority("root", "group")
                    .antMatchers(HttpMethod.POST, GROUP_URL).hasAnyAuthority("root", "group")
                    .antMatchers(HttpMethod.PUT, GROUP_URL).hasAnyAuthority("root", "group")
                    .antMatchers(HttpMethod.DELETE, GROUP_URL).hasAnyAuthority("root", "group")

                    // resource resource
                    .antMatchers(HttpMethod.GET, RESOURCE_URL).hasAnyAuthority("root", "resource")
                    .antMatchers(HttpMethod.POST, RESOURCE_URL).hasAnyAuthority("root", "resource")
                    .antMatchers(HttpMethod.PUT, RESOURCE_URL).hasAnyAuthority("root", "resource")
                    .antMatchers(HttpMethod.DELETE, RESOURCE_URL).hasAnyAuthority("root", "resource")

                    // welcome resource
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

        // When you use memory client, you can comment the custom client details service.
        @Autowired
        private CustomClientDetailsService clientDetailsService;

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

//            // Use memory client.
//            // Or you can config them here use memory client.
//            clients
//                    .inMemory()
//                    .withClient("ios_app")
//                    .authorizedGrantTypes("password", "refresh_token")
//                    .authorities("USER")
//                    .scopes("read")
//                    .resourceIds(RESOURCE_ID)
//                    .secret("123456");
//            // You can add other clients like:
//            /*
//            clients
//                    .inMemory()
//                    .withClient("android_app")
//                    .authorizedGrantTypes("password", "refresh_token")
//                    .authorities("USER")
//                    .scopes("read")
//                    .resourceIds(RESOURCE_ID)
//                    .secret("654321");
//            */
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
