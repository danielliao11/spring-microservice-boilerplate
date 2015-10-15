package com.saintdan.framework.service.impl;

import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.exception.UserException;
import com.saintdan.framework.param.UserParam;
import com.saintdan.framework.po.User;
import com.saintdan.framework.repo.UserRepository;
import com.saintdan.framework.service.UserService;
import com.saintdan.framework.tools.CustomPasswordEncoder;
import org.springframework.beans.BeanUtils;
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
    private CustomPasswordEncoder passwordEncoder;

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
    public User getUserByUsr(UserParam param) throws UserException {
        User user = userRepository.findByUsr(param.getUsr());
        if (user == null) {
            // Throw user cannot find by usr parameter exception.
            throw new UserException(ErrorType.USR0011);
        }
        return user;
    }

    /**
     * Create new user.
     *
     * @param param     user params
     * @return          user PO
     * @throws UserException        User already existing exception, username taken
     */
    @Override
    public User create(UserParam param) throws UserException {
        User user = getUserByUsr(param);
        if (user != null) {
            // Throw user already existing exception, username taken.
            throw new UserException();
        }
        return userRepository.save(userParam2PO(param));
    }

    /**
     * Transform user param to PO.
     *
     * @param param     user param
     * @return          user PO
     */
    private User userParam2PO(UserParam param) {
        User user = new User();
        BeanUtils.copyProperties(param, user);
        user.setPwd(passwordEncoder.encode(param.getPwd()));
        return user;
    }
}
