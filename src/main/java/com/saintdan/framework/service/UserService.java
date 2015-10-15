package com.saintdan.framework.service;

import com.saintdan.framework.exception.UserException;
import com.saintdan.framework.param.UserParam;
import com.saintdan.framework.po.User;

/**
 * User service.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 7/21/15
 * @since JDK1.8
 */
public interface UserService {

    /**
     * Get user PO by param.
     *
     * @param param     user params
     * @return          user PO
     * @throws UserException        User cannot find by usr parameter exception
     */
    User getUserByUsr(UserParam param) throws UserException;

    /**
     * Create new user.
     *
     * @param param     user params
     * @return          user PO
     * @throws UserException        User already existing exception, username taken
     */
    User create(UserParam param) throws UserException;
}
