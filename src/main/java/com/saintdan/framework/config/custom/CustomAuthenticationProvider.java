package com.saintdan.framework.config.custom;

import com.saintdan.framework.component.CustomPasswordEncoder;
import com.saintdan.framework.component.LogHelper;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.enums.ObjectStatus;
import com.saintdan.framework.exception.IllegalTokenTypeException;
import com.saintdan.framework.mapper.UserMapper;
import com.saintdan.framework.po.User;
import com.saintdan.framework.tools.Assert;
import com.saintdan.framework.tools.LogUtils;
import com.saintdan.framework.tools.LoginUtils;
import com.saintdan.framework.tools.RemoteAddressUtils;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

/**
 * Custom authentication provider.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 12/9/15
 * @since JDK1.8
 */
@Service
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

  @Override public Authentication authenticate(Authentication authentication)
      throws AuthenticationException {
    UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
    String clientId;
    try {
      clientId = LoginUtils.getClientId(request);
    } catch (IllegalTokenTypeException e) {
      throw new BadCredentialsException(ErrorType.LOG0007.name());
    }
    // Find user.
    String usr = token.getName();
    User user = userMapper.findByUsr(usr, ObjectStatus.VALID.code());
    TokenStore tokenStore = customTokenStore.customTokenStore();
    if (user == null) {
      throw new BadCredentialsException(ErrorType.LOG0001.name());
    }
    // Compare password and credentials of authentication.
    if (!customPasswordEncoder.matches(token.getCredentials().toString(), user.getPwd())) {
      throw new BadCredentialsException(ErrorType.LOG0002.name());
    }
    // Valid account.
    if (!user.isEnabled()) {
      throw new BadCredentialsException(ErrorType.LOG0003.name());
    } else if (!user.isAccountNonExpired()) {
      throw new BadCredentialsException(ErrorType.LOG0004.name());
    } else if (!user.isAccountNonLocked()) {
      throw new BadCredentialsException(ErrorType.LOG0005.name());
    } else if (!user.isCredentialsNonExpired()) {
      throw new BadCredentialsException(ErrorType.LOG0006.name());
    }
    // Get client ip address.
    String ip = RemoteAddressUtils.getRealIp(request);
    // Delete token if repeat login.
    if (user.getIp() != null && !user.getIp().equals(ip)) {
      tokenStore.findTokensByClientIdAndUserName(clientId, usr)
          .forEach(oAuth2AccessToken -> {
            tokenStore.removeAccessToken(oAuth2AccessToken);
            tokenStore.removeRefreshToken(oAuth2AccessToken.getRefreshToken());
          });
    }
    // Save user login info.
    user.setIp(ip);
    user.setLastLoginAt(System.currentTimeMillis());
    try {
      // Log login.
      logHelper.logLogin(ip, user.getId(), usr, clientId);
    } catch (Exception e) {
      final String errMsg = "Log user login failed.";
      LogUtils.traceError(log, e, errMsg);
    }
    //Authorize.
    return new UsernamePasswordAuthenticationToken(user, user.getPwd(), user.getAuthorities());
  }

  @Override public boolean supports(Class<?> authentication) {
    return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
  }

  private final HttpServletRequest request;
  private final UserMapper userMapper;
  private final CustomTokenStore customTokenStore;
  private final LogHelper logHelper;
  private final CustomPasswordEncoder customPasswordEncoder = new CustomPasswordEncoder();

  @Autowired
  public CustomAuthenticationProvider(HttpServletRequest request, UserMapper userMapper, CustomTokenStore customTokenStore, LogHelper logHelper) {
    Assert.defaultNotNull(request);
    Assert.defaultNotNull(userMapper);
    Assert.defaultNotNull(customPasswordEncoder);
    Assert.defaultNotNull(logHelper);
    this.request = request;
    this.userMapper = userMapper;
    this.logHelper = logHelper;
    this.customTokenStore = customTokenStore;
  }
}
