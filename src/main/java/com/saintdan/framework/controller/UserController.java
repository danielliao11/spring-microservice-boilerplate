package com.saintdan.framework.controller;

import com.saintdan.framework.annotation.CurrentUser;
import com.saintdan.framework.component.ResultHelper;
import com.saintdan.framework.component.ValidateHelper;
import com.saintdan.framework.constant.CommonsConstant;
import com.saintdan.framework.constant.ControllerConstant;
import com.saintdan.framework.constant.PathConstant;
import com.saintdan.framework.constant.ResourceURL;
import com.saintdan.framework.constant.ResultConstant;
import com.saintdan.framework.constant.VersionConstant;
import com.saintdan.framework.domain.UserDomain;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.enums.OperationStatus;
import com.saintdan.framework.exception.CommonsException;
import com.saintdan.framework.param.UserParam;
import com.saintdan.framework.po.User;
import com.saintdan.framework.tools.QueryHelper;
import com.saintdan.framework.vo.ResultVO;
import javax.validation.Valid;
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

/**
 * Controller of user.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/25/15
 * @since JDK1.8
 */
@RestController
@RequestMapping(ResourceURL.RESOURCES + VersionConstant.V1 + ResourceURL.USERS)
public class UserController {

  // ------------------------
  // PUBLIC METHODS
  // ------------------------

  /**
   * Create new {@link User}.
   *
   * @param param {@link UserParam}
   * @return {@link com.saintdan.framework.vo.UserVO}
   */
  @RequestMapping(method = RequestMethod.POST)
  public ResultVO create(@CurrentUser User currentUser, @Valid UserParam param, BindingResult result) {
    try {
      // Validate current user, param and sign.
      ResultVO resultVO = validateHelper.validate(result, currentUser, param, logger);
      if (resultVO != null) {
        return resultVO;
      }
      // Return result and message.
      return resultHelper.successResp(userDomain.create(param, currentUser));
    } catch (CommonsException e) {
      // Return error information and log the exception.
      return resultHelper.infoResp(logger, e.getErrorType(), e.getMessage());
    } catch (Exception e) {
      // Return unknown error and log the exception.
      return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage());
    }
  }

  /**
   * Show all {@link com.saintdan.framework.vo.UserVO}.
   *
   * @return {@link com.saintdan.framework.vo.UserVO}
   */
  @RequestMapping(value = PathConstant.INDEX, method = RequestMethod.GET)
  public ResultVO index(String sign) {
    try {
      UserParam param = new UserParam();
      param.setSign(sign);
      // Sign validate.
      ResultVO resultVO = validateHelper.validate(param, logger);
      if (resultVO != null) {
        return resultVO;
      }
      return resultHelper.successResp(userDomain.getAllUsers());
    } catch (CommonsException e) {
      // Return error information and log the exception.
      return resultHelper.infoResp(logger, e.getErrorType(), e.getMessage());
    } catch (Exception e) {
      // Return unknown error and log the exception.
      return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage());
    }
  }

  /**
   * Show {@link com.saintdan.framework.vo.UserVO} in {@link com.saintdan.framework.vo.PageVO}.
   *
   * @param pageNo page number
   * @return {@link com.saintdan.framework.vo.UserVO} in {@link com.saintdan.framework.vo.PageVO}
   */
  @RequestMapping(method = RequestMethod.GET)
  public ResultVO page(String pageNo, String sign) {
    try {
      // Init page number.
      if (StringUtils.isBlank(pageNo)) {
        pageNo = "0";
      }
      UserParam param = new UserParam();
      param.setSign(sign);
      // Sign validate.
      ResultVO resultVO = validateHelper.validate(param, logger);
      if (resultVO != null) {
        return resultVO;
      }
      PageRequest pageRequest = new PageRequest(Integer.valueOf(pageNo), CommonsConstant.PAGE_SIZE, QueryHelper.getDefaultSort());
      return resultHelper.successResp(userDomain.getPage(pageRequest));
    } catch (CommonsException e) {
      // Return error information and log the exception.
      return resultHelper.infoResp(logger, e.getErrorType(), e.getMessage());
    } catch (Exception e) {
      // Return unknown error and log the exception.
      return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage());
    }
  }

  /**
   * Show {@link com.saintdan.framework.vo.UserVO} by ID.
   *
   * @param id user's id
   * @return {@link com.saintdan.framework.vo.UserVO}
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResultVO show(@PathVariable String id, String sign) {
    try {
      if (StringUtils.isBlank(id)) {
        return resultHelper.infoResp(ErrorType.SYS0002, String.format(ControllerConstant.PARAM_BLANK, ControllerConstant.ID_PARAM));
      }
      UserParam param = new UserParam(Long.valueOf(id));
      param.setSign(sign);
      // Sign validate.
      ResultVO resultVO = validateHelper.validate(param, logger);
      if (resultVO != null) {
        return resultVO;
      }
      return resultHelper.successResp(userDomain.getUserById(param));
    } catch (CommonsException e) {
      // Return error information and log the exception.
      return resultHelper.infoResp(logger, e.getErrorType(), e.getMessage());
    } catch (Exception e) {
      // Return unknown error and log the exception.
      return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage());
    }
  }

  /**
   * Show {@link com.saintdan.framework.vo.UserVO} by user's usr.
   *
   * @param usr  user's usr
   * @param sign signature
   * @return {@link com.saintdan.framework.vo.UserVO}
   */
  @RequestMapping(value = "/usr/{usr}", method = RequestMethod.GET)
  public ResultVO showByUsr(@PathVariable String usr, String sign) {
    try {
      // If usr or sign is empty, return SYS0002, params error.
      if (StringUtils.isBlank(usr)) {
        final String USR = "usr";
        return resultHelper.infoResp(ErrorType.SYS0002, String.format(ControllerConstant.PARAM_BLANK, USR));
      }
      UserParam param = new UserParam(usr);
      param.setSign(sign);
      // Sign validate.
      ResultVO resultVO = validateHelper.validate(param, logger);
      if (resultVO != null) {
        return resultVO;
      }
      // Return result and message.
      return resultHelper.successResp(userDomain.getUserByUsr(param));
    } catch (CommonsException e) {
      // Return error information and log the exception.
      return resultHelper.infoResp(logger, e.getErrorType(), e.getMessage());
    } catch (Exception e) {
      // Return unknown error and log the exception.
      return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage());
    }
  }

  /**
   * Update {@link User}.
   *
   * @param id    user's id
   * @param param {@link UserParam}
   * @return {@link com.saintdan.framework.vo.UserVO}
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.POST)
  public ResultVO update(@CurrentUser User currentUser, @PathVariable String id, @Valid UserParam param, BindingResult result) {
    try {
      if (StringUtils.isBlank(id)) {
        return resultHelper.infoResp(ErrorType.SYS0002, String.format(ControllerConstant.PARAM_BLANK, ControllerConstant.ID_PARAM));
      }
      // Validate current user, param and sign.
      ResultVO resultVO = validateHelper.validate(result, currentUser, param, logger);
      if (resultVO != null) {
        return resultVO;
      }
      // Update user.
      return resultHelper.successResp(userDomain.update(param, currentUser));
    } catch (CommonsException e) {
      // Return error information and log the exception.
      return resultHelper.infoResp(logger, e.getErrorType(), e.getMessage());
    } catch (Exception e) {
      // Return unknown error and log the exception.
      return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage());
    }
  }

  /**
   * Delete {@link User}.
   *
   * @param id user's id
   * @return {@link com.saintdan.framework.vo.UserVO}
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public ResultVO delete(@CurrentUser User currentUser, @PathVariable String id, String sign) {
    try {
      if (StringUtils.isBlank(id)) {
        return resultHelper.infoResp(ErrorType.SYS0002, String.format(ControllerConstant.PARAM_BLANK, ControllerConstant.ID_PARAM));
      }
      UserParam param = new UserParam(Long.valueOf(id));
      param.setSign(sign);
      // Sign validate.
      ResultVO resultVO = validateHelper.validate(param, logger);
      if (resultVO != null) {
        return resultVO;
      }
      // Delete user.
      userDomain.delete(param, currentUser);
      final String USER = "user";
      return new ResultVO(ResultConstant.OK, OperationStatus.SUCCESS, String.format(ControllerConstant.DELETE, USER));
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

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  @Autowired private ResultHelper resultHelper;

  @Autowired private ValidateHelper validateHelper;

  @Autowired private UserDomain userDomain;

}
