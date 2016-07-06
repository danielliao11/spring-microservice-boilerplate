package com.saintdan.framework.controller;

import com.saintdan.framework.annotation.CurrentUser;
import com.saintdan.framework.component.ResultHelper;
import com.saintdan.framework.component.ValidateHelper;
import com.saintdan.framework.constant.ControllerConstant;
import com.saintdan.framework.constant.ResourceURL;
import com.saintdan.framework.constant.ResultConstant;
import com.saintdan.framework.constant.VersionConstant;
import com.saintdan.framework.domain.ResourceDomain;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.enums.OperationStatus;
import com.saintdan.framework.exception.CommonsException;
import com.saintdan.framework.param.ResourceParam;
import com.saintdan.framework.po.User;
import com.saintdan.framework.tools.QueryHelper;
import com.saintdan.framework.vo.ResultVO;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller of resource.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/17/15
 * @since JDK1.8
 */
@RestController
@RequestMapping(ResourceURL.RESOURCES + VersionConstant.V1 + ResourceURL.RESOURCES)
public class ResourceController {

  // ------------------------
  // PUBLIC METHODS
  // ------------------------

  /**
   * Create new {@link com.saintdan.framework.po.Resource}.
   *
   * @param param {@link ResourceParam}
   * @return {@link com.saintdan.framework.vo.ResourceVO}
   */
  @RequestMapping(method = RequestMethod.POST)
  public ResultVO create(@CurrentUser User currentUser, @Valid ResourceParam param, BindingResult result) {
    try {
      // Validate current user, param and sign.
      ResultVO resultVO = validateHelper.validate(result, currentUser, param, logger);
      if (resultVO != null) {
        return resultVO;
      }
      // Return result and message.
      return resultHelper.successResp(resourceDomain.create(param, currentUser));
    } catch (CommonsException e) {
      // Return error information and log the exception.
      return resultHelper.infoResp(logger, e.getErrorType(), e.getMessage());
    } catch (Exception e) {
      // Return unknown error and log the exception.
      return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage());
    }
  }

  /**
   * Show {@link com.saintdan.framework.vo.ResourceVO} in {@link com.saintdan.framework.vo.PageVO}.
   *
   * @param param {@link ResourceParam}
   * @return {@link com.saintdan.framework.vo.ResourceVO} in {@link com.saintdan.framework.vo.PageVO}.
   */
  @RequestMapping(method = RequestMethod.GET)
  public ResultVO show(ResourceParam param) {
    try {
      // Sign validate.
      ResultVO resultVO = validateHelper.validate(param, logger);
      if (resultVO != null) {
        return resultVO;
      }
      if (param.getPageNo() == null) {
        return resultHelper.successResp(resourceDomain.getAllResources());
      }
      return resultHelper.successResp(resourceDomain.getPage(QueryHelper.getPageRequest(param)));
    } catch (CommonsException e) {
      // Return error information and log the exception.
      return resultHelper.infoResp(logger, e.getErrorType(), e.getMessage());
    } catch (Exception e) {
      // Return unknown error and log the exception.
      return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage());
    }
  }

  /**
   * Show {@link com.saintdan.framework.vo.ResourceVO} by ID.
   *
   * @param id id of resource
   * @return {@link com.saintdan.framework.vo.ResourceVO}
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResultVO show(@PathVariable String id, String sign) {
    try {
      if (StringUtils.isBlank(id)) {
        return resultHelper.infoResp(ErrorType.SYS0002, String.format(ControllerConstant.PARAM_BLANK, ControllerConstant.ID_PARAM));
      }
      ResourceParam param = new ResourceParam(Long.valueOf(id));
      param.setSign(sign);
      // Sign validate.
      ResultVO resultVO = validateHelper.validate(param, logger);
      if (resultVO != null) {
        return resultVO;
      }
      return resultHelper.successResp(resourceDomain.getResourceById(param));
    } catch (CommonsException e) {
      // Return error information and log the exception.
      return resultHelper.infoResp(logger, e.getErrorType(), e.getMessage());
    } catch (Exception e) {
      // Return unknown error and log the exception.
      return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage());
    }
  }

  /**
   * Update {@link com.saintdan.framework.po.Resource}.
   *
   * @param id    id of resource
   * @param param {@link ResourceParam}
   * @return {@link com.saintdan.framework.vo.ResourceVO}
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  public ResultVO update(@CurrentUser User currentUser, @PathVariable String id, @Valid ResourceParam param, BindingResult result) {
    try {
      if (StringUtils.isBlank(id)) {
        return resultHelper.infoResp(ErrorType.SYS0002, String.format(ControllerConstant.PARAM_BLANK, ControllerConstant.ID_PARAM));
      }
      // Validate current user, param and sign.
      ResultVO resultVO = validateHelper.validate(result, currentUser, param, logger);
      if (resultVO != null) {
        return resultVO;
      }
      // Update resource.
      return resultHelper.successResp(resourceDomain.update(param, currentUser));
    } catch (CommonsException e) {
      // Return error information and log the exception.
      return resultHelper.infoResp(logger, e.getErrorType(), e.getMessage());
    } catch (Exception e) {
      // Return unknown error and log the exception.
      return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage());
    }
  }

  /**
   * Delete {@link com.saintdan.framework.po.Resource}.
   *
   * @param id id of resource
   * @return {@link com.saintdan.framework.vo.ResourceVO}
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public ResultVO delete(@CurrentUser User currentUser, @PathVariable String id, String sign) {
    try {
      if (StringUtils.isBlank(id)) {
        return resultHelper.infoResp(ErrorType.SYS0002, String.format(ControllerConstant.PARAM_BLANK, ControllerConstant.ID_PARAM));
      }
      ResourceParam param = new ResourceParam(Long.valueOf(id));
      param.setSign(sign);
      // Sign validate.
      ResultVO resultVO = validateHelper.validate(param, logger);
      if (resultVO != null) {
        return resultVO;
      }
      // Delete resource.
      resourceDomain.delete(param, currentUser);
      final String ROLE = "resource";
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

  private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

  @Autowired private ResultHelper resultHelper;

  @Autowired private ValidateHelper validateHelper;

  @Autowired private ResourceDomain resourceDomain;
}
