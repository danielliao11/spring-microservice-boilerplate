package com.saintdan.framework.starter.component;

import com.saintdan.framework.starter.mapper.LogMapper;
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
  public void logLogin(String ip, String createdBy, String usr) {
    final String LOGIN = "login";
    logMapper.insert(
        Log.builder()
            .ip(ip)
            .createdBy(createdBy)
            .usr(usr)
            .createdAt(System.currentTimeMillis())
            .path(LOGIN)
            .method(HttpMethod.POST.name())
            .build());
  }

  private final LogMapper logMapper;

  @Autowired public LogHelper(LogMapper logMapper) {
    this.logMapper = logMapper;
  }
}
