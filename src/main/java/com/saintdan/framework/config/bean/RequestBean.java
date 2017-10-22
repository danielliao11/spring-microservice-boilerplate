package com.saintdan.framework.config.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 22/10/2017
 * @since JDK1.8
 */
@Component
@Data
@ConfigurationProperties(prefix = "request")
public class RequestBean {

  private long range;
  private int count;
}
