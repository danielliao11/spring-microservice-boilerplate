package com.saintdan.framework;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Entrance of <p>spring-micro-services-boilerplate</p>
 * <p>
 * "Engine start"
 * </p>
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/25/15
 * @since JDK1.8
 */
@SpringBootApplication
@EnableAsync
@EnableSwagger2Doc
@MapperScan(basePackages = "com.saintdan.framework.mapper")
// I don't use Jmx and web socket, so I comment them.
@Import({
    JacksonAutoConfiguration.class,
    PropertySourcesPlaceholderConfigurer.class,
    ThymeleafAutoConfiguration.class,
})
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
