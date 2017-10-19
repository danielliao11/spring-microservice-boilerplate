package com.saintdan.framework.controller.open;

import com.saintdan.framework.component.ResultHelper;
import com.saintdan.framework.component.ValidateHelper;
import com.saintdan.framework.constant.ResourceURL;
import com.saintdan.framework.constant.VersionConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.enums.GrantType;
import com.saintdan.framework.exception.IllegalTokenTypeException;
import com.saintdan.framework.param.LoginParam;
import com.saintdan.framework.service.LoginService;
import com.saintdan.framework.tools.Assert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api("Login")
@RestController
@RequestMapping(ResourceURL.RESOURCES + VersionConstant.V1 + ResourceURL.OPEN + ResourceURL.LOGIN)
public class LoginController {

  @RequestMapping(method = RequestMethod.POST)
  @ApiOperation(value = "Login", httpMethod = "POST", response = OAuth2AccessToken.class)
  @ApiImplicitParam(name = "Authorization", value = "token", paramType = "header", dataType = "string", required = true)
  public ResponseEntity postAccessToken(HttpServletRequest request, @RequestBody LoginParam param)
      throws HttpRequestMethodNotSupportedException {
    // Validate current user, param and sign.
    ResponseEntity responseEntity;
    try {
      responseEntity = validateHelper.validate(request, param, GrantType.PASSWORD);
    } catch (IllegalTokenTypeException e) {
      return resultHelper.infoResp(e.getErrorType(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
    if (!responseEntity.getStatusCode().is2xxSuccessful()) {
      return responseEntity;
    }
    try {
      return loginService.login(param, request);
    } catch (Exception e) {
      // Return unknown error and log the exception.
      return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage(),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
  private final LoginService loginService;
  private final ResultHelper resultHelper;
  private final ValidateHelper validateHelper;

  @Autowired public LoginController(LoginService loginService, ResultHelper resultHelper,
      ValidateHelper validateHelper) {
    Assert.defaultNotNull(loginService);
    Assert.defaultNotNull(resultHelper);
    Assert.defaultNotNull(validateHelper);
    this.loginService = loginService;
    this.resultHelper = resultHelper;
    this.validateHelper = validateHelper;
  }
}

