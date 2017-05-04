package com.saintdan.framework;

import de.codecentric.boot.admin.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.HttpEncodingAutoConfiguration;
import org.springframework.boot.autoconfigure.web.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ServerPropertiesAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

/**
 * Entrance of <p>spring-rest-oauth2</p>
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
@EnableAdminServer
@EnableDiscoveryClient
// I don't use Jmx and web socket, so I comment them.
@Import({
    DispatcherServletAutoConfiguration.class,
    EmbeddedServletContainerAutoConfiguration.class,
    ErrorMvcAutoConfiguration.class,
    HttpEncodingAutoConfiguration.class,
    HttpMessageConvertersAutoConfiguration.class,
    JacksonAutoConfiguration.class,
//        JmxAutoConfiguration.class,
    MultipartAutoConfiguration.class,
    ServerPropertiesAutoConfiguration.class,
    PropertySourcesPlaceholderConfigurer.class,
    ThymeleafAutoConfiguration.class,
    WebMvcAutoConfiguration.class,
//        WebSocketAutoConfiguration.class,
})
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
