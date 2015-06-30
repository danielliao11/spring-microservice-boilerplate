package com.github.saintdan.config;

import com.github.saintdan.service.CustomUserDetailsService;
import com.github.saintdan.tools.CustomPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;

/**
 * Global authentication config extends
 * {@link GlobalAuthenticationConfigurerAdapter}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/29/15
 * @since JDK1.8
 */
@Configuration
public class GlobalAuthenticationConfig extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private CustomPasswordEncoder customPasswordEncoder;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(customPasswordEncoder);

    }

}
