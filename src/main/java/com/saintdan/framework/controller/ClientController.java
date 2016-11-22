package com.saintdan.framework.controller;

import com.saintdan.framework.annotation.CurrentUser;
import com.saintdan.framework.component.ResultHelper;
import com.saintdan.framework.component.ValidateHelper;
import com.saintdan.framework.constant.ControllerConstant;
import com.saintdan.framework.constant.ResourceURL;
import com.saintdan.framework.constant.VersionConstant;
import com.saintdan.framework.domain.ClientDomain;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.enums.OperationType;
import com.saintdan.framework.exception.CommonsException;
import com.saintdan.framework.param.ClientParam;
import com.saintdan.framework.po.Client;
import com.saintdan.framework.po.User;
import com.saintdan.framework.tools.QueryHelper;
import com.saintdan.framework.vo.ClientVO;
import javax.validation.Valid;
import net.kaczmarzyk.spring.data.jpa.domain.DateBetween;
import net.kaczmarzyk.spring.data.jpa.domain.In;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller of client.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/28/15
 * @since JDK1.8
 */
@RestController
@RequestMapping(ResourceURL.RESOURCES + VersionConstant.V1 + ResourceURL.CLIENTS)
public class ClientController {

  // ------------------------
  // PUBLIC METHODS
  // ------------------------

  /**
   * Create new {@link com.saintdan.framework.po.Client}.
   *
   * @param param {@link ClientParam}
   * @return {@link com.saintdan.framework.vo.ClientVO}
   */
  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity create(@CurrentUser User currentUser, @Valid ClientParam param, BindingResult result) {
    try {
      // Validate current user, param and sign.
      ResponseEntity responseEntity = validateHelper.validate(param, result, currentUser, logger, OperationType.CREATE);
      if (!responseEntity.getStatusCode().is2xxSuccessful()) {
        return responseEntity;
      }
      // Return result and message.
      return resultHelper.successResp(clientDomain.create(param, currentUser), HttpStatus.CREATED);
    } catch (CommonsException e) {
      // Return error information and log the exception.
      return resultHelper.infoResp(logger, e.getErrorType(), e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    } catch (Exception e) {
      // Return unknown error and log the exception.
      return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Show all.
   *
   * @param param {@link ClientParam}
   * @return all clients.
   */
  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity all(
      @And({
          @Spec(path = "name", spec = Like.class),
          @Spec(path = "validFlag", constVal = "VALID", spec = In.class),
          @Spec(path = "createdDate", params = {"createdDateAfter, createdDateBefore"}, spec = DateBetween.class)}) Specification<Client> clientSpecification,
      ClientParam param) {
    try {
      if (param.getPageNo() == null) {
        return resultHelper.successResp(clientDomain.getAll(clientSpecification, QueryHelper.getSort(param.getSortBy()), ClientVO.class), HttpStatus.OK);
      }
      return resultHelper.successResp(clientDomain.getPage(clientSpecification, QueryHelper.getPageRequest(param), ClientVO.class), HttpStatus.OK);
    } catch (Exception e) {
      // Return unknown error and log the exception.
      return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Show {@link com.saintdan.framework.vo.ClientVO} by id.
   *
   * @param id id of client
   * @return {@link com.saintdan.framework.vo.ClientVO}
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseEntity detail(@PathVariable String id) {
    try {
      if (StringUtils.isBlank(id)) {
        return resultHelper.infoResp(ErrorType.SYS0002, String.format(ControllerConstant.PARAM_BLANK, ControllerConstant.ID_PARAM), HttpStatus.UNPROCESSABLE_ENTITY);
      }
      return resultHelper.successResp(clientDomain.getById(Long.valueOf(id), ResponseEntity.class), HttpStatus.OK);
    } catch (Exception e) {
      // Return unknown error and log the exception.
      return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Update client.
   *
   * @param id    id of client
   * @param param {@link ClientParam}
   * @return {@link com.saintdan.framework.vo.ClientVO}
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.POST)
  public ResponseEntity update(@CurrentUser User currentUser, @PathVariable String id, @Valid ClientParam param, BindingResult result) {
    try {
      param.setId(StringUtils.isBlank(id) ? null : Long.valueOf(id));
      // Validate current user, param and sign.
      ResponseEntity responseEntity = validateHelper.validate(param, result, currentUser, logger, OperationType.UPDATE);
      if (!responseEntity.getStatusCode().is2xxSuccessful()) {
        return responseEntity;
      }
      // Update client.
      return resultHelper.successResp(clientDomain.update(ClientVO.class, param, currentUser), HttpStatus.OK);
    } catch (CommonsException e) {
      // Return error information and log the exception.
      return resultHelper.infoResp(logger, e.getErrorType(), e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    } catch (Exception e) {
      // Return unknown error and log the exception.
      return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Delete client.
   *
   * @param id id of client
   * @return {@link com.saintdan.framework.vo.ClientVO}
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public ResponseEntity delete(@CurrentUser User currentUser, @PathVariable String id) {
    try {
      ClientParam param = new ClientParam(StringUtils.isBlank(id) ? null : Long.valueOf(id));
      // Validate current user and param.
      ResponseEntity responseEntity = validateHelper.validate(param, currentUser, logger, OperationType.DELETE);
      if (!responseEntity.getStatusCode().is2xxSuccessful()) {
        return responseEntity;
      }
      // Delete client.
      clientDomain.delete(param, currentUser);
      return new ResponseEntity(HttpStatus.NO_CONTENT);
    } catch (CommonsException e) {
      // Return error information and log the exception.
      return resultHelper.infoResp(logger, e.getErrorType(), e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    } catch (Exception e) {
      // Return unknown error and log the exception.
      return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  // ------------------------
  // PRIVATE FIELDS
  // ------------------------

  private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

  @Autowired private ResultHelper resultHelper;

  @Autowired private ValidateHelper validateHelper;

  @Autowired private ClientDomain clientDomain;
}
