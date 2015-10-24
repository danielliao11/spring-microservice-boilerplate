package com.saintdan.framework.controller;

import com.saintdan.framework.component.ResultHelper;
import com.saintdan.framework.component.SignHelper;
import com.saintdan.framework.constant.ControllerConstant;
import com.saintdan.framework.constant.ResourceURL;
import com.saintdan.framework.constant.ResultConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.enums.OperationStatus;
import com.saintdan.framework.exception.SignatureException;
import com.saintdan.framework.exception.UserException;
import com.saintdan.framework.param.UserParam;
import com.saintdan.framework.service.UserService;
import com.saintdan.framework.vo.ResultVO;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * User's controller.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/25/15
 * @since JDK1.8
 */
@PropertySource("classpath:api.properties")
@RestController
@RequestMapping(ResourceURL.RESOURCES)
public class UserController {

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    /**
     * Create new user.
     *
     * @param param     user's param
     * @return          user's result
     */
    @RequestMapping(value = ResourceURL.USERS + ResourceURL.SIGN, method = RequestMethod.POST)
    public ResultVO create(UserParam param, @PathVariable String sign) {
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
            return userService.create(param);
        } catch (UserException e) {
            // Return error information and log the exception.
            return resultHelper.infoResp(log, e.getErrorType());
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(log, e, ErrorType.UNKNOWN, e.getMessage());
        }
    }

    /**
     * Show all users' VO.
     *
     * @return          users' result
     */
    @RequestMapping(value = ResourceURL.USERS + ResourceURL.SIGN, method = RequestMethod.GET)
    public ResultVO index(@PathVariable String sign) {
        try {
            UserParam param = new UserParam();
            // Prepare to validate signature.
            param.setSign(new String(Base64.decodeBase64(sign.getBytes())));
            // Sign verification.
            if (!signHelper.signCheck(PUBLIC_KEY, param, sign)) {
                // Return rsa signature failed information and log the exception.
                return resultHelper.infoResp(log, ErrorType.SGN0021);
            }
            return userService.getAllUsers();
        } catch (UserException e) {
            // Return error information and log the exception.
            return resultHelper.infoResp(log, e.getErrorType());
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(log, e, ErrorType.UNKNOWN, e.getMessage());
        }
    }

    /**
     * Show user by ID.
     *
     * @param id        user's id
     * @return          user's result
     */
    @RequestMapping(value = ResourceURL.USERS + "/{id}" + ResourceURL.SIGN, method = RequestMethod.GET)
    public ResultVO show(@PathVariable String id, @PathVariable String sign) {
        try {
            if (StringUtils.isBlank(id)) {
                return resultHelper.infoResp(ErrorType.SYS0002, String.format(ControllerConstant.PARAM_BLANK, ControllerConstant.ID_PARAM));
            }
            UserParam param = new UserParam(Long.valueOf(id));
            // Prepare to validate signature.
            param.setSign(new String(Base64.decodeBase64(sign.getBytes())));
            // Sign verification.
            if (!signHelper.signCheck(PUBLIC_KEY, param, sign)) {
                // Return rsa signature failed information and log the exception.
                return resultHelper.infoResp(log, ErrorType.SGN0021);
            }
            return userService.getUserById(param);
        } catch (UserException e) {
            // Return error information and log the exception.
            return resultHelper.infoResp(log, e.getErrorType());
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(log, e, ErrorType.UNKNOWN, e.getMessage());
        }
    }

    /**
     * Show user information by param usr.
     *
     * @param usr       usr
     * @param sign      signature
     * @return          user's result
     */
    @RequestMapping(value = ResourceURL.USERS + "/usr={usr}" + ResourceURL.SIGN, method = RequestMethod.GET)
    public ResultVO showByUsr(@PathVariable String usr, @PathVariable String sign, HttpServletRequest request) {
        try {
            // If usr or sign is empty, return SYS0002, params error.
            if (StringUtils.isBlank(usr)) {
                final String USR = "usr";
                return resultHelper.infoResp(ErrorType.SYS0002, String.format(ControllerConstant.PARAM_BLANK, USR));
            }
            UserParam param = new UserParam(usr);
            // Prepare to validate signature.
            param.setSign(new String(Base64.decodeBase64(sign.getBytes())));
            // Sign verification.
            if (!signHelper.signCheck(PUBLIC_KEY, param, sign)) {
                // Return rsa signature failed information and log the exception.
                return resultHelper.infoResp(log, ErrorType.SGN0021);
            }
            OAuth2AuthenticationDetails details = new OAuth2AuthenticationDetails(request);
            // Return result and message.
            return userService.getUserByUsr(param, details);
        } catch (SignatureException | UserException e) {
            // Return error information and log the exception.
            return resultHelper.infoResp(log, e.getErrorType());
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(log, e, ErrorType.UNKNOWN, e.getMessage());
        }
    }

    /**
     * Update user.
     *
     * @param id        user's id
     * @param param     user's params
     * @return          user's result
     */
    @RequestMapping(value = ResourceURL.USERS + "/{id}" + ResourceURL.SIGN, method = RequestMethod.POST)
    public ResultVO update(@PathVariable String id, @PathVariable String sign, UserParam param) {
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
            // Set user's ID.
            param.setId(Long.valueOf(id));
            // Prepare to validate signature.
            param.setSign(new String(Base64.decodeBase64(sign.getBytes())));
            // Sign verification.
            if (!signHelper.signCheck(PUBLIC_KEY, param, sign)) {
                // Return rsa signature failed information and log the exception.
                return resultHelper.infoResp(log, ErrorType.SGN0021);
            }
            // Update user.
            return userService.update(param);
        } catch (UserException e) {
            // Return error information and log the exception.
            return resultHelper.infoResp(log, e.getErrorType());
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(log, e, ErrorType.UNKNOWN, e.getMessage());
        }
    }

    /**
     * Delete user.
     *
     * @param id        user's id
     * @return          user's result
     */
    @RequestMapping(value = ResourceURL.USERS + "/{id}" + ResourceURL.SIGN, method = RequestMethod.DELETE)
    public ResultVO delete(@PathVariable String id, @PathVariable String sign) {
        try {
            if (StringUtils.isBlank(id)) {
                return resultHelper.infoResp(ErrorType.SYS0002, String.format(ControllerConstant.PARAM_BLANK, ControllerConstant.ID_PARAM));
            }
            UserParam param = new UserParam(Long.valueOf(id));
            // Prepare to validate signature.
            param.setSign(new String(Base64.decodeBase64(sign.getBytes())));
            // Sign verification.
            if (!signHelper.signCheck(PUBLIC_KEY, param, sign)) {
                // Return rsa signature failed information and log the exception.
                return resultHelper.infoResp(log, ErrorType.SGN0021);
            }
            // Delete user.
            userService.delete(param);
            final String USER = "user";
            return new ResultVO(ResultConstant.OK, OperationStatus.SUCCESS, String.format(ControllerConstant.DELETE, USER));
        } catch (UserException e) {
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

    private static final Log log = LogFactory.getLog(UserController.class);

    @Value("${opposite.end1.publicKey}")
    private String PUBLIC_KEY;

    @Autowired
    private ResultHelper resultHelper;

    @Autowired
    private UserService userService;

    @Autowired
    private SignHelper signHelper;

}
