package com.saintdan.framework.service;

import com.saintdan.framework.exception.UserException;
import com.saintdan.framework.param.UserParam;
import com.saintdan.framework.vo.UserVO;
import com.saintdan.framework.vo.UsersVO;

/**
 * User service.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 7/21/15
 * @since JDK1.8
 */
public interface UserService {

    /**
     * Create new user.
     *
     * @param param     user params
     * @return          user VO
     * @throws UserException        USR0031 User already existing exception, usr taken
     */
    UserVO create(UserParam param) throws UserException;

    /**
     * Show all users VO.
     *
     * @return          users VO
     * @throws UserException        USR0013 No user yet
     */
    UsersVO getAllUsers() throws UserException;

    /**
     * Show user VO.
     *
     * @param param     user params
     * @return          user VO
     * @throws UserException        USR0012 Cannot find any user by this id param.
     */
    UserVO getUserById(UserParam param) throws UserException;

    /**
     * Get user VO by param.
     *
     * @param param     user params
     * @return          user VO
     * @throws UserException        USR0011 Cannot find any user by this usr param.
     */
    UserVO getUserByUsr(UserParam param) throws UserException;


    /**
     * Update user.
     *
     * @param param     user params
     * @return          user VO
     * @throws UserException        USR0012 Cannot find any user by this id param.
     */
    UserVO update(UserParam param) throws UserException;

    /**
     * Delete user.
     *
     * @param param     user params
     * @throws UserException        USR0012 Cannot find any user by this id param.
     */
    void delete(UserParam param) throws UserException;

}
