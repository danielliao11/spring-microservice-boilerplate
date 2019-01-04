package com.saintdan.framework.component;

import com.saintdan.framework.domain.LogDomain;
import com.saintdan.framework.po.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 2019/1/4
 * @since JDK1.8
 */
@Component
public class LogHelper {

  @Async
  public void logLogin(String ip, String userId, String usr, String clientId) {
    final String LOGIN = "login";
    logDomain.create(
        Log.builder()
            .ip(ip)
            .createdBy(userId)
            .usr(usr)
            .clientId(clientId)
            .path(LOGIN)
            .method(HttpMethod.POST.name())
            .build());
  }

  private final LogDomain logDomain;

  @Autowired public LogHelper(LogDomain logDomain) {
    this.logDomain = logDomain;
  }
}
