package com.saintdan.framework.config.custom;

import java.util.List;
import net.kaczmarzyk.spring.data.jpa.web.SpecificationArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Custom configuration for specification.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 7/10/16
 * @since JDK1.8
 */
@Configuration
public class CustomSpecificationConfiguration extends WebMvcConfigurerAdapter {

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    argumentResolvers.add(new SpecificationArgumentResolver());
  }
}
