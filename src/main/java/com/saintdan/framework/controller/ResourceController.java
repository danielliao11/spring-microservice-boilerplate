package com.saintdan.framework.controller;

import com.saintdan.framework.annotation.CurrentUser;
import com.saintdan.framework.component.ResultHelper;
import com.saintdan.framework.component.ValidateHelper;
import com.saintdan.framework.constant.CommonsConstant;
import com.saintdan.framework.constant.ControllerConstant;
import com.saintdan.framework.constant.ResourceURL;
import com.saintdan.framework.constant.ResultConstant;
import com.saintdan.framework.domain.ResourceDomain;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.enums.OperationStatus;
import com.saintdan.framework.exception.CommonsException;
import com.saintdan.framework.param.ResourceParam;
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
 * Controller of resource.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/17/15
 * @since JDK1.8
 */
@RestController
@RequestMapping(ResourceURL.RESOURCES + ResourceURL.RESOURCES)
public class ResourceController {

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    /**
     * Create new {@link com.saintdan.framework.po.Resource}.
     *
     * @param param     {@link ResourceParam}
     * @return          {@link com.saintdan.framework.vo.ResourceVO}
     */
    @RequestMapping(value = ResourceURL.SIGN, method = RequestMethod.POST)
    public ResultVO create(@CurrentUser User currentUser, @Valid ResourceParam param, BindingResult result, @PathVariable String sign) {
        try {
            // Validate current user, param and sign.
            ResultVO resultVO = validateHelper.validate(result, currentUser, param, sign, logger);
            if (resultVO != null) {
                return resultVO;
            }
            // Return result and message.
            return resultHelper.successResp(resourceDomain.create(param, currentUser));
        } catch (CommonsException e) {
            // Return error information and log the exception.
            return resultHelper.infoResp(logger, e.getErrorType());
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage());
        }
    }

    /**
     * Show all {@link com.saintdan.framework.vo.ResourceVO}.
     *
     * @return          {@link com.saintdan.framework.vo.ResourceVO}
     */
    @RequestMapping(value = ResourceURL.SIGN, method = RequestMethod.GET)
    public ResultVO index(@PathVariable String sign) {
        try {
            ResourceParam param = new ResourceParam();
            // Sign validate.
            ResultVO resultVO = validateHelper.validate(param, sign, logger);
            if (resultVO != null) {
                return resultVO;
            }
            return resultHelper.successResp(resourceDomain.getAllResources());
        } catch (CommonsException e) {
            // Return error information and log the exception.
            return resultHelper.infoResp(logger, e.getErrorType());
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage());
        }
    }

    /**
     * Show {@link com.saintdan.framework.vo.ResourceVO} in {@link com.saintdan.framework.vo.PageVO}.
     *
     * @param pageNo        page number
     * @return              {@link com.saintdan.framework.vo.ResourceVO} in {@link com.saintdan.framework.vo.PageVO}.
     */
    @RequestMapping(value = "/pageNo={pageNo}" + ResourceURL.SIGN, method = RequestMethod.GET)
    public ResultVO page(@PathVariable String pageNo, @PathVariable String sign) {
        try {
            // Init page number.
            if (StringUtils.isBlank(pageNo)) {
                pageNo = "0";
            }
            ResourceParam param = new ResourceParam();
            // Sign validate.
            ResultVO resultVO = validateHelper.validate(param, sign, logger);
            if (resultVO != null) {
                return resultVO;
            }
            return resultHelper.successResp(resourceDomain.getPage(new PageRequest(Integer.valueOf(pageNo), CommonsConstant.PAGE_SIZE)));
        } catch (CommonsException e) {
            // Return error information and log the exception.
            return resultHelper.infoResp(logger, e.getErrorType());
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage());
        }
    }

    /**
     * Show {@link com.saintdan.framework.vo.ResourceVO} by ID.
     *
     * @param id        id of resource
     * @return          {@link com.saintdan.framework.vo.ResourceVO}
     */
    @RequestMapping(value = "/{id}" + ResourceURL.SIGN, method = RequestMethod.GET)
    public ResultVO show(@PathVariable String id, @PathVariable String sign) {
        try {
            if (StringUtils.isBlank(id)) {
                return resultHelper.infoResp(ErrorType.SYS0002, String.format(ControllerConstant.PARAM_BLANK, ControllerConstant.ID_PARAM));
            }
            ResourceParam param = new ResourceParam(Long.valueOf(id));
            // Sign validate.
            ResultVO resultVO = validateHelper.validate(param, sign, logger);
            if (resultVO != null) {
                return resultVO;
            }
            return resultHelper.successResp(resourceDomain.getResourceById(param));
        } catch (CommonsException e) {
            // Return error information and log the exception.
            return resultHelper.infoResp(logger, e.getErrorType());
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage());
        }
    }

    /**
     * Update {@link com.saintdan.framework.po.Resource}.
     *
     * @param id        id of resource
     * @param param     {@link ResourceParam}
     * @return          {@link com.saintdan.framework.vo.ResourceVO}
     */
    @RequestMapping(value = "/{id}" + ResourceURL.SIGN, method = RequestMethod.POST)
    public ResultVO update(@CurrentUser User currentUser, @PathVariable String id, @PathVariable String sign, @Valid ResourceParam param, BindingResult result) {
        try {
            if (StringUtils.isBlank(id)) {
                return resultHelper.infoResp(ErrorType.SYS0002, String.format(ControllerConstant.PARAM_BLANK, ControllerConstant.ID_PARAM));
            }
            // Validate current user, param and sign.
            ResultVO resultVO = validateHelper.validate(result, currentUser, param, sign, logger);
            if (resultVO != null) {
                return resultVO;
            }
            // Update resource.
            return resultHelper.successResp(resourceDomain.update(param, currentUser));
        } catch (CommonsException e) {
            // Return error information and log the exception.
            return resultHelper.infoResp(logger, e.getErrorType());
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage());
        }
    }

    /**
     * Delete {@link com.saintdan.framework.po.Resource}.
     *
     * @param id        id of resource
     * @return          {@link com.saintdan.framework.vo.ResourceVO}
     */
    @RequestMapping(value = "/{id}" + ResourceURL.SIGN, method = RequestMethod.DELETE)
    public ResultVO delete(@CurrentUser User currentUser, @PathVariable String id, @PathVariable String sign) {
        try {
            if (StringUtils.isBlank(id)) {
                return resultHelper.infoResp(ErrorType.SYS0002, String.format(ControllerConstant.PARAM_BLANK, ControllerConstant.ID_PARAM));
            }
            ResourceParam param = new ResourceParam(Long.valueOf(id));
            // Sign validate.
            ResultVO resultVO = validateHelper.validate(param, sign, logger);
            if (resultVO != null) {
                return resultVO;
            }
            // Delete resource.
            resourceDomain.delete(param, currentUser);
            final String ROLE = "resource";
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

    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

    @Autowired
    private ResultHelper resultHelper;

    @Autowired
    private ValidateHelper validateHelper;

    @Autowired
    private ResourceDomain resourceDomain;
}
