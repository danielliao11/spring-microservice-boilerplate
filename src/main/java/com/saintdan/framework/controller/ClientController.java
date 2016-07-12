package com.saintdan.framework.controller;

import com.saintdan.framework.annotation.CurrentUser;
import com.saintdan.framework.component.ResultHelper;
import com.saintdan.framework.component.ValidateHelper;
import com.saintdan.framework.constant.ControllerConstant;
import com.saintdan.framework.constant.ResourceURL;
import com.saintdan.framework.constant.ResultConstant;
import com.saintdan.framework.constant.VersionConstant;
import com.saintdan.framework.domain.ClientDomain;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.enums.OperationStatus;
import com.saintdan.framework.exception.CommonsException;
import com.saintdan.framework.param.ClientParam;
import com.saintdan.framework.po.Client;
import com.saintdan.framework.po.User;
import com.saintdan.framework.tools.QueryHelper;
import com.saintdan.framework.vo.ClientVO;
import com.saintdan.framework.vo.ResultVO;
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
  public ResultVO create(@CurrentUser User currentUser, @Valid ClientParam param, BindingResult result) {
    try {
      // Validate current user, param and sign.
      ResultVO resultVO = validateHelper.validateWithOutSignCheck(result, currentUser, logger);
      if (resultVO != null) {
        return resultVO;
      }
      // Return result and message.
      return resultHelper.successResp(clientDomain.create(param, currentUser));
    } catch (CommonsException e) {
      // Return error information and log the exception.
      return resultHelper.infoResp(logger, e.getErrorType(), e.getMessage());
    } catch (Exception e) {
      // Return unknown error and log the exception.
      return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage());
    }
  }

  /**
   * Show all.
   *
   * @param param {@link ClientParam}
   * @return all clients.
   */
  @RequestMapping(method = RequestMethod.GET)
  public ResultVO all(
      @And({
          @Spec(path = "name", spec = Like.class),
          @Spec(path = "validFlag", constVal = "VALID", spec = In.class),
          @Spec(path = "createdDate", params = {"createdDateAfter, createdDateBefore"}, spec = DateBetween.class)}) Specification<Client> clientSpecification,
      ClientParam param) {
    try {
      if (param.getPageNo() == null) {
        return resultHelper.successResp(clientDomain.getAll(clientSpecification, QueryHelper.getSort(param.getSortBy()), ClientVO.class));
      }
      return resultHelper.successResp(clientDomain.getPage(clientSpecification, QueryHelper.getPageRequest(param), ClientVO.class));
    } catch (CommonsException e) {
      // Return error information and log the exception.
      return resultHelper.infoResp(logger, e.getErrorType(), e.getMessage());
    } catch (Exception e) {
      // Return unknown error and log the exception.
      return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage());
    }
  }

  /**
   * Show {@link com.saintdan.framework.vo.ClientVO} by id.
   *
   * @param id id of client
   * @return {@link com.saintdan.framework.vo.ClientVO}
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResultVO detail(@PathVariable String id) {
    try {
      if (StringUtils.isBlank(id)) {
        return resultHelper.infoResp(ErrorType.SYS0002, String.format(ControllerConstant.PARAM_BLANK, ControllerConstant.ID_PARAM));
      }
      return resultHelper.successResp(clientDomain.getById(new ClientParam(Long.valueOf(id)), ResultVO.class));
    } catch (CommonsException e) {
      // Return error information and log the exception.
      return resultHelper.infoResp(logger, e.getErrorType(), e.getMessage());
    } catch (Exception e) {
      // Return unknown error and log the exception.
      return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage());
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
  public ResultVO update(@CurrentUser User currentUser, @PathVariable String id, @Valid ClientParam param, BindingResult result) {
    try {
      if (StringUtils.isBlank(id)) {
        return resultHelper.infoResp(ErrorType.SYS0002, String.format(ControllerConstant.PARAM_BLANK, ControllerConstant.ID_PARAM));
      }
      // Validate current user, param and sign.
      ResultVO resultVO = validateHelper.validateWithOutSignCheck(result, currentUser, logger);
      if (resultVO != null) {
        return resultVO;
      }
      // Update client.
      return resultHelper.successResp(clientDomain.update(ResultVO.class, param, currentUser));
    } catch (CommonsException e) {
      // Return error information and log the exception.
      return resultHelper.infoResp(logger, e.getErrorType(), e.getMessage());
    } catch (Exception e) {
      // Return unknown error and log the exception.
      return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage());
    }
  }

  /**
   * Delete client.
   *
   * @param id id of client
   * @return {@link com.saintdan.framework.vo.ClientVO}
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public ResultVO delete(@CurrentUser User currentUser, @PathVariable String id) {
    try {
      if (StringUtils.isBlank(id)) {
        return resultHelper.infoResp(ErrorType.SYS0002, String.format(ControllerConstant.PARAM_BLANK, ControllerConstant.ID_PARAM));
      }
      ClientParam param = new ClientParam(Long.valueOf(id));
      ResultVO resultVO = validateHelper.validateWithOutSignCheck(currentUser, logger);
      if (resultVO != null) {
        return resultVO;
      }
      // Delete client.
      clientDomain.delete(param, currentUser);
      final String ROLE = "client";
      return new ResultVO(ResultConstant.OK, OperationStatus.SUCCESS, String.format(ControllerConstant.DELETE, ROLE));
    } catch (CommonsException e) {
      // Return error information and log the exception.
      return resultHelper.infoResp(logger, e.getErrorType(), e.getMessage());
    } catch (Exception e) {
      // Return unknown error and log the exception.
      return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage());
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
