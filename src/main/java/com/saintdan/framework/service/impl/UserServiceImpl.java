package com.saintdan.framework.service.impl;

import com.saintdan.framework.component.CustomPasswordEncoder;
import com.saintdan.framework.component.ResultHelper;
import com.saintdan.framework.component.Transformer;
import com.saintdan.framework.constant.ControllerConstant;
import com.saintdan.framework.constant.ResourceConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.enums.LogType;
import com.saintdan.framework.enums.ValidFlag;
import com.saintdan.framework.exception.RoleException;
import com.saintdan.framework.exception.UserException;
import com.saintdan.framework.param.LogParam;
import com.saintdan.framework.param.UserParam;
import com.saintdan.framework.po.Role;
import com.saintdan.framework.po.User;
import com.saintdan.framework.repo.UserRepository;
import com.saintdan.framework.service.LogService;
import com.saintdan.framework.service.RoleService;
import com.saintdan.framework.service.UserService;
import com.saintdan.framework.tools.SpringSecurityUtils;
import com.saintdan.framework.vo.ObjectsVO;
import com.saintdan.framework.vo.PageVO;
import com.saintdan.framework.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
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
@Transactional
public class UserServiceImpl implements UserService {

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    /**
     * Create new user.
     *
     * @param currentUser   current user
     * @param param         user's params
     * @return              user's VO
     * @throws UserException        USR0031 User already existing exception, usr taken
     * @throws RoleException        ROL0012 Cannot find any role by this id param.
     */
    @Override
    public UserVO create(UserParam param, User currentUser) throws UserException, RoleException {
        User user = userRepository.findByUsr(param.getUsr());
        if (user != null) {
            // Throw user already existing exception, username taken.
            throw new UserException(ErrorType.USR0031);
        }
        return userPO2VO(userRepository.save(userParam2PO(param, new User(), currentUser)),
                String.format(ControllerConstant.CREATE, USER));
    }

    /**
     * Show all users' VO.
     *
     * @return          users
     * @throws UserException        USR0011 No user exists.
     */
    @Override
    public ObjectsVO getAllUsers() throws UserException {
        List<User> users = (List<User>) userRepository.findAll();
        if (users.isEmpty()) {
            // Throw no user exist exception.
            throw new UserException(ErrorType.USR0011);
        }
        return usersPO2VO(users, String.format(ControllerConstant.INDEX, USER));
    }

    /**
     * Show users' page VO.
     *
     * @param pageable      page
     * @return              users' page VO
     * @throws UserException        USR0011 No user exists.
     */
    @Override
    public PageVO getPage(Pageable pageable) throws UserException {
        Page<User> userPage = userRepository.findAll(pageable);
        if (userPage.getContent().isEmpty()) {
            // Throw no user exist exception.
            throw new UserException(ErrorType.USR0011);
        }
        return transformer.poPage2VO(
                poList2VOList(userPage.getContent()),
                pageable, userPage.getTotalElements(),
                String.format(ControllerConstant.INDEX, USER));
    }

    /**
     * Show users by ids.
     *
     * @param ids           users' ids
     * @return              users' PO
     * @throws UserException        USR0012 Cannot find any user by this id param.
     */
    @Override
    public Iterable<User> getUsersByIds(Iterable<Long> ids) throws UserException {
        return userRepository.findAll(ids);
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
     * @throws RoleException        ROL0012 Cannot find any role by this id param.
     */
    @Override
    public UserVO update(UserParam param, User currentUser) throws UserException, RoleException {
        User user = userRepository.findOne(param.getId());
        if (user == null) {
            // Throw user cannot find by usr parameter exception.
            throw new UserException(ErrorType.USR0012);
        }
        return userPO2VO(userRepository.save(userParam2PO(param, user, currentUser)),
                String.format(ControllerConstant.UPDATE, USER));
    }

    /**
     * Update user's password
     *
     * @param currentUser   current user
     * @param param         user's param
     * @throws UserException        USR0041 Update user's password failed.
     */
    @Override
    public void updatePwd(UserParam param, User currentUser) throws UserException {
        userRepository.updatePwdFor(param.getPwd(), param.getId());
    }

    /**
     * Delete user.
     *
     * @param currentUser   current user
     * @param param         user's params
     * @throws UserException        USR0012 Cannot find any user by this id param.
     */
    @Override
    public void delete(UserParam param, User currentUser) throws UserException {
        User user = userRepository.findOne(param.getId());
        if (user == null) {
            // Throw user cannot find by usr parameter exception.
            throw new UserException(ErrorType.USR0012);
        }
        // Get ip and clientId
        String ip = SpringSecurityUtils.getCurrentUserIp();
        String clientId = SpringSecurityUtils.getCurrentUsername();
        logService.create(new LogParam(ip, LogType.DELETE, clientId, ResourceConstant.USER), currentUser);
        userRepository.updateValidFlagFor(ValidFlag.INVALID, user.getId());
    }

    // --------------------------
    // PRIVATE FIELDS AND METHODS
    // --------------------------

    @Autowired
    private RoleService roleService;

    @Autowired
    private LogService logService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomPasswordEncoder passwordEncoder;

    @Autowired
    private ResultHelper resultHelper;

    @Autowired
    private Transformer transformer;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final static String USER = "user";

    /**
     * Transform user's param to PO.
     *
     * @param param         user's param
     * @param user          user
     * @param currentUser   currentUser
     * @return              user's PO
     */
    private User userParam2PO(UserParam param, User user, User currentUser) throws RoleException {
        // Get ip and clientId
        String ip = SpringSecurityUtils.getCurrentUserIp();
        String clientId = SpringSecurityUtils.getCurrentUsername();
        // Init createdBy, lastModifiedBy
        Long createdBy, lastModifiedBy;
        // Init createdDate
        Date createdDate = new Date();
        if (user.getId() == null) {
            createdBy = currentUser.getId();
            lastModifiedBy = createdBy;
            logService.create(new LogParam(ip, LogType.CREATE, clientId, ResourceConstant.USER), currentUser);
        } else {
            createdBy = user.getCreatedBy();
            createdDate = user.getCreatedDate();
            lastModifiedBy = currentUser.getId();
            logService.create(new LogParam(ip, LogType.UPDATE, clientId, ResourceConstant.USER), currentUser);
        }
        BeanUtils.copyProperties(param, user);
        user.setCreatedBy(createdBy);
        user.setCreatedDate(createdDate);
        user.setLastModifiedBy(lastModifiedBy);
        if (!StringUtils.isBlank(param.getRoleIds())) {
            Iterable<Role> roles = roleService.getRolesByIds(transformer.idsStr2Iterable(param.getRoleIds()));
            user.setRoles(transformer.iterable2Set(roles));
        }
        if (!StringUtils.isBlank(param.getPwd())) {
            user.setPwd(passwordEncoder.encode(param.getPwd()));
        }
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
    private ObjectsVO usersPO2VO(Iterable<User> users, String msg) {
        List objList = poList2VOList(users);
        ObjectsVO vos = transformer.voList2ObjectsVO(objList, msg);
        return (ObjectsVO) resultHelper.sucessResp(vos);
    }

    /**
     * Transform user's PO list to VO list.
     *
     * @param users     user's PO list
     * @return          user's VO list
     */
    private List<UserVO> poList2VOList(Iterable<User> users) {
        List<UserVO> userVOList = new ArrayList<>();
        for (User user : users) {
            UserVO vo = userPO2VO(user, "");
            userVOList.add(vo);
        }
        return userVOList;
    }

}
