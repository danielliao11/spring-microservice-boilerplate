package com.saintdan.framework.controller.management;

import com.saintdan.framework.annotation.CurrentUser;
import com.saintdan.framework.component.ResultHelper;
import com.saintdan.framework.component.ValidateHelper;
import com.saintdan.framework.constant.CommonsConstant;
import com.saintdan.framework.constant.ResourceURL;
import com.saintdan.framework.constant.VersionConstant;
import com.saintdan.framework.domain.UserDomain;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.enums.OperationType;
import com.saintdan.framework.exception.CommonsException;
import com.saintdan.framework.param.UserParam;
import com.saintdan.framework.po.User;
import com.saintdan.framework.spec.LocalDateTimeAfter;
import com.saintdan.framework.spec.LocalDateTimeBefore;
import com.saintdan.framework.tools.Assert;
import com.saintdan.framework.tools.QueryHelper;
import com.saintdan.framework.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.kaczmarzyk.spring.data.jpa.domain.In;
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
@Api("User") @RestController @RequestMapping(ResourceURL.RESOURCES + VersionConstant.V1 + ResourceURL.MANAGEMENT + ResourceURL.USERS) public class UserController {

  @RequestMapping(method = RequestMethod.POST)
  @ApiOperation(value = "Create", httpMethod = "POST", response = UserVO.class)
  @ApiImplicitParam(name = "Authorization", paramType = "header", dataType = "string", required = true)
  public ResponseEntity create(@ApiIgnore @CurrentUser User currentUser, @RequestBody UserParam param) {
    try {
      // Validate current user, param and sign.
      ResponseEntity responseEntity = validateHelper.validate(param, currentUser, logger, OperationType.CREATE);
      if (!responseEntity.getStatusCode().is2xxSuccessful()) {
        return responseEntity;
      }
      // Return result and message.
      return new ResponseEntity<>(userDomain.create(param, currentUser), HttpStatus.CREATED);
//      return new ResponseEntity<>(userDomain.create(param, currentUser), HttpStatus.CREATED);
    } catch (CommonsException e) {
      // Return error information and log the exception.
      return resultHelper.infoResp(logger, e.getErrorType(), e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    } catch (Exception e) {
      // Return unknown error and log the exception.
      return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @RequestMapping(method = RequestMethod.GET)
  @ApiOperation(value = "List", httpMethod = "GET", response = UserVO.class)
  @ApiImplicitParams({
      @ApiImplicitParam(name = "Authorization", value = "token", paramType = "header", dataType = "string", required = true),
      @ApiImplicitParam(name = "name", value = "user's name", paramType = "query", dataType = "string"),
      @ApiImplicitParam(name = "usr", value = "user's username", paramType = "query", dataType = "string"),
      @ApiImplicitParam(name = "createdDateAfter", value = "unix milli timestamp", dataType = "string", paramType = "query"),
      @ApiImplicitParam(name = "createdDateBefore", value = "unix milli timestamp", dataType = "string", paramType = "query"),
      @ApiImplicitParam(name = "pageNo", dataType = "date", paramType = "query"),
      @ApiImplicitParam(name = "pageSize", dataType = "date", paramType = "query"),
      @ApiImplicitParam(name = "sortBy", dataType = "date", paramType = "query", example = "sortBy=id:desc,username:desc")
  })
  public ResponseEntity all(
      @And({
          @Spec(path = "usr", spec = Like.class),
          @Spec(path = "name", spec = Like.class),
          @Spec(path = "validFlag", constVal = "VALID", spec = In.class),
          @Spec(path = "createdDate", params = "createdDateAfter", spec = LocalDateTimeAfter.class),
          @Spec(path = "createdDate", params = "createdDateBefore", spec = LocalDateTimeBefore.class)
      }) @ApiIgnore Specification<User> userSpecification, @ApiIgnore UserParam param) {
    try {
      if (param.getPageNo() == null) {
        return new ResponseEntity<>(userDomain.getAll(userSpecification, QueryHelper.getSort(param.getSortBy())), HttpStatus.OK);
      }
      return new ResponseEntity<>(userDomain.getPage(userSpecification, QueryHelper.getPageRequest(param)), HttpStatus.OK);
    } catch (Exception e) {
      // Return unknown error and log the exception.
      return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  @ApiOperation(value = "Detail", httpMethod = "GET", response = UserVO.class)
  @ApiImplicitParams({
      @ApiImplicitParam(name = "Authorization", paramType = "header", dataType = "string", required = true),
      @ApiImplicitParam(name = "id", paramType = "path", dataType = "long", required = true)
  })
  public ResponseEntity detail(@ApiIgnore @PathVariable Long id) {
    try {
      if (id == null) {
        return resultHelper.infoResp(ErrorType.SYS0002, CommonsConstant.ID_BLANK, HttpStatus.UNPROCESSABLE_ENTITY);
      }
      return new ResponseEntity<>(userDomain.getById(id, UserVO.class), HttpStatus.OK);
    } catch (Exception e) {
      // Return unknown error and log the exception.
      return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  @ApiImplicitParams({
      @ApiImplicitParam(name = "Authorization", paramType = "header", dataType = "string", required = true),
      @ApiImplicitParam(name = "id", paramType = "path", dataType = "long", required = true)
  })
  public ResponseEntity update(@ApiIgnore @CurrentUser User currentUser, @RequestBody UserParam param) {
    try {
      // Validate current user, param and sign.
      ResponseEntity responseEntity = validateHelper.validate(param, currentUser, logger, OperationType.UPDATE);
      if (!responseEntity.getStatusCode().is2xxSuccessful()) {
        return responseEntity;
      }
      // Update user.
      return new ResponseEntity<>(userDomain.update(param, currentUser), HttpStatus.OK);
    } catch (CommonsException e) {
      // Return error information and log the exception.
      return resultHelper.infoResp(logger, e.getErrorType(), e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    } catch (Exception e) {
      // Return unknown error and log the exception.
      return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  @ApiImplicitParams({
      @ApiImplicitParam(name = "Authorization", paramType = "header", dataType = "string", required = true),
      @ApiImplicitParam(name = "id", paramType = "path", dataType = "long", required = true)
  })
  public ResponseEntity delete(@ApiIgnore @CurrentUser User currentUser, @ApiIgnore @PathVariable Long id) {
    try {
      UserParam param = new UserParam(id);
      // Validate current user and param.
      ResponseEntity responseEntity = validateHelper.validate(param, currentUser, logger, OperationType.DELETE);
      if (!responseEntity.getStatusCode().is2xxSuccessful()) {
        return responseEntity;
      }
      // Delete user.
      userDomain.deepDelete(param.getId(), currentUser);
      return new ResponseEntity(HttpStatus.NO_CONTENT);
    } catch (CommonsException e) {
      // Return error information and log the exception.
      return resultHelper.infoResp(logger, e.getErrorType(), e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    } catch (Exception e) {
      // Return unknown error and log the exception.
      return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  private final ResultHelper resultHelper;

  private final ValidateHelper validateHelper;

  private final UserDomain userDomain;

  @Autowired public UserController(ResultHelper resultHelper, ValidateHelper validateHelper, UserDomain userDomain) {
    Assert.defaultNotNull(resultHelper);
    Assert.defaultNotNull(validateHelper);
    Assert.defaultNotNull(userDomain);
    this.resultHelper = resultHelper;
    this.validateHelper = validateHelper;
    this.userDomain = userDomain;
  }

}
