package com.saintdan.framework.service.impl;

import com.saintdan.framework.bo.UserBO;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.exception.UserException;
import com.saintdan.framework.po.User;
import com.saintdan.framework.repo.UserRepository;
import com.saintdan.framework.service.UserService;
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

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Get user po by param
     *
     * @param param     user params
     * @return          user po
     * @throws UserException        User cannot find by usr parameter exception.
     */
    @Override
    public User getUserByUsr(UserBO param) throws UserException {
        User user = userRepository.findByUsr(param.getUsr());
        if (user == null) {
            // Throw user cannot find by usr parameter exception.
            throw new UserException(ErrorType.USR0011);
        }
        return user;
    }
}
