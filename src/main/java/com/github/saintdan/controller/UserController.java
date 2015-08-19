package com.github.saintdan.controller;

import com.github.saintdan.bo.UserParams;
import com.github.saintdan.constant.Resource;
import com.github.saintdan.enums.ErrorType;
import com.github.saintdan.enums.OperationStatus;
import com.github.saintdan.exception.SignException;
import com.github.saintdan.exception.UserException;
import com.github.saintdan.po.User;
import com.github.saintdan.repo.UserRepository;
import com.github.saintdan.service.UserService;
import com.github.saintdan.vo.ResultVO;
import com.github.saintdan.vo.UserVO;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping(Resource.RESOURCES)
public class UserController {

    @Autowired
    UserService userService;

    @Value("${opposite.end1.publicKey}")
    private String PUBLIC_KEY;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = Resource.USERS +"/usr={usr}&sign={sign}", method = RequestMethod.GET)
    public ResultVO getUserByUsr(@PathVariable String usr, @PathVariable String sign) {
        if (usr == null || sign == null) {
            return new ResultVO(ErrorType.SYS0002.name(), OperationStatus.FAILURE, ErrorType.SYS0002.value());
        }
        // Prepare to validate signature.
        UserParams userParams = new UserParams(usr);
        userParams.setSign(new String(Base64.decodeBase64(sign.getBytes())));
        try {
            // If validate the signature failed, return SGN0020.
            if(!userParams.isSignValid(PUBLIC_KEY)) {
                return new ResultVO(ErrorType.SGN0020.name(), OperationStatus.FAILURE, ErrorType.SGN0020.value());
            }
            User user = userService.getUserByUsr(new UserParams(usr));
            UserVO vo = new UserVO();
            if (user != null) {
                vo = userPO2VO(user);
            }
            return vo;
        } catch (SignException e) {
            // Return sign error.
            log.debug("RSA validate signature failed.", e);
            return new ResultVO(ErrorType.SGN0001.name(), OperationStatus.FAILURE, ErrorType.SGN0001.value());
        } catch (UserException e) {
            // Return user find error.
            log.debug("Get user by usr failed.", e);
            return new ResultVO(ErrorType.USR0001.name(), OperationStatus.FAILURE, ErrorType.USR0001.value());
        }
    }

    /**
     * Transform the user po to vo.
     *
     * @param user
     *                  user po
     * @return
     *                  user vo
     */
    private UserVO userPO2VO(User user) {
        UserVO vo = new UserVO();
        vo.setName(user.getName());
        vo.setUsername(user.getUsr());
        return vo;
    }

}
