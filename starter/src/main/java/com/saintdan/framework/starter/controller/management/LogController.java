package com.saintdan.framework.starter.controller.management;

import com.saintdan.framework.common.constant.ResourcePath;
import com.saintdan.framework.common.controller.QueryController;
import com.saintdan.framework.starter.domain.LogDomain;
import com.saintdan.framework.starter.po.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Api for {@link Log}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 2019/3/31
 * @since JDK1.8
 */
@RestController
@RequestMapping(ResourcePath.API + ResourcePath.V1 + ResourcePath.MANAGEMENT + ResourcePath.LOGS)
public class LogController extends QueryController<LogDomain, Log, String> {

  private final LogDomain logDomain;

  @Autowired
  public LogController(LogDomain logDomain) {
    this.logDomain = logDomain;
    setDomain(logDomain);
  }
}
