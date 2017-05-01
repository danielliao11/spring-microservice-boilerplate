package com.saintdan.framework.controller.open;

import com.saintdan.framework.component.ResultHelper;
import com.saintdan.framework.component.ValidateHelper;
import com.saintdan.framework.constant.ResourceURL;
import com.saintdan.framework.constant.VersionConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.enums.GrantType;
import com.saintdan.framework.param.LoginParam;
import com.saintdan.framework.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
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
@Api("refresh token") @RestController @RequestMapping(ResourceURL.RESOURCES + VersionConstant.V1 + ResourceURL.OPEN + ResourceURL.REFRESH)
public class RefreshController {

  @RequestMapping(method = RequestMethod.POST)
  @ApiOperation(value = "refresh token", httpMethod = "POST", response = OAuth2AccessToken.class)
  @ApiImplicitParam(name = "Authorization", value = "token", paramType = "header", dataType = "string", required = true)
  public ResponseEntity refresh(@RequestBody LoginParam param, @ApiIgnore HttpServletRequest request) {
    try {
      // Validate current user, param and sign.
      ResponseEntity responseEntity = validateHelper.validate(param, GrantType.REFRESH_TOKEN);
      if (!responseEntity.getStatusCode().is2xxSuccessful()) {
        return responseEntity;
      }
      return service.refresh(param, request);
    } catch (BadCredentialsException | InvalidGrantException e) {
      // Return unknown error and log the exception.
      return resultHelper.infoResp(logger, ErrorType.parse(e.getMessage()), ErrorType.parse(e.getMessage()).description(), HttpStatus.UNAUTHORIZED);
    } catch (Exception e) {
      // Return unknown error and log the exception.
      return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  // ------------------------
  // PRIVATE FIELDS
  // ------------------------

  private static final Logger logger = LoggerFactory.getLogger(RefreshController.class);

  @Autowired private LoginService service;

  @Autowired private ResultHelper resultHelper;

  @Autowired private ValidateHelper validateHelper;

}
