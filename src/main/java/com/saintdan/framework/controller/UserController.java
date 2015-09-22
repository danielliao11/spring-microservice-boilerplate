package com.saintdan.framework.controller;

import com.saintdan.framework.bo.UserBO;
import com.saintdan.framework.component.ResultHelper;
import com.saintdan.framework.component.SignHelper;
import com.saintdan.framework.constant.ResourceURL;
import com.saintdan.framework.constant.ResultConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.enums.OperationStatus;
import com.saintdan.framework.exception.SignatureException;
import com.saintdan.framework.exception.UserException;
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
     * Show user information with param usr.
     *
     * @param usr       usr
     * @param sign      signature
     * @return          user result
     */
    @RequestMapping(value = ResourceURL.USERS +"/usr={usr}&sign={sign}", method = RequestMethod.GET)
    public ResultVO show(@PathVariable String usr, @PathVariable String sign) {
        // If usr or sign is empty, return SYS0002, params error.
        if (StringUtils.isEmpty(usr)) {
            return resultHelper.infoResp(ErrorType.SYS0002, "Usr cannot be null.");
        }
        // Prepare to validate signature.
        UserBO userBO = new UserBO(usr);
        userBO.setSign(new String(Base64.decodeBase64(sign.getBytes())));
        try {
            // Sign verification.
            if (ResultConstant.OK.equals(signHelper.signCheck(PUBLIC_KEY, userBO, sign).getCode())) {
                return signHelper.signCheck(PUBLIC_KEY, userBO, sign);
            }
            User user = userService.getUserWithUsr(new UserBO(usr));
            if (user == null) {
                return resultHelper.infoResp(ErrorType.USR0011);
            }
            return userPO2VO(user);
        } catch (SignatureException | UserException e) {
            // Return error information.
            return resultHelper.errorResp(log, e, e.getErrorType());
        } catch (Exception e) {
            // Return unknown error.
            return resultHelper.errorResp(log, e, ErrorType.UNKNOWN);
        }
    }

    /**
     * Transform the user po to vo.
     *
     * @param user      user po
     * @return          user vo
     */
    private UserVO userPO2VO(User user) {
        final String msg = "Get user data successfully.";
        UserVO vo = new UserVO();
        vo.setName(user.getName());
        vo.setUsername(user.getUsr());
        vo.setCode(ResultConstant.OK);
        vo.setOperationStatus(OperationStatus.SUCCESS);
        vo.setMessage(msg);
        return vo;
    }

}
