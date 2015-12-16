package com.saintdan.framework.config;

import com.saintdan.framework.config.custom.CustomAuthenticationProvider;
import com.saintdan.framework.constant.ResourceURL;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

/**
 * Spring web security config.
 * Extends {@link WebSecurityConfigurerAdapter}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/25/15
 * @since JDK1.8
 */
@Configuration
@EnableWebSecurity
@EnableWebMvcSecurity
@EnableSpringDataWebSupport
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
	}

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .formLogin().usernameParameter("username").passwordParameter("password")
                .and()
                .authorizeRequests()
                .antMatchers(CLIENT_URL).hasAnyAuthority("root", "client")
                .antMatchers(USER_URL).hasAnyAuthority("root", "user")
                .antMatchers(ROLE_URL).hasAnyAuthority("root", "role")
                .antMatchers(GROUP_URL).hasAnyAuthority("root", "group")
                .antMatchers(RESOURCE_URL).hasAnyAuthority("root", "resource");
    }

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

    @Bean
    public AuthenticationProvider authenticationProvider(){
        return new CustomAuthenticationProvider();
    }

    private static final String FIX = "/**";
    private static final String CLIENT_URL = new String(new StringBuilder(FIX).append(ResourceURL.RESOURCES).append(ResourceURL.CLIENTS).append(FIX));
    private static final String USER_URL = new String(new StringBuilder(FIX).append(ResourceURL.RESOURCES).append(ResourceURL.USERS).append(FIX));
    private static final String ROLE_URL = new String(new StringBuilder(FIX).append(ResourceURL.RESOURCES).append(ResourceURL.USERS).append(FIX));
    private static final String GROUP_URL = new String(new StringBuilder(FIX).append(ResourceURL.RESOURCES).append(ResourceURL.USERS).append(FIX));
    private static final String RESOURCE_URL = new String(new StringBuilder(FIX).append(ResourceURL.RESOURCES).append(ResourceURL.USERS).append(FIX));

}
