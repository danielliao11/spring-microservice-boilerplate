package com.saintdan.framework.starter.component;

import com.saintdan.framework.starter.domain.LogDomain;
import com.saintdan.framework.starter.po.Log;
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
  public void logLogin(String ip) {
    final String LOGIN = "login";
    logDomain.create(
        Log.builder()
            .ip(ip)
            .path(LOGIN)
            .method(HttpMethod.POST.name())
            .build());
  }

  private final LogDomain logDomain;

  @Autowired public LogHelper(LogDomain logDomain) {
    this.logDomain = logDomain;
  }
}
