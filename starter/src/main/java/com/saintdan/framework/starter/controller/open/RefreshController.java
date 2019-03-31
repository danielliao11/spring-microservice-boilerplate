package com.saintdan.framework.starter.controller.open;

import com.saintdan.framework.common.constant.ResourcePath;
import com.saintdan.framework.common.enums.ErrorType;
import com.saintdan.framework.common.exception.IllegalTokenTypeException;
import com.saintdan.framework.common.tools.Assert;
import com.saintdan.framework.common.tools.ResponseHelper;
import com.saintdan.framework.starter.param.RefreshParam;
import com.saintdan.framework.starter.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 15/02/2017
 * @since JDK1.8
 */
@Api("refresh token")
@Slf4j
@RestController
@RequestMapping(ResourcePath.API + ResourcePath.V1 + ResourcePath.OPEN + ResourcePath.REFRESH)
public class RefreshController {

  @RequestMapping(method = RequestMethod.PUT)
  @ApiOperation(value = "refresh token", httpMethod = "POST", response = OAuth2AccessToken.class)
  @ApiImplicitParam(name = "Authorization", value = "token", paramType = "header", dataType = "string", required = true)
  public ResponseEntity refresh(@RequestBody RefreshParam param, @ApiIgnore HttpServletRequest request) {
    try {
      return loginService.refresh(param, request);
    } catch (IllegalTokenTypeException e) {
      return ResponseHelper.clientError(e.code(), e.message());
    } catch (ClientRegistrationException e) {
      return ResponseHelper.clientError(ErrorType.CLIENT_REGISTER_ERROR.code(), e.getMessage());
    } catch (Exception e) {
      return ResponseHelper.unknownError();
    }
  }

  private final LoginService loginService;

  public RefreshController(LoginService loginService) {
    Assert.defaultNotNull(loginService);
    this.loginService = loginService;
  }
}
