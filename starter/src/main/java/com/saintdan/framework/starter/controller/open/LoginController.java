package com.saintdan.framework.starter.controller.open;

import com.saintdan.framework.common.constant.ResourcePath;
import com.saintdan.framework.common.enums.ErrorType;
import com.saintdan.framework.common.tools.Assert;
import com.saintdan.framework.common.tools.ResponseHelper;
import com.saintdan.framework.starter.param.LoginParam;
import com.saintdan.framework.starter.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
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
      @ApiImplicitParam(name = "Authorization", value = "basic token", paramType = "header", dataType = "string", required = true),
  })
  public ResponseEntity login(HttpServletRequest request, @RequestBody LoginParam param) {
    try {
      return loginService.login(param, request);
    } catch (ClientRegistrationException e) {
      return ResponseHelper.clientError(ErrorType.CLIENT_REGISTER_ERROR.code(), e.getMessage());
    } catch (InvalidGrantException e) {
      return ResponseHelper.clientError(Integer.valueOf(e.getMessage()), ErrorType.parse(Integer.valueOf(e.getMessage())).msg());
    } catch (Exception e) {
      return ResponseHelper.unknownError(log, e);
    }
  }

  private final LoginService loginService;

  @Autowired public LoginController(LoginService loginService) {
    Assert.defaultNotNull(loginService);
    this.loginService = loginService;
  }
}

