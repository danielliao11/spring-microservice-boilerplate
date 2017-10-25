package com.saintdan.framework.controller.management;

import com.saintdan.framework.annotation.CurrentUser;
import com.saintdan.framework.component.ResultHelper;
import com.saintdan.framework.constant.ResourcePath;
import com.saintdan.framework.domain.ClientDomain;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.exception.CommonsException;
import com.saintdan.framework.param.ClientParam;
import com.saintdan.framework.po.User;
import com.saintdan.framework.tools.Assert;
import com.saintdan.framework.vo.ClientVO;
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
 * Controller of client.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/28/15
 * @since JDK1.8
 */
@Api("Client")
@RestController
@RequestMapping(
    ResourcePath.API + ResourcePath.V1 + ResourcePath.MANAGEMENT + ResourcePath.CLIENTS)
public class ClientController {

  /**
   * Create new {@link com.saintdan.framework.po.Client}.
   *
   * @param param {@link ClientParam}
   * @return {@link com.saintdan.framework.vo.ClientVO}
   */
  @RequestMapping(method = RequestMethod.POST)
  @ApiOperation(value = "Create", httpMethod = "POST", response = ClientVO.class)
  @ApiImplicitParams({
      @ApiImplicitParam(name = "Authorization", value = "token", paramType = "header", dataType = "string", required = true),
      @ApiImplicitParam(name = "Limit-Key", value = "limit key", paramType = "header", dataType = "string")
  })
  public ResponseEntity create(@ApiIgnore @CurrentUser User currentUser,
      @RequestBody ClientParam param) {
    try {
      // Return result and message.
      return new ResponseEntity<>(clientDomain.create(param, currentUser), HttpStatus.CREATED);
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

  /**
   * Show all.
   *
   * @return all clients.
   */
  @RequestMapping(method = RequestMethod.GET)
  @ApiOperation(value = "List", httpMethod = "GET", response = ClientVO.class)
  @ApiImplicitParams({
      @ApiImplicitParam(name = "Authorization", value = "token", paramType = "header", dataType = "string", required = true),
      @ApiImplicitParam(name = "Limit-Key", value = "limit key", paramType = "header", dataType = "string")
  })
  public ResponseEntity all() {
    try {
      return new ResponseEntity<>(clientDomain.all(), HttpStatus.OK);
    } catch (Exception e) {
      // Return unknown error and log the exception.
      return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage(),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Update client.
   *
   * @param currentUser {@link User}
   * @param id          {@link User#id}
   * @param param       {@link ClientParam}
   * @return {@link com.saintdan.framework.vo.ClientVO}
   */
  @RequestMapping(value = "/{id}", method = {RequestMethod.PUT, RequestMethod.PATCH})
  @ApiOperation(value = "Update", httpMethod = "PUT", response = ClientVO.class)
  @ApiImplicitParams({
      @ApiImplicitParam(name = "Authorization", paramType = "header", dataType = "string", required = true),
      @ApiImplicitParam(name = "Limit-Key", value = "limit key", paramType = "header", dataType = "string"),
      @ApiImplicitParam(name = "id", value = "client's id", paramType = "path", dataType = "long", required = true)
  })
  public ResponseEntity update(@ApiIgnore @CurrentUser User currentUser,
      @ApiIgnore @PathVariable Long id,
      @RequestBody ClientParam param) {
    try {
      // Update client.
      param.setId(id);
      return new ResponseEntity<>(clientDomain.update(param, currentUser), HttpStatus.OK);
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

  /**
   * Delete client.
   *
   * @param id {@link com.saintdan.framework.po.Client#id}
   * @return {@link com.saintdan.framework.vo.ClientVO}
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  @ApiOperation(value = "Delete", httpMethod = "DELETE", response = ResponseEntity.class)
  @ApiImplicitParams({
      @ApiImplicitParam(name = "Authorization", paramType = "header", dataType = "string", required = true),
      @ApiImplicitParam(name = "Limit-Key", value = "limit key", paramType = "header", dataType = "string"),
      @ApiImplicitParam(name = "id", value = "client's id", paramType = "path", dataType = "long", required = true)
  })
  public ResponseEntity delete(@ApiIgnore @PathVariable Long id) {
    try {
      // Delete client.
      clientDomain.deepDelete(id);
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

  private static final Logger logger = LoggerFactory.getLogger(ClientController.class);
  private final ResultHelper resultHelper;
  private final ClientDomain clientDomain;

  @Autowired public ClientController(ResultHelper resultHelper, ClientDomain clientDomain) {
    Assert.defaultNotNull(resultHelper);
    Assert.defaultNotNull(clientDomain);
    this.resultHelper = resultHelper;
    this.clientDomain = clientDomain;
  }
}
