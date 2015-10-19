package com.saintdan.framework.controller;

import com.saintdan.framework.component.ResultHelper;
import com.saintdan.framework.component.SignHelper;
import com.saintdan.framework.constant.ControllerConstant;
import com.saintdan.framework.constant.ResourceURL;
import com.saintdan.framework.constant.ResultConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.enums.OperationStatus;
import com.saintdan.framework.exception.RoleException;
import com.saintdan.framework.param.RoleParam;
import com.saintdan.framework.service.RoleService;
import com.saintdan.framework.vo.ResultVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Role's controller.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/17/15
 * @since JDK1.8
 */
@RestController
@RequestMapping(ResourceURL.RESOURCES)
public class RoleController {

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    /**
     * Create new role.
     *
     * @param param     role's param
     * @return          role's result
     */
    @RequestMapping(value = ResourceURL.ROLES , method = RequestMethod.POST)
    public ResultVO create(RoleParam param) {
        try {
            // Get incorrect params.
            String validateContent = param.getIncorrectParams();
            if (!StringUtils.isBlank(validateContent)) {
                // If validate failed, return error message.
                return new ResultVO(ErrorType.SYS0002.value(), OperationStatus.FAILURE,
                        String.format(ControllerConstant.PARAM_BLANK, validateContent));
            }
            // Return result and message.
            return roleService.create(param);
        } catch (RoleException e) {
            // Return error information and log the exception.
            return resultHelper.infoResp(log, e.getErrorType());
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(log, e, ErrorType.UNKNOWN, e.getMessage());
        }
    }

    /**
     * Show all roles' VO.
     *
     * @return          roles' result
     */
    @RequestMapping(value = ResourceURL.ROLES, method = RequestMethod.GET)
    public ResultVO index() {
        try {
            return roleService.getAllRoles();
        } catch (RoleException e) {
            // Return error information and log the exception.
            return resultHelper.infoResp(log, e.getErrorType());
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(log, e, ErrorType.UNKNOWN, e.getMessage());
        }
    }

    /**
     * Show role by ID.
     *
     * @param id        role's id
     * @return          role's result
     */
    @RequestMapping(value = ResourceURL.ROLES + "/{id}", method = RequestMethod.GET)
    public ResultVO show(@PathVariable String id) {
        try {
            if (StringUtils.isBlank(id)) {
                return resultHelper.infoResp(ErrorType.SYS0002, String.format(ControllerConstant.PARAM_BLANK, ControllerConstant.ID_PARAM));
            }
            RoleParam param = new RoleParam(Long.valueOf(id));
            return roleService.getRoleById(param);
        } catch (RoleException e) {
            // Return error information and log the exception.
            return resultHelper.infoResp(log, e.getErrorType());
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(log, e, ErrorType.UNKNOWN, e.getMessage());
        }
    }

    /**
     * Update role.
     *
     * @param id        role's id
     * @param param     role's params
     * @return          role's result
     */
    @RequestMapping(value = ResourceURL.ROLES + "/{id}", method = RequestMethod.POST)
    public ResultVO update(@PathVariable String id, RoleParam param) {
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
            // Set role's ID.
            param.setId(Long.valueOf(id));
            // Update role.
            return roleService.update(param);
        } catch (RoleException e) {
            // Return error information and log the exception.
            return resultHelper.infoResp(log, e.getErrorType());
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(log, e, ErrorType.UNKNOWN, e.getMessage());
        }
    }

    /**
     * Delete role.
     *
     * @param id        role's id
     * @return          role's result
     */
    public ResultVO delete(@PathVariable String id) {
        try {
            if (StringUtils.isBlank(id)) {
                return resultHelper.infoResp(ErrorType.SYS0002, String.format(ControllerConstant.PARAM_BLANK, ControllerConstant.ID_PARAM));
            }
            // Delete role.
            roleService.delete(new RoleParam(Long.valueOf(id)));
            final String ROLE = "role";
            return new ResultVO(ResultConstant.OK, OperationStatus.SUCCESS, String.format(ControllerConstant.INDEX, ROLE));
        } catch (RoleException e) {
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

    private static final Log log = LogFactory.getLog(RoleController.class);

    @Autowired
    private ResultHelper resultHelper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private SignHelper signHelper;
}
