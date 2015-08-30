package com.saintdan.framework.controller;

import com.saintdan.framework.bo.UserParams;
import com.saintdan.framework.constant.ResourceURL;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.enums.OperationStatus;
import com.saintdan.framework.exception.SignatureException;
import com.saintdan.framework.exception.UserException;
import com.saintdan.framework.po.User;
import com.saintdan.framework.repo.UserRepository;
import com.saintdan.framework.service.UserService;
import com.saintdan.framework.tools.LogUtils;
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

    @Autowired
    UserService userService;

    @Value("${opposite.end1.publicKey}")
    private String PUBLIC_KEY;

    private static final Log log = LogFactory.getLog(UserController.class);

    @RequestMapping(value = ResourceURL.USERS +"/usr={usr}&sign={sign}", method = RequestMethod.GET)
    public ResultVO getUserByUsr(@PathVariable String usr, @PathVariable String sign) {
        // If usr or sign is empty, return SYS0002.
        if (StringUtils.isEmpty(usr) || StringUtils.isEmpty(sign)) {
            return logAndReturn(ErrorType.SYS0002);
        }
        // Prepare to validate signature.
        UserParams userParams = new UserParams(usr);
        userParams.setSign(new String(Base64.decodeBase64(sign.getBytes())));
        try {
            // If validate the signature failed, return SGN0020.
            if(!userParams.isSignValid(PUBLIC_KEY)) {
                return logAndReturn(ErrorType.SGN0020);
            }
            User user = userService.getUserByUsr(new UserParams(usr));
            UserVO vo = new UserVO();
            if (user != null) {
                vo = userPO2VO(user);
            }
            return vo;
        } catch (SignatureException e) {
            // Return sign error.
            return logAndReturn(ErrorType.SGN0021, e);
        } catch (UserException e) {
            // Return user find error.
            return logAndReturn(ErrorType.USR0011, e);
        }
    }

    /**
     * Transform the user po to vo.
     *
     * @param user      user po
     * @return          user vo
     */
    private UserVO userPO2VO(User user) {
        UserVO vo = new UserVO();
        vo.setName(user.getName());
        vo.setUsername(user.getUsr());
        return vo;
    }

    /**
     * Log debug message and return result.
     *
     * @param errorType     error type
     * @return              result vo
     */
    private ResultVO logAndReturn(ErrorType errorType) {
        return logAndReturn(errorType, null);
    }

    /**
     * Log debug message and e, and return result.
     *
     * @param errorType     error type
     * @param e             e
     * @return              result vo
     */
    private ResultVO logAndReturn(ErrorType errorType, Throwable e) {
        LogUtils.traceError(log, e ,errorType.value());
        return new ResultVO(
                errorType.name(),
                OperationStatus.FAILURE,
                errorType.value()
        );
    }

}
