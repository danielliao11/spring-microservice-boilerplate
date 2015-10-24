package com.saintdan.framework.service;

import com.saintdan.framework.exception.RoleException;
import com.saintdan.framework.exception.UserException;
import com.saintdan.framework.param.UserParam;
import com.saintdan.framework.po.User;
import com.saintdan.framework.vo.UserVO;
import com.saintdan.framework.vo.UsersVO;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

/**
 * User's service.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 7/21/15
 * @since JDK1.8
 */
public interface UserService {

    /**
     * Create new user.
     *
     * @param param     user's params
     * @return          user's VO
     * @throws UserException        USR0031 User already existing exception, usr taken
     * @throws RoleException        ROL0012 Cannot find any role by this id param.
     */
    UserVO create(UserParam param) throws UserException, RoleException;

    /**
     * Show all users' VO.
     *
     * @return          users' VO
     * @throws UserException        USR0011 No user yet
     */
    UsersVO getAllUsers() throws UserException;

    /**
     * Show users by ids.
     *
     * @param ids           users' ids
     * @return              users' PO
     * @throws UserException        USR0012 Cannot find any user by this id param.
     */
    Iterable<User> getUsersByIds(Iterable<Long> ids) throws UserException;

    /**
     * Show user's VO.
     *
     * @param param     user's params
     * @return          user's VO
     * @throws UserException        USR0012 Cannot find any user by this id param.
     */
    UserVO getUserById(UserParam param) throws UserException;

    /**
     * Get user's VO by param.
     *
     * @param param     user's params
     * @return          user's VO
     * @throws UserException        USR0013 Cannot find any user by this usr param.
     */
    UserVO getUserByUsr(UserParam param, OAuth2AuthenticationDetails details) throws UserException;


    /**
     * Update user.
     *
     * @param param     user's params
     * @return          user's VO
     * @throws UserException        USR0012 Cannot find any user by this id param.
     * @throws RoleException        ROL0012 Cannot find any role by this id param.
     */
    UserVO update(UserParam param) throws UserException, RoleException;

    /**
     * Update user's password
     *
     * @param param     user's param
     * @throws UserException        USR0041 Update user's password failed.
     */
    void updatePwd(UserParam param) throws UserException;

    /**
     * Delete user.
     *
     * @param param     user's params
     * @throws UserException        USR0012 Cannot find any user by this id param.
     */
    void delete(UserParam param) throws UserException;

}
