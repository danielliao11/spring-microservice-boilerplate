package com.saintdan.framework.controller;

import com.saintdan.framework.annotation.CurrentUser;
import com.saintdan.framework.component.ResultHelper;
import com.saintdan.framework.component.ValidateHelper;
import com.saintdan.framework.constant.CommonsConstant;
import com.saintdan.framework.constant.ControllerConstant;
import com.saintdan.framework.constant.ResourceURL;
import com.saintdan.framework.constant.ResultConstant;
import com.saintdan.framework.domain.GroupDomain;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.enums.OperationStatus;
import com.saintdan.framework.exception.CommonsException;
import com.saintdan.framework.param.GroupParam;
import com.saintdan.framework.po.User;
import com.saintdan.framework.vo.ResultVO;
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
 * Controller of group.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/17/15
 * @since JDK1.8
 */
@RestController
@RequestMapping(ResourceURL.RESOURCES + ResourceURL.GROUPS)
public class GroupController {

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    /**
     * Create new {@link com.saintdan.framework.po.Group}.
     *
     * @param param     {@link GroupParam}
     * @return          {@link com.saintdan.framework.vo.GroupVO}
     */
    @RequestMapping(value = ResourceURL.SIGN, method = RequestMethod.POST)
    public ResultVO create(@CurrentUser User currentUser, @Valid GroupParam param, BindingResult result, @PathVariable String sign) {
        try {
            // Validate current user, param and sign.
            ResultVO resultVO = validateHelper.validate(result, currentUser, param, sign, logger);
            if (resultVO != null) {
                return resultVO;
            }
            // Return result and message.
            return resultHelper.successResp(groupDomain.create(param, currentUser));
        } catch (CommonsException e) {
            // Return error information and log the exception.
            return resultHelper.infoResp(logger, e.getErrorType());
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage());
        }
    }

    /**
     * Show all {@link com.saintdan.framework.vo.GroupVO}.
     *
     * @return          {@link com.saintdan.framework.vo.GroupVO}
     */
    @RequestMapping(value = ResourceURL.SIGN, method = RequestMethod.GET)
    public ResultVO index(@PathVariable String sign) {
        try {
            GroupParam param = new GroupParam();
            // Sign validate.
            ResultVO resultVO = validateHelper.validate(param, sign, logger);
            if (resultVO != null) {
                return resultVO;
            }
            return resultHelper.successResp(groupDomain.getAllGroups());
        } catch (CommonsException e) {
            // Return error information and log the exception.
            return resultHelper.infoResp(logger, e.getErrorType());
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage());
        }
    }

    /**
     * Show {@link com.saintdan.framework.vo.GroupVO} in {@link com.saintdan.framework.vo.PageVO}.
     *
     * @param pageNo        page number
     * @return              {@link com.saintdan.framework.vo.GroupVO} in {@link com.saintdan.framework.vo.PageVO}
     */
    @RequestMapping(value = "/pageNo={pageNo}" + ResourceURL.SIGN, method = RequestMethod.GET)
    public ResultVO page(@PathVariable String pageNo, @PathVariable String sign) {
        try {
            // Init page number.
            if (StringUtils.isBlank(pageNo)) {
                pageNo = "0";
            }
            GroupParam param = new GroupParam();
            // Sign validate.
            ResultVO resultVO = validateHelper.validate(param, sign, logger);
            if (resultVO != null) {
                return resultVO;
            }
            return resultHelper.successResp(groupDomain.getPage(new PageRequest(Integer.valueOf(pageNo), CommonsConstant.PAGE_SIZE)));
        } catch (CommonsException e) {
            // Return error information and log the exception.
            return resultHelper.infoResp(logger, e.getErrorType());
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage());
        }
    }

    /**
     * Show {@link com.saintdan.framework.vo.GroupVO} by ID.
     *
     * @param id        id of group
     * @return          {@link com.saintdan.framework.vo.GroupVO}
     */
    @RequestMapping(value = "/{id}" + ResourceURL.SIGN, method = RequestMethod.GET)
    public ResultVO show(@PathVariable String id, @PathVariable String sign) {
        try {
            if (StringUtils.isBlank(id)) {
                return resultHelper.infoResp(ErrorType.SYS0002, String.format(ControllerConstant.PARAM_BLANK, ControllerConstant.ID_PARAM));
            }
            GroupParam param = new GroupParam(Long.valueOf(id));
            // Sign validate.
            ResultVO resultVO = validateHelper.validate(param, sign, logger);
            if (resultVO != null) {
                return resultVO;
            }
            return resultHelper.successResp(groupDomain.getGroupById(param));
        } catch (CommonsException e) {
            // Return error information and log the exception.
            return resultHelper.infoResp(logger, e.getErrorType());
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage());
        }
    }

    /**
     * Update {@link com.saintdan.framework.po.Group}.
     *
     * @param id        id of group
     * @param param     {@link GroupParam}
     * @return          {@link com.saintdan.framework.vo.GroupVO}
     */
    @RequestMapping(value = "/{id}" + ResourceURL.SIGN, method = RequestMethod.POST)
    public ResultVO update(@CurrentUser User currentUser, @PathVariable String id, @PathVariable String sign, @Valid GroupParam param, BindingResult result) {
        try {
            if (StringUtils.isBlank(id)) {
                return resultHelper.infoResp(ErrorType.SYS0002, String.format(ControllerConstant.PARAM_BLANK, ControllerConstant.ID_PARAM));
            }
            // Validate current user, param and sign.
            ResultVO resultVO = validateHelper.validate(result, currentUser, param, sign, logger);
            if (resultVO != null) {
                return resultVO;
            }
            // Update group.
            return resultHelper.successResp(groupDomain.update(param, currentUser));
        } catch (CommonsException e) {
            // Return error information and log the exception.
            return resultHelper.infoResp(logger, e.getErrorType());
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage());
        }
    }

    /**
     * Delete group.
     *
     * @param id        group's id
     * @return          {@link com.saintdan.framework.vo.GroupVO}
     */
    @RequestMapping(value = "/{id}" + ResourceURL.SIGN, method = RequestMethod.DELETE)
    public ResultVO delete(@CurrentUser User currentUser, @PathVariable String id, @PathVariable String sign) {
        try {
            if (StringUtils.isBlank(id)) {
                return resultHelper.infoResp(ErrorType.SYS0002, String.format(ControllerConstant.PARAM_BLANK, ControllerConstant.ID_PARAM));
            }
            GroupParam param = new GroupParam(Long.valueOf(id));
            // Sign validate.
            ResultVO resultVO = validateHelper.validate(param, sign, logger);
            if (resultVO != null) {
                return resultVO;
            }
            // Delete group.
            groupDomain.delete(param, currentUser);
            final String ROLE = "group";
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

    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    private ResultHelper resultHelper;

    @Autowired
    private ValidateHelper validateHelper;

    @Autowired
    private GroupDomain groupDomain;
}
