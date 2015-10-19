package com.saintdan.framework.service.impl;

import com.saintdan.framework.component.CustomPasswordEncoder;
import com.saintdan.framework.component.ResultHelper;
import com.saintdan.framework.constant.ControllerConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.exception.UserException;
import com.saintdan.framework.param.UserParam;
import com.saintdan.framework.po.User;
import com.saintdan.framework.repo.UserRepository;
import com.saintdan.framework.service.UserService;
import com.saintdan.framework.vo.UserVO;
import com.saintdan.framework.vo.UsersVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    /**
     * Create new user.
     *
     * @param param     user's params
     * @return          user's VO
     * @throws UserException        USR0031 User already existing exception, usr taken
     */
    @Override
    public UserVO create(UserParam param) throws UserException {
        User user = userRepository.findByUsr(param.getUsr());
        if (user != null) {
            // Throw user already existing exception, username taken.
            throw new UserException(ErrorType.USR0031);
        }
        return userPO2VO(userRepository.save(userParam2PO(param)),
                String.format(ControllerConstant.CREATE, USER));
    }

    /**
     * Show all users' VO.
     *
     * @return          users
     * @throws UserException        USR0011 No user exists.
     */
    @Override
    public UsersVO getAllUsers() throws UserException {
        List<User> users = (List<User>) userRepository.findAll();
        if (users.isEmpty()) {
            // Throw no user exist exception.
            throw new UserException(ErrorType.USR0011);
        }
        return usersPO2VO(users, String.format(ControllerConstant.INDEX, USER));
    }

    /**
     * Show user VO by user's id.
     *
     * @param param     user's params
     * @return          user's VO
     * @throws UserException        USR0012 Cannot find any user by this id param.
     */
    @Override
    public UserVO getUserById(UserParam param) throws UserException {
        User user = userRepository.findOne(param.getId());
        if (user == null) {
            // Throw user cannot find by id parameter exception.
            throw new UserException(ErrorType.USR0012);
        }
        return userPO2VO(user, String.format(ControllerConstant.SHOW, USER));
    }

    /**
     * Get user's VO by usr.
     *
     * @param param     user's params
     * @return          user's VO
     * @throws UserException        USR0012 Cannot find any user by this usr param.
     */
    @Override
    public UserVO getUserByUsr(UserParam param) throws UserException {
        User user = userRepository.findByUsr(param.getUsr());
        if (user == null) {
            // Throw user cannot find by usr parameter exception.
            throw new UserException(ErrorType.USR0013);
        }
        return userPO2VO(user, String.format(ControllerConstant.SHOW, USER));
    }

    /**
     * Update user.
     *
     * @param param     user's params
     * @return          user's VO
     * @throws UserException        USR0012 Cannot find any user by this id param.
     */
    @Override
    public UserVO update(UserParam param) throws UserException {
        User user = userRepository.findOne(param.getId());
        if (user == null) {
            // Throw user cannot find by usr parameter exception.
            throw new UserException(ErrorType.USR0012);
        }
        return userPO2VO(userRepository.save(userParam2PO(param)),
                String.format(ControllerConstant.UPDATE, USER));
    }

    /**
     * Delete user.
     *
     * @param param     user's params
     * @throws UserException        USR0012 Cannot find any user by this id param.
     */
    @Override
    public void delete(UserParam param) throws UserException {
        User user = userRepository.findOne(param.getId());
        if (user == null) {
            // Throw user cannot find by usr parameter exception.
            throw new UserException(ErrorType.USR0012);
        }
        userRepository.delete(user);
    }

    // --------------------------
    // PRIVATE FIELDS AND METHODS
    // --------------------------

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private CustomPasswordEncoder passwordEncoder;

    @Autowired
    private ResultHelper resultHelper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final static String USER = "user";

    /**
     * Transform user's param to PO.
     *
     * @param param     user's param
     * @return          user's PO
     */
    public User userParam2PO(UserParam param) {
        User user = new User();
        BeanUtils.copyProperties(param, user);
        user.setPwd(passwordEncoder.encode(param.getPwd()));
        return user;
    }

    /**
     * Transform user's PO to VO.
     *
     * @param user      user's PO
     * @param msg       return message
     * @return          user's VO
     */
    private UserVO userPO2VO(User user, String msg) {
        UserVO vo = new UserVO();
        BeanUtils.copyProperties(user, vo);
        if (StringUtils.isBlank(msg)) {
            return vo;
        }
        vo.setMessage(msg);
        // Return success result.
        return (UserVO) resultHelper.sucessResp(vo);
    }

    /**
     * Transform users' PO to users VO
     *
     * @param users     users' PO
     * @param msg       return message
     * @return          users' VO
     */
    private UsersVO usersPO2VO(Iterable<User> users, String msg) {
        UsersVO vos = new UsersVO();
        List<UserVO> userVOList = new ArrayList<>();
        for (User user : users) {
            UserVO vo = userPO2VO(user, "");
            userVOList.add(vo);
        }
        vos.setUserVOList(userVOList);
        if (StringUtils.isBlank(msg)) {
            return vos;
        }
        vos.setMessage(msg);
        return (UsersVO) resultHelper.sucessResp(vos);
    }

}
