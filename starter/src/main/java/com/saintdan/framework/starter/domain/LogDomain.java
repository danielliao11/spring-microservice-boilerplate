package com.saintdan.framework.starter.domain;

import com.saintdan.framework.starter.mapper.LogMapper;
import com.saintdan.framework.starter.po.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 2019/1/4
 * @since JDK1.8
 */
@Service
public class LogDomain {

  public int create(Log log) {
    return logMapper.insert(log);
  }

  private final LogMapper logMapper;

  @Autowired public LogDomain(LogMapper logMapper) {
    this.logMapper = logMapper;
  }
}
