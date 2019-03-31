package com.saintdan.framework.starter.config.custom;

import com.saintdan.framework.common.component.CustomPasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 2019-03-31
 * @since JDK1.8
 */
@Configuration
public class CustomConfiguration {

  @Bean
  public CustomPasswordEncoder passwordEncoder() {
    return new CustomPasswordEncoder();
  }
}
