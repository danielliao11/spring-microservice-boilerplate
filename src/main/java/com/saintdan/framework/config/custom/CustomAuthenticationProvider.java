package com.saintdan.framework.config.custom;

import com.saintdan.framework.component.CustomPasswordEncoder;
import com.saintdan.framework.component.LogHelper;
import com.saintdan.framework.domain.UserDomain;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.enums.OperationType;
import com.saintdan.framework.po.OauthAccessToken;
import com.saintdan.framework.po.OauthRefreshToken;
import com.saintdan.framework.po.User;
import com.saintdan.framework.repo.OauthAccessTokenRepository;
import com.saintdan.framework.repo.OauthRefreshTokenRepository;
import com.saintdan.framework.repo.UserRepository;
import com.saintdan.framework.tools.LogUtils;
import com.saintdan.framework.tools.RemoteAddressUtils;
import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

/**
 * Custom authentication provider.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 12/9/15
 * @since JDK1.8
 */
@Service public class CustomAuthenticationProvider implements AuthenticationProvider {

  // ------------------------
  // PUBLIC METHODS
  // ------------------------

  @Override public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
    // Find user.
    String username = token.getName();
    User user = userDomain.findByAccount(username);
    if (user == null) {
      throw new BadCredentialsException(ErrorType.LOG0001.name());
    }
    // Get token object
    OauthAccessToken oauthAccessToken = accessTokenRepository.findByUserName(username).orElse(null);
    // Compare password and credentials of authentication.
    if (!customPasswordEncoder.matches(token.getCredentials().toString(), user.getPwd())) {
      throw new BadCredentialsException(ErrorType.LOG0002.name());
    }
    CustomUserRepositoryUserDetails userDetails = new CustomUserRepositoryUserDetails(user);
    // Valid account.
    if (!userDetails.isEnabled()) {
      throw new BadCredentialsException(ErrorType.LOG0003.name());
    } else if (!userDetails.isAccountNonExpired()) {
      throw new BadCredentialsException(ErrorType.LOG0004.name());
    } else if (!userDetails.isAccountNonLocked()) {
      throw new BadCredentialsException(ErrorType.LOG0005.name());
    } else if (!userDetails.isCredentialsNonExpired()) {
      throw new BadCredentialsException(ErrorType.LOG0006.name());
    }
    // Get client ip address.
    String ip = RemoteAddressUtils.getRealIp(request);
    // Delete token if repeat login.
    if (user.getIp() != null) {
      if (!user.getIp().equals(ip) && oauthAccessToken != null) {
        accessTokenRepository.delete(oauthAccessToken);
        OauthRefreshToken refreshToken = refreshTokenRepository.findByTokenId(oauthAccessToken.getTokenId()).orElse(null);
        if (refreshToken != null) {
          refreshTokenRepository.delete(refreshToken);
        }
      }
    }
    // Save user login info.
    user.setIp(ip);
    user.setLastLoginTime(LocalDateTime.now());
    userRepository.save(user);
    // Save to log.
    try {
      logHelper.logUsersOperations(OperationType.LOGIN, "login", user);
    } catch (Exception e) {
      final String errMsg = "Log user login failed.";
      LogUtils.traceError(logger, e, errMsg);
    }
    //Authorize.
    return new UsernamePasswordAuthenticationToken(userDetails, user.getPwd(), userDetails.getAuthorities());
  }

  @Override public boolean supports(Class<?> authentication) {
    return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
  }

  // --------------------------
  // PRIVATE FIELDS AND METHODS
  // --------------------------

  @Autowired private UserRepository userRepository;

  @Autowired private OauthAccessTokenRepository accessTokenRepository;

  @Autowired private OauthRefreshTokenRepository refreshTokenRepository;

  @Autowired private HttpServletRequest request;

  @Autowired private UserDomain userDomain;

  @Autowired private LogHelper logHelper;

  @Autowired private CustomPasswordEncoder customPasswordEncoder;

  private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

}
