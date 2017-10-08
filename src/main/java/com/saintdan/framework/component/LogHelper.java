package com.saintdan.framework.component;

import com.saintdan.framework.domain.LogDomain;
import com.saintdan.framework.enums.OperationType;
import com.saintdan.framework.po.Log;
import com.saintdan.framework.tools.RemoteAddressUtils;
import com.saintdan.framework.tools.SpringSecurityUtils;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Log users' operations.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 11/6/15
 * @since JDK1.8
 */
@Component public class LogHelper {

  public void log(HttpServletRequest request) {
    // Get ip and clientId
    String ip = RemoteAddressUtils.getRealIp(request);
    Log log = new Log();
    log.setUsr(SpringSecurityUtils.getCurrentUsername());
    log.setIp(StringUtils.isBlank(ip) ? "0.0.0.0.0.0.0.0:1" : ip);
    log.setClientId(SpringSecurityUtils.getCurrentClientId());
    log.setPath(request.getRequestURI().substring(request.getContextPath().length()));
    log.setCreatedAt(System.currentTimeMillis());
    log.setOperationType(OperationType.READ);
    logDomain.create(log);
  }

  public void log(OperationType operationType, String usr, String ip, String clientId, String path) {
    Log log = new Log();
    log.setUsr(usr);
    log.setIp(StringUtils.isBlank(ip) ? "0.0.0.0.0.0.0.0:1" : ip);
    log.setClientId(clientId);
    log.setCreatedAt(System.currentTimeMillis());
    log.setOperationType(operationType);
    log.setPath(path);
    logDomain.create(log);
  }

  public void log(OperationType operationType) {
    Log log = new Log();
    String ip = SpringSecurityUtils.getCurrentUserIp();
    log.setUsr(SpringSecurityUtils.getCurrentUsername());
    log.setIp(StringUtils.isBlank(ip) ? "0.0.0.0.0.0.0.0:1" : ip);
    log.setClientId(SpringSecurityUtils.getCurrentClientId());
    log.setCreatedAt(System.currentTimeMillis());
    log.setOperationType(operationType);
    logDomain.create(log);
  }

  public void log(OperationType operationType, String path) {
    Log log = new Log();
    String ip = SpringSecurityUtils.getCurrentUserIp();
    log.setUsr(SpringSecurityUtils.getCurrentUsername());
    log.setIp(StringUtils.isBlank(ip) ? "0.0.0.0.0.0.0.0:1" : ip);
    log.setClientId(SpringSecurityUtils.getCurrentClientId());
    log.setCreatedAt(System.currentTimeMillis());
    log.setOperationType(operationType);
    log.setPath(path);
    logDomain.create(log);
  }

  private final LogDomain logDomain;

  @Autowired public LogHelper(LogDomain logDomain) {
    this.logDomain = logDomain;
  }

}
