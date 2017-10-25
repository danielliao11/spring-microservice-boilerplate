package com.saintdan.framework.controller.management;

import com.saintdan.framework.annotation.CurrentUser;
import com.saintdan.framework.component.ResultHelper;
import com.saintdan.framework.constant.ResourcePath;
import com.saintdan.framework.domain.UserDomain;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.exception.CommonsException;
import com.saintdan.framework.param.UserParam;
import com.saintdan.framework.po.User;
import com.saintdan.framework.tools.Assert;
import com.saintdan.framework.tools.QueryHelper;
import com.saintdan.framework.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.kaczmarzyk.spring.data.jpa.domain.GreaterThanOrEqual;
import net.kaczmarzyk.spring.data.jpa.domain.In;
import net.kaczmarzyk.spring.data.jpa.domain.LessThanOrEqual;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Controller of user.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/25/15
 * @since JDK1.8
 */
@Api("User")
@RestController
@RequestMapping(
    ResourcePath.API + ResourcePath.V1 + ResourcePath.MANAGEMENT + ResourcePath.USERS)
public class UserController {

  @RequestMapping(method = RequestMethod.POST)
  @ApiOperation(value = "Create", httpMethod = "POST", response = UserVO.class)
  @ApiImplicitParams({
      @ApiImplicitParam(name = "Authorization", value = "token", paramType = "header", dataType = "string", required = true),
      @ApiImplicitParam(name = "Limit-Key", value = "limit key", paramType = "header", dataType = "string")
  })
  public ResponseEntity create(@ApiIgnore @CurrentUser User currentUser,
      @RequestBody UserParam param) {
    try {
      // Return result and message.
      return new ResponseEntity<>(userDomain.create(param, currentUser), HttpStatus.CREATED);
      //      return new ResponseEntity<>(userDomain.create(param, currentUser), HttpStatus.CREATED);
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
  @ApiOperation(value = "List", httpMethod = "GET", response = UserVO.class)
  @ApiImplicitParams({
      @ApiImplicitParam(name = "Authorization", value = "token", paramType = "header", dataType = "string", required = true),
      @ApiImplicitParam(name = "Limit-Key", value = "limit key", paramType = "header", dataType = "string"),
      @ApiImplicitParam(name = "name", value = "user's name", paramType = "query", dataType = "string"),
      @ApiImplicitParam(name = "usr", value = "user's username", paramType = "query", dataType = "string"),
      @ApiImplicitParam(name = "createdAtAfter", value = "unix milli timestamp", paramType = "query", dataType = "long"),
      @ApiImplicitParam(name = "createdAtBefore", value = "unix milli timestamp", paramType = "query", dataType = "long"),
      @ApiImplicitParam(name = "pageNo", paramType = "query", dataType = "int"),
      @ApiImplicitParam(name = "pageSize", paramType = "query", dataType = "int"),
      @ApiImplicitParam(name = "sortBy", paramType = "query", dataType = "string", example = "sortBy=id:desc,username:desc")
  })
  public ResponseEntity all(
      @And({
          @Spec(path = "usr", spec = Like.class),
          @Spec(path = "name", spec = Like.class),
          @Spec(path = "validFlag", constVal = "VALID", spec = In.class),
          @Spec(path = "createdAt", params = "createdAtAfter", spec = GreaterThanOrEqual.class),
          @Spec(path = "createdAt", params = "createdAtBefore", spec = LessThanOrEqual.class)
      }) @ApiIgnore Specification<User> userSpecification, @ApiIgnore UserParam param) {
    try {
      if (param.getPageNo() == null) {
        return new ResponseEntity<>(
            userDomain.getAll(userSpecification, QueryHelper.getSort(param.getSortBy())),
            HttpStatus.OK);
      }
    } catch (Exception e) {
      // Return unknown error and log the exception.
      return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage(),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    try {
      return new ResponseEntity<>(
          userDomain.getPage(userSpecification, QueryHelper.getPageRequest(param)), HttpStatus.OK);
    } catch (Exception e) {
      // Return unknown error and log the exception.
      return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage(),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  @ApiOperation(value = "Detail", httpMethod = "GET", response = UserVO.class)
  @ApiImplicitParams({
      @ApiImplicitParam(name = "Authorization", paramType = "header", dataType = "string", required = true),
      @ApiImplicitParam(name = "Limit-Key", value = "limit key", paramType = "header", dataType = "string"),
      @ApiImplicitParam(name = "id", paramType = "path", dataType = "long", required = true)
  })
  public ResponseEntity detail(@ApiIgnore @PathVariable Long id) {
    try {
      return new ResponseEntity<>(userDomain.getById(id, UserVO.class), HttpStatus.OK);
    } catch (Exception e) {
      // Return unknown error and log the exception.
      return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage(),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @RequestMapping(value = "/{id}", method = {RequestMethod.PUT, RequestMethod.PATCH})
  @ApiImplicitParams({
      @ApiImplicitParam(name = "Authorization", paramType = "header", dataType = "string", required = true),
      @ApiImplicitParam(name = "Limit-Key", value = "limit key", paramType = "header", dataType = "string"),
      @ApiImplicitParam(name = "id", paramType = "path", dataType = "long", required = true)
  })
  public ResponseEntity update(@ApiIgnore @PathVariable Long id,
      @ApiIgnore @CurrentUser User currentUser,
      @RequestBody UserParam param) {
    try {
      // Update user.
      param.setId(id);
      return new ResponseEntity<>(userDomain.update(param, currentUser), HttpStatus.OK);
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
  @ApiImplicitParams({
      @ApiImplicitParam(name = "Authorization", paramType = "header", dataType = "string", required = true),
      @ApiImplicitParam(name = "Limit-Key", value = "limit key", paramType = "header", dataType = "string"),
      @ApiImplicitParam(name = "id", paramType = "path", dataType = "long", required = true)
  })
  public ResponseEntity delete(@ApiIgnore @PathVariable Long id) {
    try {
      // Delete user.
      userDomain.deepDelete(id);
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

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);
  private final ResultHelper resultHelper;
  private final UserDomain userDomain;

  @Autowired public UserController(ResultHelper resultHelper, UserDomain userDomain) {
    Assert.defaultNotNull(resultHelper);
    Assert.defaultNotNull(userDomain);
    this.resultHelper = resultHelper;
    this.userDomain = userDomain;
  }
}
