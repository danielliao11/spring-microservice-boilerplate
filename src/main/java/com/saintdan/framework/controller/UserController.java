package com.saintdan.framework.controller;

import com.saintdan.framework.component.ResultHelper;
import com.saintdan.framework.component.SignHelper;
import com.saintdan.framework.constant.ResourceURL;
import com.saintdan.framework.constant.ResultConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.enums.OperationStatus;
import com.saintdan.framework.exception.SignatureException;
import com.saintdan.framework.exception.UserException;
import com.saintdan.framework.param.UserParam;
import com.saintdan.framework.po.User;
import com.saintdan.framework.repo.UserRepository;
import com.saintdan.framework.service.UserService;
import com.saintdan.framework.vo.ResultVO;
import com.saintdan.framework.vo.UserVO;
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
 * User controller.
 * see {@link UserRepository}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/25/15
 * @since JDK1.8
 */
@PropertySource("classpath:api.properties")
@RestController
@RequestMapping(ResourceURL.RESOURCES)
public class UserController {

    private static final Log log = LogFactory.getLog(UserController.class);

    @Value("${opposite.end1.publicKey}")
    private String PUBLIC_KEY;

    @Autowired
    private ResultHelper resultHelper;

    @Autowired
    private UserService userService;

    @Autowired
    private SignHelper signHelper;

    /**
     * Show user information by param usr.
     *
     * @param usr       usr
     * @param sign      signature
     * @return          user result
     */
    @RequestMapping(value = ResourceURL.USERS +"/usr={usr}&sign={sign}", method = RequestMethod.GET)
    public ResultVO showByUsr(@PathVariable String usr, @PathVariable String sign) {
        // If usr or sign is empty, return SYS0002, params error.
        if (StringUtils.isEmpty(usr)) {
            return resultHelper.infoResp(ErrorType.SYS0002, "Usr cannot be null.");
        }
        // Prepare to validate signature.
        UserParam param = new UserParam();
        param.setUsr(usr);
        param.setSign(new String(Base64.decodeBase64(sign.getBytes())));
        try {
            // Sign verification.
            if (!ResultConstant.OK.equals(signHelper.signCheck(PUBLIC_KEY, param, sign).getCode())) {
                return signHelper.signCheck(PUBLIC_KEY, param, sign);
            }
            // Return result and message.
            return userPO2VO(userService.getUserByUsr(param), "Get user data successfully.");
        } catch (SignatureException | UserException e) {
            // Return error information.
            return resultHelper.errorResp(log, e, e.getErrorType());
        } catch (Exception e) {
            // Return unknown error.
            return resultHelper.errorResp(log, e, ErrorType.UNKNOWN);
        }
    }

    /**
     * Create new user.
     *
     * @param param     user param
     * @return          user result
     */
    @RequestMapping(value = ResourceURL.USERS , method = RequestMethod.POST)
    public ResultVO create(UserParam param) {
        try {
            // Get incorrect params.
            String validateContent = param.getIncorrectParams();
            if (!StringUtils.isBlank(validateContent)) {
                // If validate failed, return error message.
                return new ResultVO(ErrorType.SYS0002.value(), OperationStatus.FAILURE,
                        String.format("Params: %s could not be null.", validateContent));
            }
            return userPO2VO(userService.create(param), "Create user successfully.");
        } catch (UserException e) {
            // Return error information.
            return resultHelper.errorResp(log, e, e.getErrorType());
        } catch (Exception e) {
            // Return unknown error.
            return resultHelper.errorResp(log, e, ErrorType.UNKNOWN);
        }
    }

    /**
     * Transform user PO to VO.
     *
     * @param user      user PO
     * @param msg       return message
     * @return          user VO
     */
    private UserVO userPO2VO(User user, String msg) {
        UserVO vo = new UserVO();
        vo.setUserId(user.getId());
        vo.setName(user.getName());
        vo.setUsername(user.getUsr());
        vo.setMessage(msg);
        // Return success result.
        return (UserVO) resultHelper.sucessResp(vo);
    }

}
