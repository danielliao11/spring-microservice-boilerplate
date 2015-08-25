package com.saintdan.framework.service.impl;

import com.saintdan.framework.bo.UserParams;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.exception.UserException;
import com.saintdan.framework.po.User;
import com.saintdan.framework.repo.UserRepository;
import com.saintdan.framework.service.UserService;
import com.saintdan.framework.tools.LogUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implements the
 * {@link UserService}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 7/21/15
 * @since JDK1.8
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Log log = LogFactory.getLog(UserServiceImpl.class);

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserByUsr(UserParams param) throws UserException {
        User user;
        try {
            user = userRepository.findByUsr(param.getUsr());
        } catch (Exception e) {
            LogUtils.traceError(log, e, "Can not find User by the usr param.");
            throw new UserException(ErrorType.USR0001);
        }
        return user;
    }
}
