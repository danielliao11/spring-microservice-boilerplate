package com.saintdan.framework.config;

import com.saintdan.framework.interceptor.LogInterceptor;
import java.util.List;
import net.kaczmarzyk.spring.data.jpa.web.SpecificationArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 23/10/2017
 * @since JDK1.8
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

  @Override public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(new SpecificationArgumentResolver());
  }

  @Override public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(logInterceptor);
  }

  private final LogInterceptor logInterceptor;

  @Autowired public WebMvcConfiguration(LogInterceptor logInterceptor) {
    this.logInterceptor = logInterceptor;
  }
}
