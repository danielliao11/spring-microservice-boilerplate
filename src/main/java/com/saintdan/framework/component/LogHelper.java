package com.saintdan.framework.component;

import com.saintdan.framework.domain.LogDomain;
import com.saintdan.framework.po.Log;
import com.saintdan.framework.tools.RemoteAddressUtils;
import com.saintdan.framework.tools.SpringSecurityUtils;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

/**
 * Log users' operations.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 11/6/15
 * @since JDK1.8
 */
@Component
public class LogHelper {

  public void log(HttpServletRequest request) {
    String ip = RemoteAddressUtils.getRealIp(request);
    Log log = Log.builder()
        .usr(SpringSecurityUtils.getCurrentUsername())
        .ip(StringUtils.isBlank(ip) ? "0.0.0.0.0.0.0.0:1" : ip)
        .clientId(SpringSecurityUtils.getCurrentClientId())
        .path(request.getRequestURI().substring(request.getContextPath().length()))
        .method(HttpMethod.GET)
        .build();
    logDomain.create(log);
  }

  public void log(HttpMethod method, String usr, String ip, String clientId,
      String path) {
    Log log = Log.builder()
        .usr(usr)
        .ip(StringUtils.isBlank(ip) ? "0.0.0.0.0.0.0.0:1" : ip)
        .clientId(clientId)
        .path(path)
        .method(method)
        .build();
    logDomain.create(log);
  }

  public void log(HttpMethod method) {
    String ip = SpringSecurityUtils.getCurrentUserIp();
    Log log = Log.builder()
        .usr(SpringSecurityUtils.getCurrentUsername())
        .ip(StringUtils.isBlank(ip) ? "0.0.0.0.0.0.0.0:1" : ip)
        .clientId(SpringSecurityUtils.getCurrentClientId())
        .method(method)
        .build();
    logDomain.create(log);
  }

  public void log(HttpMethod method, String path) {
    String ip = SpringSecurityUtils.getCurrentUserIp();
    Log log = Log.builder()
        .usr(SpringSecurityUtils.getCurrentUsername())
        .ip(StringUtils.isBlank(ip) ? "0.0.0.0.0.0.0.0:1" : ip)
        .clientId(SpringSecurityUtils.getCurrentClientId())
        .path(path)
        .method(method)
        .build();
    logDomain.create(log);
  }

  private final LogDomain logDomain;

  @Autowired public LogHelper(LogDomain logDomain) {
    this.logDomain = logDomain;
  }
}
