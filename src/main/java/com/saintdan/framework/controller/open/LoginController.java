package com.saintdan.framework.controller.open;

import com.saintdan.framework.constant.ResourcePath;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.param.LoginParam;
import com.saintdan.framework.service.LoginService;
import com.saintdan.framework.tools.Assert;
import com.saintdan.framework.tools.ResponseHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api("Login")
@Slf4j
@RestController
@RequestMapping(ResourcePath.API + ResourcePath.V1 + ResourcePath.OPEN + ResourcePath.LOGIN)
public class LoginController {

  @RequestMapping(method = RequestMethod.POST)
  @ApiOperation(value = "Login", httpMethod = "POST", response = OAuth2AccessToken.class)
  @ApiImplicitParams({
      @ApiImplicitParam(name = "Authorization", value = "token", paramType = "header", dataType = "string", required = true),
  })
  public ResponseEntity login(HttpServletRequest request, @RequestBody LoginParam param) {
    try {
      return loginService.login(param, request);
    } catch (ClientRegistrationException e) {
      return ResponseHelper.clientError(ErrorType.CLIENT_REGISTER_ERROR.code(), e.getMessage());
    } catch (Exception e) {
      return ResponseHelper.unknownError();
    }
  }

  private final LoginService loginService;

  @Autowired public LoginController(LoginService loginService) {
    Assert.defaultNotNull(loginService);
    this.loginService = loginService;
  }
}

