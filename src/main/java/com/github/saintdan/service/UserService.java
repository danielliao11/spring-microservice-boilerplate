package com.github.saintdan.service;

import com.github.saintdan.bo.UserParams;
import com.github.saintdan.exception.UserException;
import com.github.saintdan.po.User;

/**
 * User service.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 7/21/15
 * @since JDK1.8
 */
public interface UserService {

    User getUserByUsr(UserParams param) throws UserException;
}
