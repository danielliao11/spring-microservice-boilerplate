package com.saintdan.framework.service;

import com.saintdan.framework.exception.CommonsException;
import com.saintdan.framework.param.UserParam;
import com.saintdan.framework.po.User;
import com.saintdan.framework.vo.ObjectsVO;
import com.saintdan.framework.vo.PageVO;
import com.saintdan.framework.vo.UserVO;
import org.springframework.data.domain.Pageable;

/**
 * User's service.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 7/21/15
 * @since JDK1.8
 */
public interface UserService extends BaseService<User, Long> {

    /**
     * Create new user.
     *
     * @param currentUser   current user
     * @param param         user's params
     * @return              user's VO
     * @throws CommonsException        SYS0111 user already existing, usr taken.
     */
    UserVO create(UserParam param, User currentUser) throws Exception;

    /**
     * Show all users' VO.
     *
     * @return          users
     * @throws CommonsException        SYS0120 No user exists.
     */
    ObjectsVO getAllUsers() throws Exception;

    /**
     * Show users' page VO.
     *
     * @param pageable      page
     * @return              users' page VO
     * @throws CommonsException        SYS0120 No user exists.
     */
    PageVO getPage(Pageable pageable) throws Exception;

    /**
     * Show users by ids.
     *
     * @param ids           users' ids
     * @return              users' PO
     * @throws CommonsException        SYS0120 No user exists.
     */
    Iterable<User> getUsersByIds(Iterable<Long> ids) throws Exception;

    /**
     * Show user VO by user's id.
     *
     * @param param     user's params
     * @return          user's VO
     * @throws CommonsException        SYS0122 Cannot find any user by id param.
     */
    UserVO getUserById(UserParam param) throws Exception;

    /**
     * Get user's VO by usr.
     *
     * @param param     user's params
     * @return          user's VO
     * @throws CommonsException        SYS0122 Cannot find any user by usr param.
     */
    UserVO getUserByUsr(UserParam param) throws Exception;

    /**
     * Update user.
     *
     * @param param     user's params
     * @return          user's VO
     * @throws CommonsException        SYS0122 Cannot find any user by id param.
     */
    UserVO update(UserParam param, User currentUser) throws Exception;

    /**
     * Update user's password
     *
     * @param currentUser   current user
     * @param param         user's param
     * @throws CommonsException        SYS0131 user's pwd update failed.
     */
    void updatePwd(UserParam param, User currentUser) throws Exception;

    /**
     * Delete user.
     *
     * @param currentUser   current user
     * @param param         user's params
     * @throws CommonsException        SYS0122 Cannot find any user by id param.
     */
    void delete(UserParam param, User currentUser) throws Exception;

}
