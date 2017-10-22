package com.saintdan.framework.tools;

import static com.saintdan.framework.constant.AuthorityConstant.BASIC;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.saintdan.framework.constant.CommonsConstant;
import com.saintdan.framework.enums.GrantType;
import com.saintdan.framework.exception.IllegalTokenTypeException;
import com.saintdan.framework.param.LoginParam;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

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

  public static Map<String, String> getParams(LoginParam param) {
    Map<String, String> map = new HashMap<>();
    if (StringUtils.isNotBlank(param.getUsr())) {
      map.put(USERNAME, param.getUsr());
    }
    if (StringUtils.isNotBlank(param.getPwd())) {
      map.put(PASSWORD, param.getPwd());
      map.put(GRANT_TYPE, GrantType.PASSWORD.description());
    }
    if (StringUtils.isNotBlank(param.getRefreshToken())) {
      map.put(REFRESH_TOKEN, param.getRefreshToken());
      map.put(GRANT_TYPE, GrantType.REFRESH_TOKEN.description());
    }
    map.put(SCOPE, READ);
    return map;
  }

  private static final String USERNAME = "username";
  private static final String PASSWORD = "password";
  private final static String REFRESH_TOKEN = "refresh_token";
  private static final String GRANT_TYPE = "grant_type";
  private static final String SCOPE = "scope";
  private static final String READ = "read";
}
