package com.saintdan.framework.common.tools;

import com.saintdan.framework.common.constant.CommonsConstant;
import com.saintdan.framework.common.enums.GrantType;
import com.saintdan.framework.common.exception.IllegalTokenTypeException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.security.web.server.ServerHttpBasicAuthenticationConverter.BASIC;

/**
 * Login utils.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 08/04/2017
 * @since JDK1.8
 */
public class LoginUtils {

  public static String getClientId(HttpServletRequest request) throws IllegalTokenTypeException {
    String auth = request.getHeader(AUTHORIZATION);
    if (auth == null || !auth.toLowerCase().contains(BASIC)) {
      throw new IllegalTokenTypeException();
    }
    String clientId64 = new String(Base64.decodeBase64(auth.replace(BASIC, CommonsConstant.BLANK)));
    return clientId64.trim().substring(0, clientId64.indexOf(CommonsConstant.COLON));
  }

  public static Map<String, String> getParams(String usr, String pwd, String refreshToken) {
    Map<String, String> map = new HashMap<>();
    if (StringUtils.isNotBlank(usr)) {
      map.put(USERNAME, usr);
    }
    if (StringUtils.isNotBlank(pwd)) {
      map.put(PASSWORD, pwd);
      map.put(GRANT_TYPE, GrantType.PASSWORD.description());
    }
    if (StringUtils.isNotBlank(refreshToken)) {
      map.put(REFRESH_TOKEN, refreshToken);
      map.put(GRANT_TYPE, GrantType.REFRESH_TOKEN.description());
    }
    map.put(SCOPE, READ);
    return map;
  }

  private static final String USERNAME = "username";
  private static final String PASSWORD = "password";
  private static final String REFRESH_TOKEN = "refresh_token";
  private static final String GRANT_TYPE = "grant_type";
  private static final String SCOPE = "scope";
  private static final String READ = "read";
}
