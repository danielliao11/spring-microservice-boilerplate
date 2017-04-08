package com.saintdan.framework.controller.open;

import com.saintdan.framework.constant.ResourceURL;
import com.saintdan.framework.constant.VersionConstant;
import com.saintdan.framework.param.LoginParam;
import com.saintdan.framework.tools.LoginUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api("Login") @RestController @RequestMapping(ResourceURL.RESOURCES + VersionConstant.V1 + ResourceURL.OPEN + ResourceURL.LOGIN)
public class LoginController {

  @RequestMapping(method = RequestMethod.POST)
  @ApiOperation(value = "Login", httpMethod = "POST", response = OAuth2AccessToken.class)
  @ApiImplicitParam(name = "Authorization", value = "token", paramType = "header", dataType = "string", required = true)
  public ResponseEntity postAccessToken(HttpServletRequest request, @RequestBody LoginParam param) throws HttpRequestMethodNotSupportedException {
    final List<GrantedAuthority> CLIENT_AUTHORITIES = AuthorityUtils.commaSeparatedStringToAuthorityList(environment.getProperty(AUTHORITY_PROP));
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(LoginUtils.getClentId(request), "", CLIENT_AUTHORITIES);
    Map<String, String> params = LoginUtils.getParams(param);
    return tokenEndpoint.postAccessToken(token, params);
  }

  @Autowired private Environment environment;

  @Autowired private TokenEndpoint tokenEndpoint;

  private static final String AUTHORITY_PROP = "client.authorities";

}

