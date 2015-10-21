package com.saintdan.framework.controller;

import com.saintdan.framework.component.ResultHelper;
import com.saintdan.framework.component.SignHelper;
import com.saintdan.framework.constant.ControllerConstant;
import com.saintdan.framework.constant.ResourceURL;
import com.saintdan.framework.constant.ResultConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.enums.OperationStatus;
import com.saintdan.framework.exception.ResourceException;
import com.saintdan.framework.param.ResourceParam;
import com.saintdan.framework.service.ResourceService;
import com.saintdan.framework.vo.ResultVO;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Resource's controller.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/17/15
 * @since JDK1.8
 */
@PropertySource("classpath:api.properties")
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
    public ResultVO create(ResourceParam param, @PathVariable String sign) {
        try {
            // Get incorrect params.
            String validateContent = param.getIncorrectParams();
            if (!StringUtils.isBlank(validateContent)) {
                // If validate failed, return error message.
                return new ResultVO(ErrorType.SYS0002.value(), OperationStatus.FAILURE,
                        String.format(ControllerConstant.PARAM_BLANK, validateContent));
            }// Prepare to validate signature.
            param.setSign(new String(Base64.decodeBase64(sign.getBytes())));
            // Sign verification.
            if (!signHelper.signCheck(PUBLIC_KEY, param, sign)) {
                // Return rsa signature failed information and log the exception.
                return resultHelper.infoResp(log, ErrorType.SGN0021);
            }
            // Return result and message.
            return resourceService.create(param);
        } catch (ResourceException e) {
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
            // Prepare to validate signature.
            param.setSign(new String(Base64.decodeBase64(sign.getBytes())));
            // Sign verification.
            if (!signHelper.signCheck(PUBLIC_KEY, param, sign)) {
                // Return rsa signature failed information and log the exception.
                return resultHelper.infoResp(log, ErrorType.SGN0021);
            }
            return resourceService.getAllResources();
        } catch (ResourceException e) {
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
            // Prepare to validate signature.
            param.setSign(new String(Base64.decodeBase64(sign.getBytes())));
            // Sign verification.
            if (!signHelper.signCheck(PUBLIC_KEY, param, sign)) {
                // Return rsa signature failed information and log the exception.
                return resultHelper.infoResp(log, ErrorType.SGN0021);
            }
            return resourceService.getResourceById(param);
        } catch (ResourceException e) {
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
    public ResultVO update(@PathVariable String id, @PathVariable String sign, ResourceParam param) {
        try {
            if (StringUtils.isBlank(id)) {
                return resultHelper.infoResp(ErrorType.SYS0002, String.format(ControllerConstant.PARAM_BLANK, ControllerConstant.ID_PARAM));
            }
            // Get incorrect params.
            String validateContent = param.getIncorrectParams();
            if (!StringUtils.isBlank(validateContent)) {
                // If validate failed, return error message.
                return new ResultVO(ErrorType.SYS0002.value(), OperationStatus.FAILURE,
                        String.format(ControllerConstant.PARAM_BLANK, validateContent));
            }
            // Set resource's ID.
            param.setId(Long.valueOf(id));
            // Prepare to validate signature.
            param.setSign(new String(Base64.decodeBase64(sign.getBytes())));
            // Sign verification.
            if (!signHelper.signCheck(PUBLIC_KEY, param, sign)) {
                // Return rsa signature failed information and log the exception.
                return resultHelper.infoResp(log, ErrorType.SGN0021);
            }
            // Update resource.
            return resourceService.update(param);
        } catch (ResourceException e) {
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
    public ResultVO delete(@PathVariable String id, @PathVariable String sign) {
        try {
            if (StringUtils.isBlank(id)) {
                return resultHelper.infoResp(ErrorType.SYS0002, String.format(ControllerConstant.PARAM_BLANK, ControllerConstant.ID_PARAM));
            }
            // Prepare to validate signature.
            ResourceParam param = new ResourceParam(Long.valueOf(id));
            param.setSign(new String(Base64.decodeBase64(sign.getBytes())));
            // Sign verification.
            if (!signHelper.signCheck(PUBLIC_KEY, param, sign)) {
                // Return rsa signature failed information and log the exception.
                return resultHelper.infoResp(log, ErrorType.SGN0021);
            }
            // Delete resource.
            resourceService.delete(param);
            final String ROLE = "resource";
            return new ResultVO(ResultConstant.OK, OperationStatus.SUCCESS, String.format(ControllerConstant.INDEX, ROLE));
        } catch (ResourceException e) {
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

    @Value("${opposite.end1.publicKey}")
    private String PUBLIC_KEY;

    @Autowired
    private ResultHelper resultHelper;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private SignHelper signHelper;
}
