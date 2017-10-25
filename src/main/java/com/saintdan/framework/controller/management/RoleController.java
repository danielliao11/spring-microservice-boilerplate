package com.saintdan.framework.controller.management;

import com.saintdan.framework.annotation.CurrentUser;
import com.saintdan.framework.component.ResultHelper;
import com.saintdan.framework.constant.ResourcePath;
import com.saintdan.framework.domain.RoleDomain;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.exception.CommonsException;
import com.saintdan.framework.param.RoleParam;
import com.saintdan.framework.po.User;
import com.saintdan.framework.tools.Assert;
import com.saintdan.framework.vo.RoleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Controller of role.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/17/15
 * @since JDK1.8
 */
@Api("Role")
@RestController
@RequestMapping(
    ResourcePath.API + ResourcePath.V1 + ResourcePath.MANAGEMENT + ResourcePath.ROLES)
public class RoleController {

  @RequestMapping(method = RequestMethod.POST)
  @ApiOperation(value = "Create", httpMethod = "POST", response = RoleVO.class)
  @ApiImplicitParams({
      @ApiImplicitParam(name = "Authorization", value = "token", paramType = "header", dataType = "string", required = true),
      @ApiImplicitParam(name = "Limit-Key", value = "limit key", paramType = "header", dataType = "string")
  })
  public ResponseEntity create(@ApiIgnore @CurrentUser User currentUser,
      @RequestBody RoleParam param) {
    try {
      // Return result and message.
      return new ResponseEntity<>(roleDomain.create(param, currentUser), HttpStatus.CREATED);
    } catch (CommonsException e) {
      // Return error information and log the exception.
      return resultHelper
          .infoResp(logger, e.getErrorType(), e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    } catch (Exception e) {
      // Return unknown error and log the exception.
      return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage(),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @RequestMapping(method = RequestMethod.GET)
  @ApiOperation(value = "List", httpMethod = "GET", response = RoleVO.class)
  @ApiImplicitParams({
      @ApiImplicitParam(name = "Authorization", value = "token", paramType = "header", dataType = "string", required = true),
      @ApiImplicitParam(name = "Limit-Key", value = "limit key", paramType = "header", dataType = "string")
  })
  public ResponseEntity all() {
    try {
      return new ResponseEntity<>(roleDomain.all(), HttpStatus.OK);
    } catch (Exception e) {
      // Return unknown error and log the exception.
      return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage(),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  @ApiOperation(value = "Detail", httpMethod = "GET", response = RoleVO.class)
  @ApiImplicitParams({
      @ApiImplicitParam(name = "Authorization", paramType = "header", dataType = "string", required = true),
      @ApiImplicitParam(name = "Limit-Key", value = "limit key", paramType = "header", dataType = "string"),
      @ApiImplicitParam(name = "id", paramType = "path", dataType = "long", required = true)
  })
  public ResponseEntity detail(@ApiIgnore @PathVariable String id) {
    try {
      return new ResponseEntity<>(roleDomain.getById(Long.valueOf(id)), HttpStatus.OK);
    } catch (Exception e) {
      // Return unknown error and log the exception.
      return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage(),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @RequestMapping(value = "/{id}", method = {RequestMethod.PUT, RequestMethod.PATCH})
  @ApiOperation(value = "Update", httpMethod = "PUT", response = RoleVO.class)
  @ApiImplicitParams({
      @ApiImplicitParam(name = "Authorization", paramType = "header", dataType = "string", required = true),
      @ApiImplicitParam(name = "Limit-Key", value = "limit key", paramType = "header", dataType = "string"),
      @ApiImplicitParam(name = "id", paramType = "path", dataType = "long", required = true)
  })
  public ResponseEntity update(@ApiIgnore @CurrentUser User currentUser,
      @ApiIgnore @PathVariable Long id,
      @RequestBody RoleParam param) {
    try {
      // Update role.
      param.setId(id);
      return new ResponseEntity<>(roleDomain.update(param, currentUser), HttpStatus.OK);
    } catch (CommonsException e) {
      // Return error information and log the exception.
      return resultHelper
          .infoResp(logger, e.getErrorType(), e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    } catch (Exception e) {
      // Return unknown error and log the exception.
      return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage(),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  @ApiOperation(value = "Delete", httpMethod = "DELETE", response = ResponseEntity.class)
  @ApiImplicitParams({
      @ApiImplicitParam(name = "Authorization", paramType = "header", dataType = "string", required = true),
      @ApiImplicitParam(name = "Limit-Key", value = "limit key", paramType = "header", dataType = "string"),
      @ApiImplicitParam(name = "id", paramType = "path", dataType = "long", required = true)
  })
  public ResponseEntity delete(@ApiIgnore @PathVariable Long id) {
    try {
      // Delete role.
      roleDomain.deepDelete(id);
    } catch (CommonsException e) {
      // Return error information and log the exception.
      return resultHelper
          .infoResp(logger, e.getErrorType(), e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    } catch (Exception e) {
      // Return unknown error and log the exception.
      return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage(),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return ResponseEntity.noContent().build();
  }

  private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
  private final ResultHelper resultHelper;
  private final RoleDomain roleDomain;

  @Autowired public RoleController(ResultHelper resultHelper, RoleDomain roleDomain) {
    Assert.defaultNotNull(resultHelper);
    Assert.defaultNotNull(roleDomain);
    this.resultHelper = resultHelper;
    this.roleDomain = roleDomain;
  }
}
