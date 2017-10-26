package com.saintdan.framework.config.custom;

import com.saintdan.framework.constant.ResourcePath;
import java.util.Collections;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * OAuth2 server configuration.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/30/15
 * @since JDK1.8
 */
@Configuration
public class CustomResourceServerConfiguration {

  /**
   * Resource of api
   *
   * @return {@link ResourceServerConfiguration}
   */
  @Bean protected ResourceServerConfiguration adminResources() {

    ResourceServerConfiguration resource = new ResourceServerConfiguration() {
      // Switch off the Spring Boot @Autowired configurers
      public void setConfigurers(List<ResourceServerConfigurer> configurers) {
        super.setConfigurers(configurers);
      }
    };

    resource.setConfigurers(Collections.singletonList(new ResourceServerConfigurerAdapter() {

      @Override public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(RESOURCE_ID);
      }

      @Override public void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers(OPEN_URL).permitAll()
            .antMatchers(MANAGEMENT_URL).hasAnyAuthority("root", "management")
            .antMatchers(APP_URL).hasAnyAuthority("root", "management", "app");
      }
    }));

    resource.setOrder(1);

    return resource;
  }

  private static final String RESOURCE_ID = "api";
  private static final String MANAGEMENT_URL = getURL(ResourcePath.MANAGEMENT);
  private static final String APP_URL = getURL(ResourcePath.APP);
  private static final String OPEN_URL = getURL(ResourcePath.OPEN);

  private static String getURL(CharSequence element) {
    return String.join("", ResourcePath.FIX, ResourcePath.API, ResourcePath.V1, element,
        ResourcePath.FIX);
  }
}
