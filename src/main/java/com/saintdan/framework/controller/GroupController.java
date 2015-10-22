package com.saintdan.framework.controller;

import com.saintdan.framework.component.ResultHelper;
import com.saintdan.framework.component.SignHelper;
import com.saintdan.framework.constant.ControllerConstant;
import com.saintdan.framework.constant.ResourceURL;
import com.saintdan.framework.constant.ResultConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.enums.OperationStatus;
import com.saintdan.framework.exception.GroupException;
import com.saintdan.framework.param.GroupParam;
import com.saintdan.framework.service.GroupService;
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
 * Group's controller.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/17/15
 * @since JDK1.8
 */
@PropertySource("classpath:api.properties")
@RestController
@RequestMapping(ResourceURL.RESOURCES)
public class GroupController {

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    /**
     * Create new group.
     *
     * @param param     group's param
     * @return          group's result
     */
    @RequestMapping(value = ResourceURL.GROUPS + ResourceURL.SIGN, method = RequestMethod.POST)
    public ResultVO create(GroupParam param, @PathVariable String sign) {
        try {
            // Get incorrect params.
            String validateContent = param.getIncorrectParams();
            if (!StringUtils.isBlank(validateContent)) {
                // If validate failed, return error message.
                return new ResultVO(ErrorType.SYS0002.value(), OperationStatus.FAILURE,
                        String.format(ControllerConstant.PARAM_BLANK, validateContent));
            }
            // Prepare to validate signature.
            param.setSign(new String(Base64.decodeBase64(sign.getBytes())));
            // Sign verification.
            if (!signHelper.signCheck(PUBLIC_KEY, param, sign)) {
                // Return rsa signature failed information and log the exception.
                return resultHelper.infoResp(log, ErrorType.SGN0021);
            }
            // Return result and message.
            return groupService.create(param);
        } catch (GroupException e) {
            // Return error information and log the exception.
            return resultHelper.infoResp(log, e.getErrorType());
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(log, e, ErrorType.UNKNOWN, e.getMessage());
        }
    }

    /**
     * Show all groups' VO.
     *
     * @return          groups' result
     */
    @RequestMapping(value = ResourceURL.GROUPS + ResourceURL.SIGN, method = RequestMethod.GET)
    public ResultVO index(@PathVariable String sign) {
        try {
            GroupParam param = new GroupParam();
            // Prepare to validate signature.
            param.setSign(new String(Base64.decodeBase64(sign.getBytes())));
            // Sign verification.
            if (!signHelper.signCheck(PUBLIC_KEY, param, sign)) {
                // Return rsa signature failed information and log the exception.
                return resultHelper.infoResp(log, ErrorType.SGN0021);
            }
            return groupService.getAllGroups();
        } catch (GroupException e) {
            // Return error information and log the exception.
            return resultHelper.infoResp(log, e.getErrorType());
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(log, e, ErrorType.UNKNOWN, e.getMessage());
        }
    }

    /**
     * Show group by ID.
     *
     * @param id        group's id
     * @return          group's result
     */
    @RequestMapping(value = ResourceURL.GROUPS + "/{id}" + ResourceURL.SIGN, method = RequestMethod.GET)
    public ResultVO show(@PathVariable String id, @PathVariable String sign) {
        try {
            if (StringUtils.isBlank(id)) {
                return resultHelper.infoResp(ErrorType.SYS0002, String.format(ControllerConstant.PARAM_BLANK, ControllerConstant.ID_PARAM));
            }
            GroupParam param = new GroupParam(Long.valueOf(id));
            // Prepare to validate signature.
            param.setSign(new String(Base64.decodeBase64(sign.getBytes())));
            // Sign verification.
            if (!signHelper.signCheck(PUBLIC_KEY, param, sign)) {
                // Return rsa signature failed information and log the exception.
                return resultHelper.infoResp(log, ErrorType.SGN0021);
            }
            return groupService.getGroupById(param);
        } catch (GroupException e) {
            // Return error information and log the exception.
            return resultHelper.infoResp(log, e.getErrorType());
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(log, e, ErrorType.UNKNOWN, e.getMessage());
        }
    }

    /**
     * Update group.
     *
     * @param id        group's id
     * @param param     group's params
     * @return          group's result
     */
    @RequestMapping(value = ResourceURL.GROUPS + "/{id}" + ResourceURL.SIGN, method = RequestMethod.POST)
    public ResultVO update(@PathVariable String id, @PathVariable String sign, GroupParam param) {
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
            // Set group's ID.
            param.setId(Long.valueOf(id));
            // Prepare to validate signature.
            param.setSign(new String(Base64.decodeBase64(sign.getBytes())));
            // Sign verification.
            if (!signHelper.signCheck(PUBLIC_KEY, param, sign)) {
                // Return rsa signature failed information and log the exception.
                return resultHelper.infoResp(log, ErrorType.SGN0021);
            }
            // Update group.
            return groupService.update(param);
        } catch (GroupException e) {
            // Return error information and log the exception.
            return resultHelper.infoResp(log, e.getErrorType());
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(log, e, ErrorType.UNKNOWN, e.getMessage());
        }
    }

    /**
     * Delete group.
     *
     * @param id        group's id
     * @return          group's result
     */
    @RequestMapping(value = ResourceURL.GROUPS + "/{id}" + ResourceURL.SIGN, method = RequestMethod.DELETE)
    public ResultVO delete(@PathVariable String id, @PathVariable String sign) {
        try {
            if (StringUtils.isBlank(id)) {
                return resultHelper.infoResp(ErrorType.SYS0002, String.format(ControllerConstant.PARAM_BLANK, ControllerConstant.ID_PARAM));
            }
            GroupParam param = new GroupParam(Long.valueOf(id));
            // Prepare to validate signature.
            param.setSign(new String(Base64.decodeBase64(sign.getBytes())));
            // Sign verification.
            if (!signHelper.signCheck(PUBLIC_KEY, param, sign)) {
                // Return rsa signature failed information and log the exception.
                return resultHelper.infoResp(log, ErrorType.SGN0021);
            }
            // Delete group.
            groupService.delete(param);
            final String ROLE = "group";
            return new ResultVO(ResultConstant.OK, OperationStatus.SUCCESS, String.format(ControllerConstant.DELETE, ROLE));
        } catch (GroupException e) {
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

    private static final Log log = LogFactory.getLog(GroupController.class);

    @Value("${opposite.end1.publicKey}")
    private String PUBLIC_KEY;

    @Autowired
    private ResultHelper resultHelper;

    @Autowired
    private GroupService groupService;

    @Autowired
    private SignHelper signHelper;
}
