package com.saintdan.framework.controller;

import com.saintdan.framework.annotation.CurrentUser;
import com.saintdan.framework.component.ResultHelper;
import com.saintdan.framework.component.ValidateHelper;
import com.saintdan.framework.constant.*;
import com.saintdan.framework.domain.RoleDomain;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.enums.OperationStatus;
import com.saintdan.framework.exception.CommonsException;
import com.saintdan.framework.param.RoleParam;
import com.saintdan.framework.po.User;
import com.saintdan.framework.vo.ResultVO;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Controller of role.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/17/15
 * @since JDK1.8
 */
@RestController
@RequestMapping(ResourceURL.RESOURCES + VersionConstant.V1 + ResourceURL.ROLES)
public class RoleController {

  // ------------------------
  // PUBLIC METHODS
  // ------------------------

  /**
   * Create new {@link com.saintdan.framework.po.Role}.
   *
   * @param param {@link RoleParam}
   * @return {@link com.saintdan.framework.vo.RoleVO}
   */
  @RequestMapping(method = RequestMethod.POST)
  public ResultVO create(@CurrentUser User currentUser, @Valid RoleParam param, BindingResult result) {
    try {
      // Validate current user, param and sign.
      ResultVO resultVO = validateHelper.validate(result, currentUser, param, logger);
      if (resultVO != null) {
        return resultVO;
      }
      // Return result and message.
      return resultHelper.successResp(roleDomain.create(param, currentUser));
    } catch (CommonsException e) {
      // Return error information and log the exception.
      return resultHelper.infoResp(logger, e.getErrorType());
    } catch (Exception e) {
      // Return unknown error and log the exception.
      return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage());
    }
  }

  /**
   * Show all {@link com.saintdan.framework.vo.RoleVO}.
   *
   * @return {@link com.saintdan.framework.vo.RoleVO}
   */
  @RequestMapping(value = PathConstant.INDEX, method = RequestMethod.GET)
  public ResultVO index(String sign) {
    try {
      RoleParam param = new RoleParam();
      param.setSign(sign);
      // Sign validate.
      ResultVO resultVO = validateHelper.validate(param, logger);
      if (resultVO != null) {
        return resultVO;
      }
      return resultHelper.successResp(roleDomain.getAllRoles());
    } catch (CommonsException e) {
      // Return error information and log the exception.
      return resultHelper.infoResp(logger, e.getErrorType());
    } catch (Exception e) {
      // Return unknown error and log the exception.
      return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage());
    }
  }

  /**
   * Show {@link com.saintdan.framework.vo.RoleVO} in {@link com.saintdan.framework.vo.PageVO}.
   *
   * @param pageNo page number
   * @return {@link com.saintdan.framework.vo.RoleVO} in {@link com.saintdan.framework.vo.PageVO}.
   */
  @RequestMapping(method = RequestMethod.GET)
  public ResultVO page(String pageNo, String sign) {
    try {
      // Init page number.
      if (StringUtils.isBlank(pageNo)) {
        pageNo = "0";
      }
      RoleParam param = new RoleParam();
      param.setSign(sign);
      // Sign validate.
      ResultVO resultVO = validateHelper.validate(param, logger);
      if (resultVO != null) {
        return resultVO;
      }
      return resultHelper.successResp(roleDomain.getPage(new PageRequest(Integer.valueOf(pageNo), CommonsConstant.PAGE_SIZE)));
    } catch (CommonsException e) {
      // Return error information and log the exception.
      return resultHelper.infoResp(logger, e.getErrorType());
    } catch (Exception e) {
      // Return unknown error and log the exception.
      return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage());
    }
  }

  /**
   * Show {@link com.saintdan.framework.vo.RoleVO} by ID.
   *
   * @param id id of role
   * @return {@link com.saintdan.framework.vo.RoleVO}
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResultVO show(@PathVariable String id, String sign) {
    try {
      if (StringUtils.isBlank(id)) {
        return resultHelper.infoResp(ErrorType.SYS0002, String.format(ControllerConstant.PARAM_BLANK, ControllerConstant.ID_PARAM));
      }
      RoleParam param = new RoleParam(Long.valueOf(id));
      param.setSign(sign);
      // Sign validate.
      ResultVO resultVO = validateHelper.validate(param, logger);
      if (resultVO != null) {
        return resultVO;
      }
      return resultHelper.successResp(roleDomain.getRoleById(param));
    } catch (CommonsException e) {
      // Return error information and log the exception.
      return resultHelper.infoResp(logger, e.getErrorType());
    } catch (Exception e) {
      // Return unknown error and log the exception.
      return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage());
    }
  }

  /**
   * Update {@link com.saintdan.framework.po.Role}.
   *
   * @param id    id of role
   * @param param role's params
   * @return {@link com.saintdan.framework.vo.RoleVO}
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.POST)
  public ResultVO update(@CurrentUser User currentUser, @PathVariable String id, @Valid RoleParam param, BindingResult result) {
    try {
      if (StringUtils.isBlank(id)) {
        return resultHelper.infoResp(ErrorType.SYS0002, String.format(ControllerConstant.PARAM_BLANK, ControllerConstant.ID_PARAM));
      }
      // Validate current user, param and sign.
      ResultVO resultVO = validateHelper.validate(result, currentUser, param, logger);
      if (resultVO != null) {
        return resultVO;
      }
      // Update role.
      return resultHelper.successResp(roleDomain.update(param, currentUser));
    } catch (CommonsException e) {
      // Return error information and log the exception.
      return resultHelper.infoResp(logger, e.getErrorType());
    } catch (Exception e) {
      // Return unknown error and log the exception.
      return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage());
    }
  }

  /**
   * Delete {@link com.saintdan.framework.po.Role}.
   *
   * @param id id of role
   * @return {@link com.saintdan.framework.vo.RoleVO}
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public ResultVO delete(@CurrentUser User currentUser, @PathVariable String id, String sign) {
    try {
      if (StringUtils.isBlank(id)) {
        return resultHelper.infoResp(ErrorType.SYS0002, String.format(ControllerConstant.PARAM_BLANK, ControllerConstant.ID_PARAM));
      }
      RoleParam param = new RoleParam(Long.valueOf(id));
      param.setSign(sign);
      // Prepare to validate signature.
      param.setSign(new String(Base64.decodeBase64(sign.getBytes())));
      // Sign validate.
      ResultVO resultVO = validateHelper.validate(param, logger);
      if (resultVO != null) {
        return resultVO;
      }
      // Delete role.
      roleDomain.delete(param, currentUser);
      final String ROLE = "role";
      return new ResultVO(ResultConstant.OK, OperationStatus.SUCCESS, String.format(ControllerConstant.DELETE, ROLE));
    } catch (CommonsException e) {
      // Return error information and log the exception.
      return resultHelper.infoResp(logger, e.getErrorType());
    } catch (Exception e) {
      // Return unknown error and log the exception.
      return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage());
    }
  }

  // ------------------------
  // PRIVATE FIELDS
  // ------------------------

  private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

  @Autowired private ResultHelper resultHelper;

  @Autowired private ValidateHelper validateHelper;

  @Autowired private RoleDomain roleDomain;
}
