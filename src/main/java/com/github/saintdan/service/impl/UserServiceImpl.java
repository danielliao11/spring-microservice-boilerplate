package com.github.saintdan.service.impl;

import com.github.saintdan.bo.UserParam;
import com.github.saintdan.enums.ErrorType;
import com.github.saintdan.exception.UserException;
import com.github.saintdan.po.User;
import com.github.saintdan.repo.UserRepository;
import com.github.saintdan.service.UserService;
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

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserByUsr(UserParam param) throws UserException {
        User user = null;
        try {
            user = userRepository.findByUsr(param.getUsr());
        } catch (Exception e) {
            log.debug("Can not find User by the usr param.", e);
            throw new UserException(ErrorType.USR0001);
        }
        return user;
    }
}
