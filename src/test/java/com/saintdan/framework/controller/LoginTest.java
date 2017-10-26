package com.saintdan.framework.controller;

import com.saintdan.framework.constant.CommonsConstant;
import com.saintdan.framework.constant.ResourcePath;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 26/10/2017
 * @since JDK1.8
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LoginTest {

  @Test
  public void testLoginFailed() throws Exception {
    webTestClient.post()
        .uri(String
            .join(CommonsConstant.BLANK, ResourcePath.API, ResourcePath.V1, ResourcePath.OPEN,
                ResourcePath.LOGIN))
        .accept(MediaType.APPLICATION_JSON_UTF8)
        .exchange().expectStatus().isUnauthorized();
  }

  @Autowired private WebTestClient webTestClient;
}
