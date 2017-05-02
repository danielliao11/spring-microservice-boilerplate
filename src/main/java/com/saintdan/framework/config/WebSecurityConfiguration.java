package com.saintdan.framework.config;

import com.saintdan.framework.component.CustomPasswordEncoder;
import com.saintdan.framework.component.LogHelper;
import com.saintdan.framework.config.custom.CustomAuthenticationProvider;
import com.saintdan.framework.domain.UserDomain;
import com.saintdan.framework.repo.OauthAccessTokenRepository;
import com.saintdan.framework.repo.OauthRefreshTokenRepository;
import com.saintdan.framework.repo.UserRepository;
import com.saintdan.framework.tools.Assert;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Spring web security config.
 * Extends {@link WebSecurityConfigurerAdapter}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/25/15
 * @since JDK1.8
 */
@Configuration @EnableWebSecurity @EnableSpringDataWebSupport
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final UserRepository userRepository;

  private final OauthAccessTokenRepository accessTokenRepository;

  private final OauthRefreshTokenRepository refreshTokenRepository;

  private final HttpServletRequest request;

  private final UserDomain userDomain;

  private final LogHelper logHelper;

  private final CustomPasswordEncoder customPasswordEncoder;

  public WebSecurityConfiguration(UserRepository userRepository, OauthAccessTokenRepository accessTokenRepository, OauthRefreshTokenRepository refreshTokenRepository, HttpServletRequest request, UserDomain userDomain, LogHelper logHelper, CustomPasswordEncoder customPasswordEncoder) {
    Assert.defaultNotNull(userRepository);
    Assert.defaultNotNull(accessTokenRepository);
    Assert.defaultNotNull(refreshTokenRepository);
    Assert.defaultNotNull(request);
    Assert.defaultNotNull(userDomain);
    Assert.defaultNotNull(logHelper);
    Assert.defaultNotNull(customPasswordEncoder);
    this.userRepository = userRepository;
    this.accessTokenRepository = accessTokenRepository;
    this.refreshTokenRepository = refreshTokenRepository;
    this.request = request;
    this.userDomain = userDomain;
    this.logHelper = logHelper;
    this.customPasswordEncoder = customPasswordEncoder;
  }

  @Override protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authenticationProvider());
  }

  @Override @Bean public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean public AuthenticationProvider authenticationProvider() {
    return new CustomAuthenticationProvider(userRepository, accessTokenRepository, refreshTokenRepository, request, userDomain, logHelper, customPasswordEncoder);
  }

}
