package com.saintdan.framework.starter.config;

import com.saintdan.framework.starter.interceptor.CustomInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 23/10/2017
 * @since JDK1.8
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

  @Override public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(customInterceptor);
  }

  private final CustomInterceptor customInterceptor;

  @Autowired public WebMvcConfiguration(CustomInterceptor customInterceptor) {
    this.customInterceptor = customInterceptor;
  }
}
