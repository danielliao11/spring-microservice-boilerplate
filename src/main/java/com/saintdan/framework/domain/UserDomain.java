package com.saintdan.framework.domain;

import com.saintdan.framework.component.CustomPasswordEncoder;
import com.saintdan.framework.component.Transformer;
import com.saintdan.framework.constant.CommonsConstant;
import com.saintdan.framework.constant.ControllerConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.enums.LogType;
import com.saintdan.framework.enums.ValidFlag;
import com.saintdan.framework.exception.CommonsException;
import com.saintdan.framework.param.UserParam;
import com.saintdan.framework.po.Role;
import com.saintdan.framework.po.User;
import com.saintdan.framework.repo.UserRepository;
import com.saintdan.framework.tools.ErrorMsgHelper;
import com.saintdan.framework.vo.ObjectsVO;
import com.saintdan.framework.vo.PageVO;
import com.saintdan.framework.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Domain of {@link User}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 7/21/15
 * @since JDK1.8
 */
@Service
@Transactional
public class UserDomain extends BaseDomain<User, Long> {

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    /**
     * Create new user.
     *
     * @param currentUser   current user
     * @param param         user's params
     * @return              user's VO
     * @throws CommonsException        SYS0111 user already existing, usr taken.
     */
    public UserVO create(UserParam param, User currentUser) throws Exception {
        User user = userRepository.findByUsr(param.getUsr());
        if (user != null) {
            // Throw user already existing exception, usr taken.
            throw new CommonsException(ErrorType.SYS0111,
                    ErrorMsgHelper.getReturnMsg(ErrorType.SYS0111, getClassT().getSimpleName(), getClassT().getSimpleName(), USR));
        }
        return super.createByPO(UserVO.class, userParam2PO(param, new User(), currentUser), currentUser);
    }

    /**
     * Show all users' VO.
     *
     * @return          users
     * @throws CommonsException        SYS0120 No user exists.
     */
    public ObjectsVO getAllUsers() throws Exception {
        Iterable users = userRepository.findAll();
        if (((List) users).isEmpty()) {
            // Throw no user exists exception.
            throw new CommonsException(ErrorType.SYS0120,
                    ErrorMsgHelper.getReturnMsg(ErrorType.SYS0120, getClassT().getSimpleName(), getClassT().getSimpleName()));
        }
        return transformer.pos2VO(ObjectsVO.class, users, String.format(ControllerConstant.INDEX, getClassT()));
    }

    /**
     * Show users' page VO.
     *
     * @param pageable      page
     * @return              users' page VO
     * @throws CommonsException        SYS0120 No user exists.
     */
    public PageVO getPage(Pageable pageable) throws Exception {
        Page<User> userPage = userRepository.findAll(pageable);
        if (!userPage.hasContent()) {
            // Throw no user exists exception.
            throw new CommonsException(ErrorType.SYS0120,
                    ErrorMsgHelper.getReturnMsg(ErrorType.SYS0120, getClassT().getSimpleName(), getClassT().getSimpleName()));
        }
        return transformer.poPage2VO(transformer.poList2VOList(UserVO.class, userPage.getContent()), pageable, userPage.getTotalElements(),
                String.format(ControllerConstant.INDEX, getClassT()));
    }

    /**
     * Show users by ids.
     *
     * @param ids           users' ids
     * @return              users' PO
     * @throws CommonsException        SYS0120 No user exists.
     */
    public Iterable<User> getUsersByIds(Iterable<Long> ids) throws Exception {
        return userRepository.findAll(ids);
    }

    /**
     * Show user VO by user's id.
     *
     * @param param     user's params
     * @return          user's VO
     * @throws CommonsException        SYS0122 Cannot find any user by id param.
     */
    public UserVO getUserById(UserParam param) throws Exception {
        User user = userRepository.findOne(param.getId());
        if (user == null) {
            // Throw user cannot find by id parameter exception.
            throw new CommonsException(ErrorType.SYS0122,
                    ErrorMsgHelper.getReturnMsg(ErrorType.SYS0122, getClassT().getSimpleName(), getClassT().getSimpleName(), CommonsConstant.ID));
        }
        return transformer.po2VO(UserVO.class, user, String.format(ControllerConstant.SHOW, getClassT()));
    }

    /**
     * Get user's VO by usr.
     *
     * @param param     user's params
     * @return          user's VO
     * @throws CommonsException        SYS0122 Cannot find any user by usr param.
     */
    public UserVO getUserByUsr(UserParam param) throws Exception {
        User user = userRepository.findByUsr(param.getUsr());
        if (user == null) {
            // Throw user cannot find by usr parameter exception.
            throw new CommonsException(ErrorType.SYS0122,
                    ErrorMsgHelper.getReturnMsg(ErrorType.SYS0122, getClassT().getSimpleName(), getClassT().getSimpleName(), USR));
        }
        return transformer.po2VO(UserVO.class, user, String.format(ControllerConstant.SHOW, getClassT()));
    }

    /**
     * Update user.
     *
     * @param param     user's params
     * @return          user's VO
     * @throws CommonsException        SYS0122 Cannot find any user by id param.
     */
    public UserVO update(UserParam param, User currentUser) throws Exception {
        User user = userRepository.findByUsr(param.getUsr());
        if (user == null) {
            // Throw cannot find any user by this id param.
            throw new CommonsException(ErrorType.SYS0122,
                    ErrorMsgHelper.getReturnMsg(ErrorType.SYS0122, getClassT().getSimpleName(), getClassT().getSimpleName(), CommonsConstant.ID));
        }
        return super.updateByPO(UserVO.class, userParam2PO(param, new User(), currentUser), currentUser);
    }

    /**
     * Update user's password
     *
     * @param currentUser   current user
     * @param param         user's param
     * @throws CommonsException        SYS0131 user's pwd update failed.
     */
    public void updatePwd(UserParam param, User currentUser) throws Exception {
        User user = userRepository.findByUsr(param.getUsr());
        if (user == null) {
            // Throw cannot find any user by this id param.
            throw new CommonsException(ErrorType.SYS0122,
                    ErrorMsgHelper.getReturnMsg(ErrorType.SYS0122, getClassT().getSimpleName(), getClassT().getSimpleName(), CommonsConstant.ID));
        }
        userRepository.updatePwdFor(param.getPwd(), param.getId());
    }

    /**
     * Delete user.
     *
     * @param currentUser   current user
     * @param param         user's params
     * @throws CommonsException        SYS0122 Cannot find any user by id param.
     */
    public void delete(UserParam param, User currentUser) throws Exception {
        User user = userRepository.findOne(param.getId());
        if (user == null) {
            // Throw cannot find any user by this id param.
            throw new CommonsException(ErrorType.SYS0122,
                    ErrorMsgHelper.getReturnMsg(ErrorType.SYS0122, getClassT().getSimpleName(), getClassT().getSimpleName(), CommonsConstant.ID));
        }
        // Log delete operation.
        logHelper.logUsersOperations(LogType.DELETE, getClassT().getSimpleName(), currentUser);
        // Change valid flag to invalid.
        userRepository.updateValidFlagFor(ValidFlag.INVALID, user.getId());
    }

    // --------------------------
    // PRIVATE FIELDS AND METHODS
    // --------------------------

    @Autowired
    private RoleDomain roleService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomPasswordEncoder passwordEncoder;

    @Autowired
    private Transformer transformer;

    @Autowired
    public UserDomain(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final static String USR = "usr";

    /**
     * Transform user's param to PO.
     *
     * @param param         user's param
     * @param user          user
     * @param currentUser   currentUser
     * @return              user's PO
     */
    private User userParam2PO(UserParam param, User user, User currentUser) throws Exception {
        transformer.param2PO(getClassT(), param, user, currentUser);
        if (!StringUtils.isBlank(param.getRoleIds())) {
            Iterable<Role> roles = roleService.getRolesByIds(transformer.idsStr2Iterable(param.getRoleIds()));
            user.setRoles(transformer.iterable2Set(roles));
        }
        if (!StringUtils.isBlank(param.getPwd())) {
            user.setPwd(passwordEncoder.encode(param.getPwd()));
        }
        return user;
    }

}
