package com.saintdan.framework.controller;

import com.saintdan.framework.annotation.CurrentUser;
import com.saintdan.framework.component.ResultHelper;
import com.saintdan.framework.component.ValidateHelper;
import com.saintdan.framework.constant.CommonsConstant;
import com.saintdan.framework.constant.ControllerConstant;
import com.saintdan.framework.constant.ResourceURL;
import com.saintdan.framework.constant.ResultConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.enums.OperationStatus;
import com.saintdan.framework.exception.CommonsException;
import com.saintdan.framework.param.ResourceParam;
import com.saintdan.framework.po.User;
import com.saintdan.framework.domain.ResourceDomain;
import com.saintdan.framework.vo.ResultVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Resource's controller.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/17/15
 * @since JDK1.8
 */
@RestController
@RequestMapping(ResourceURL.RESOURCES)
public class ResourceController {

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    /**
     * Create new resource.
     *
     * @param param     resource's param
     * @return          resource's result
     */
    @RequestMapping(value = ResourceURL.RESOURCES + ResourceURL.SIGN, method = RequestMethod.POST)
    public ResultVO create(@CurrentUser User currentUser, @Valid ResourceParam param, BindingResult result, @PathVariable String sign) {
        try {
            // Validate current user, param and sign.
            ResultVO resultVO = validateHelper.validate(result, currentUser, param, sign, log);
            if (resultVO != null) {
                return resultVO;
            }
            // Return result and message.
            return resourceService.create(param, currentUser);
        } catch (CommonsException e) {
            // Return error information and log the exception.
            return resultHelper.infoResp(log, e.getErrorType());
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(log, e, ErrorType.UNKNOWN, e.getMessage());
        }
    }

    /**
     * Show all resources' VO.
     *
     * @return          resources' result
     */
    @RequestMapping(value = ResourceURL.RESOURCES + ResourceURL.SIGN, method = RequestMethod.GET)
    public ResultVO index(@PathVariable String sign) {
        try {
            ResourceParam param = new ResourceParam();
            // Sign validate.
            ResultVO resultVO = validateHelper.validate(param, sign, log);
            if (resultVO != null) {
                return resultVO;
            }
            return resourceService.getAllResources();
        } catch (CommonsException e) {
            // Return error information and log the exception.
            return resultHelper.infoResp(log, e.getErrorType());
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(log, e, ErrorType.UNKNOWN, e.getMessage());
        }
    }

    /**
     * Show resources' page.
     *
     * @param pageNo        page number
     * @return              resources' page
     */
    @RequestMapping(value = ResourceURL.RESOURCES + "/pageNo={pageNo}" + ResourceURL.SIGN, method = RequestMethod.GET)
    public ResultVO page(@PathVariable String pageNo, @PathVariable String sign) {
        try {
            // Init page number.
            if (StringUtils.isBlank(pageNo)) {
                pageNo = "0";
            }
            ResourceParam param = new ResourceParam();
            // Sign validate.
            ResultVO resultVO = validateHelper.validate(param, sign, log);
            if (resultVO != null) {
                return resultVO;
            }
            return resourceService.getPage(new PageRequest(Integer.valueOf(pageNo), CommonsConstant.PAGE_SIZE));
        } catch (CommonsException e) {
            // Return error information and log the exception.
            return resultHelper.infoResp(log, e.getErrorType());
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(log, e, ErrorType.UNKNOWN, e.getMessage());
        }
    }

    /**
     * Show resource by ID.
     *
     * @param id        resource's id
     * @return          resource's result
     */
    @RequestMapping(value = ResourceURL.RESOURCES + "/{id}" + ResourceURL.SIGN, method = RequestMethod.GET)
    public ResultVO show(@PathVariable String id, @PathVariable String sign) {
        try {
            if (StringUtils.isBlank(id)) {
                return resultHelper.infoResp(ErrorType.SYS0002, String.format(ControllerConstant.PARAM_BLANK, ControllerConstant.ID_PARAM));
            }
            ResourceParam param = new ResourceParam(Long.valueOf(id));
            // Sign validate.
            ResultVO resultVO = validateHelper.validate(param, sign, log);
            if (resultVO != null) {
                return resultVO;
            }
            return resourceService.getResourceById(param);
        } catch (CommonsException e) {
            // Return error information and log the exception.
            return resultHelper.infoResp(log, e.getErrorType());
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(log, e, ErrorType.UNKNOWN, e.getMessage());
        }
    }

    /**
     * Update resource.
     *
     * @param id        resource's id
     * @param param     resource's params
     * @return          resource's result
     */
    @RequestMapping(value = ResourceURL.RESOURCES + "/{id}" + ResourceURL.SIGN, method = RequestMethod.POST)
    public ResultVO update(@CurrentUser User currentUser, @PathVariable String id, @PathVariable String sign, @Valid ResourceParam param, BindingResult result) {
        try {
            if (StringUtils.isBlank(id)) {
                return resultHelper.infoResp(ErrorType.SYS0002, String.format(ControllerConstant.PARAM_BLANK, ControllerConstant.ID_PARAM));
            }
            // Validate current user, param and sign.
            ResultVO resultVO = validateHelper.validate(result, currentUser, param, sign, log);
            if (resultVO != null) {
                return resultVO;
            }
            // Update resource.
            return resourceService.update(param, currentUser);
        } catch (CommonsException e) {
            // Return error information and log the exception.
            return resultHelper.infoResp(log, e.getErrorType());
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(log, e, ErrorType.UNKNOWN, e.getMessage());
        }
    }

    /**
     * Delete resource.
     *
     * @param id        resource's id
     * @return          resource's result
     */
    @RequestMapping(value = ResourceURL.RESOURCES + "/{id}" + ResourceURL.SIGN, method = RequestMethod.DELETE)
    public ResultVO delete(@CurrentUser User currentUser, @PathVariable String id, @PathVariable String sign) {
        try {
            if (StringUtils.isBlank(id)) {
                return resultHelper.infoResp(ErrorType.SYS0002, String.format(ControllerConstant.PARAM_BLANK, ControllerConstant.ID_PARAM));
            }
            ResourceParam param = new ResourceParam(Long.valueOf(id));
            // Sign validate.
            ResultVO resultVO = validateHelper.validate(param, sign, log);
            if (resultVO != null) {
                return resultVO;
            }
            // Delete resource.
            resourceService.delete(param, currentUser);
            final String ROLE = "resource";
            return new ResultVO(ResultConstant.OK, OperationStatus.SUCCESS, String.format(ControllerConstant.DELETE, ROLE));
        } catch (CommonsException e) {
            // Return error information and log the exception.
            return resultHelper.infoResp(log, e.getErrorType());
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(log, e, ErrorType.UNKNOWN, e.getMessage());
        }
    }

    // ------------------------
    // PRIVATE FIELDS
    // ------------------------

    private static final Log log = LogFactory.getLog(ResourceController.class);

    @Autowired
    private ResultHelper resultHelper;

    @Autowired
    private ValidateHelper validateHelper;

    @Autowired
    private ResourceDomain resourceService;
}
