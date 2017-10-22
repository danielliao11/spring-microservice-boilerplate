package com.saintdan.framework.service.impl;

import com.saintdan.framework.param.LoginParam;
import com.saintdan.framework.service.LoginService;
import com.saintdan.framework.tools.Assert;
import com.saintdan.framework.tools.LoginUtils;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.stereotype.Service;

/**
 * Implements the {@link LoginService}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 23/01/2017
 * @since JDK1.8
 */
@Service
public class LoginServiceImpl implements LoginService {

  @Override public ResponseEntity login(LoginParam param, HttpServletRequest request)
      throws Exception {
    return execute(param, request);
  }

  @Override public ResponseEntity refresh(LoginParam param, HttpServletRequest request)
      throws Exception {
    return execute(param, request);
  }

  private static final String AUTHORITY_PROP = "client.authorities";

  private ResponseEntity execute(LoginParam param, HttpServletRequest request) throws Exception {
    final List<GrantedAuthority> CLIENT_AUTHORITIES = AuthorityUtils
        .commaSeparatedStringToAuthorityList(environment.getProperty(AUTHORITY_PROP));
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
        LoginUtils.getClientId(request), "", CLIENT_AUTHORITIES);
    Map<String, String> params = LoginUtils.getParams(param);
    return tokenEndpoint.postAccessToken(token, params);
  }

  private final TokenEndpoint tokenEndpoint;
  private final Environment environment;

  @Autowired public LoginServiceImpl(TokenEndpoint tokenEndpoint, Environment environment) {
    Assert.defaultNotNull(tokenEndpoint);
    Assert.defaultNotNull(environment);
    this.tokenEndpoint = tokenEndpoint;
    this.environment = environment;
  }
}
