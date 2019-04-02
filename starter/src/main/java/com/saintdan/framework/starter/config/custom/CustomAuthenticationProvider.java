package com.saintdan.framework.starter.config.custom;

import com.saintdan.framework.common.component.CustomPasswordEncoder;
import com.saintdan.framework.common.enums.ErrorType;
import com.saintdan.framework.common.enums.ObjectStatus;
import com.saintdan.framework.common.exception.IllegalTokenTypeException;
import com.saintdan.framework.common.tools.Assert;
import com.saintdan.framework.common.tools.LogUtils;
import com.saintdan.framework.common.tools.LoginUtils;
import com.saintdan.framework.common.tools.RemoteAddressUtils;
import com.saintdan.framework.common.tools.SpringContextUtils;
import com.saintdan.framework.starter.component.LogHelper;
import com.saintdan.framework.starter.mapper.UserMapper;
import com.saintdan.framework.starter.po.User;
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

  @Override public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
    String clientId;
    try {
      clientId = LoginUtils.getClientId(request);
    } catch (IllegalTokenTypeException e) {
      throw new BadCredentialsException(ErrorType.ILLEGAL_TOKEN_TYPE_ERROR.code().toString());
    }
    // Find user.
    String usr = token.getName();
    User user = userMapper.findByUsr(usr, ObjectStatus.VALID.code());
    TokenStore tokenStore = customTokenStore.customTokenStore();
    if (user == null) {
      throw new BadCredentialsException(ErrorType.NO_SUCH_ELEMENT_ERROR.code().toString());
    }
    // Compare password and credentials of authentication.
    if (!customPasswordEncoder.matches(token.getCredentials().toString(), user.getPwd())) {
      throw new BadCredentialsException(ErrorType.USERNAME_OR_PASSWORD_IS_WRONG_ERROR.code().toString());
    }
    // Valid account.
    if (!user.isEnabled() || !user.isAccountNonExpired() || !user.isAccountNonLocked() || !user.isCredentialsNonExpired()) {
      throw new BadCredentialsException(ErrorType.INVALID_USER_ERROR.code().toString());
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
    // Record context.
    recordContext(user);
    try {
      // Log login.
      logHelper.logLogin(ip, user.getCreatedBy(), usr);
    } catch (Exception e) {
      final String errMsg = "Log user login failed.";
      LogUtils.traceError(log, e, errMsg);
    }
    // Authorize.
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

  private void recordContext(User user) {
    SpringContextUtils.setUsr(user.getUsr());
    SpringContextUtils.setUserID(user.getId());
    SpringContextUtils.setName(user.getName());
  }
}
