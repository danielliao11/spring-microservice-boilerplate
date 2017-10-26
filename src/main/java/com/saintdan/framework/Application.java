package com.saintdan.framework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.scheduling.annotation.EnableAsync;

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
@EntityScan(
    basePackageClasses = { Application.class, Jsr310JpaConverters.class }
)
@SpringBootApplication
@EnableAsync
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
