package com.saintdan.framework.controller;

import com.saintdan.framework.component.ResultHelper;
import com.saintdan.framework.component.SignHelper;
import com.saintdan.framework.constant.CommonsConstant;
import com.saintdan.framework.constant.ControllerConstant;
import com.saintdan.framework.constant.ResourceURL;
import com.saintdan.framework.constant.ResultConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.enums.OperationStatus;
import com.saintdan.framework.exception.RoleException;
import com.saintdan.framework.param.RoleParam;
import com.saintdan.framework.service.RoleService;
import com.saintdan.framework.vo.ResultVO;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.PageRequest;
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
@PropertySource("classpath:api.properties")
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
    @RequestMapping(value = ResourceURL.ROLES + ResourceURL.SIGN, method = RequestMethod.POST)
    public ResultVO create(RoleParam param, @PathVariable String sign) {
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
    @RequestMapping(value = ResourceURL.ROLES + ResourceURL.SIGN, method = RequestMethod.GET)
    public ResultVO index(@PathVariable String sign) {
        try {
            RoleParam param = new RoleParam();
            // Prepare to validate signature.
            param.setSign(new String(Base64.decodeBase64(sign.getBytes())));
            // Sign verification.
            if (!signHelper.signCheck(PUBLIC_KEY, param, sign)) {
                // Return rsa signature failed information and log the exception.
                return resultHelper.infoResp(log, ErrorType.SGN0021);
            }
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
     * Show roles' page.
     *
     * @param pageNo        page number
     * @return              roles' page
     */
    @RequestMapping(value = ResourceURL.ROLES + "/pageNo={pageNo}" + ResourceURL.SIGN, method = RequestMethod.GET)
    public ResultVO page(@PathVariable String pageNo, @PathVariable String sign) {
        try {
            RoleParam param = new RoleParam();
            // Prepare to validate signature.
            param.setSign(new String(Base64.decodeBase64(sign.getBytes())));
            // Sign verification.
            if (!signHelper.signCheck(PUBLIC_KEY, param, sign)) {
                // Return rsa signature failed information and log the exception.
                return resultHelper.infoResp(log, ErrorType.SGN0021);
            }
            // Init page number.
            if (StringUtils.isBlank(pageNo)) {
                pageNo = "0";
            }
            return roleService.getPage(new PageRequest(Integer.valueOf(pageNo), CommonsConstant.PAGE_SIZE));
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
    @RequestMapping(value = ResourceURL.ROLES + "/{id}" + ResourceURL.SIGN, method = RequestMethod.GET)
    public ResultVO show(@PathVariable String id, @PathVariable String sign) {
        try {
            if (StringUtils.isBlank(id)) {
                return resultHelper.infoResp(ErrorType.SYS0002, String.format(ControllerConstant.PARAM_BLANK, ControllerConstant.ID_PARAM));
            }
            RoleParam param = new RoleParam(Long.valueOf(id));
            // Prepare to validate signature.
            param.setSign(new String(Base64.decodeBase64(sign.getBytes())));
            // Sign verification.
            if (!signHelper.signCheck(PUBLIC_KEY, param, sign)) {
                // Return rsa signature failed information and log the exception.
                return resultHelper.infoResp(log, ErrorType.SGN0021);
            }
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
    @RequestMapping(value = ResourceURL.ROLES + "/{id}" + ResourceURL.SIGN, method = RequestMethod.POST)
    public ResultVO update(@PathVariable String id, @PathVariable String sign, RoleParam param) {
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
            // Prepare to validate signature.
            param.setSign(new String(Base64.decodeBase64(sign.getBytes())));
            // Sign verification.
            if (!signHelper.signCheck(PUBLIC_KEY, param, sign)) {
                // Return rsa signature failed information and log the exception.
                return resultHelper.infoResp(log, ErrorType.SGN0021);
            }
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
    @RequestMapping(value = ResourceURL.ROLES + "/{id}" + ResourceURL.SIGN, method = RequestMethod.DELETE)
    public ResultVO delete(@PathVariable String id, @PathVariable String sign) {
        try {
            if (StringUtils.isBlank(id)) {
                return resultHelper.infoResp(ErrorType.SYS0002, String.format(ControllerConstant.PARAM_BLANK, ControllerConstant.ID_PARAM));
            }
            RoleParam param = new RoleParam(Long.valueOf(id));
            // Prepare to validate signature.
            param.setSign(new String(Base64.decodeBase64(sign.getBytes())));
            // Sign verification.
            if (!signHelper.signCheck(PUBLIC_KEY, param, sign)) {
                // Return rsa signature failed information and log the exception.
                return resultHelper.infoResp(log, ErrorType.SGN0021);
            }
            // Delete role.
            roleService.delete(param);
            final String ROLE = "role";
            return new ResultVO(ResultConstant.OK, OperationStatus.SUCCESS, String.format(ControllerConstant.DELETE, ROLE));
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

    @Value("${opposite.end1.publicKey}")
    private String PUBLIC_KEY;

    @Autowired
    private ResultHelper resultHelper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private SignHelper signHelper;
}
