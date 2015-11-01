package com.saintdan.framework.service.impl;

import com.saintdan.framework.component.ResultHelper;
import com.saintdan.framework.component.Transformer;
import com.saintdan.framework.constant.ControllerConstant;
import com.saintdan.framework.constant.ResourceConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.enums.LogType;
import com.saintdan.framework.enums.ValidFlag;
import com.saintdan.framework.exception.GroupException;
import com.saintdan.framework.exception.RoleException;
import com.saintdan.framework.exception.UserException;
import com.saintdan.framework.param.LogParam;
import com.saintdan.framework.param.RoleParam;
import com.saintdan.framework.po.Group;
import com.saintdan.framework.po.Role;
import com.saintdan.framework.po.User;
import com.saintdan.framework.repo.RoleRepository;
import com.saintdan.framework.service.GroupService;
import com.saintdan.framework.service.LogService;
import com.saintdan.framework.service.RoleService;
import com.saintdan.framework.service.UserService;
import com.saintdan.framework.tools.SpringSecurityUtils;
import com.saintdan.framework.vo.ObjectsVO;
import com.saintdan.framework.vo.PageVO;
import com.saintdan.framework.vo.RoleVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Implements the
 * {@link RoleService}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/17/15
 * @since JDK1.8
 */
@Service
public class RoleServiceImpl implements RoleService {

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    /**
     * Create new role.
     *
     * @param currentUser   current user
     * @param param         role's params
     * @return              role's VO
     * @throws RoleException        ROL0031 Role already existing, name taken.
     * @throws UserException        USR0012 Cannot find any user by this id param.
     * @throws GroupException       GRP0012 Cannot find any group by this id param.
     */
    @Override
    public RoleVO create(RoleParam param, User currentUser) throws RoleException, UserException, GroupException {
        Role role = roleRepository.findByName(param.getName());
        if (role != null) {
            // Throw role already existing, name taken.
            throw new RoleException(ErrorType.ROL0031);
        }
        return rolePO2VO(roleRepository.save(roleParam2PO(param, new Role(), currentUser)),
                String.format(ControllerConstant.CREATE, ROLE));
    }

    /**
     * Show all roles' VO.
     *
     * @return              roles' VO
     * @throws RoleException        ROL0011 No role exist.
     */
    @Override
    public ObjectsVO getAllRoles() throws RoleException {
        List<Role> roles = (List<Role>) roleRepository.findAll();
        if (roles == null) {
            // Throw No role exist exception.
            throw new RoleException(ErrorType.ROL0011);
        }
        return rolesPO2VO(roles, String.format(ControllerConstant.INDEX, ROLE));
    }

    /**
     * Show roles' page VO.
     *
     * @param pageable      page
     * @return              roles' page VO
     * @throws RoleException        ROL0011 No role exists.
     */
    @Override
    public PageVO getPage(Pageable pageable) throws RoleException {
        Page<Role> rolePage = roleRepository.findAll(pageable);
        if (rolePage.getContent().isEmpty()) {
            // Throw no user exist exception.
            throw new RoleException(ErrorType.ROL0011);
        }
        return transformer.poPage2VO(
                poList2VOList(rolePage.getContent()),
                pageable, rolePage.getTotalElements(),
                String.format(ControllerConstant.INDEX, ROLE));
    }

    /**
     * Show roles by ids.
     *
     * @param ids           roles' ids
     * @return              roles' PO
     * @throws RoleException        ROL0012 Cannot find any role by this id param.
     */
    @Override
    public Iterable<Role> getRolesByIds(Iterable<Long> ids) throws RoleException {
        return roleRepository.findAll(ids);
    }

    /**
     * Show role's VO by role's id.
     *
     * @param param         role's params
     * @return              role's VO
     * @throws RoleException        ROL0012 Cannot find any role by this id param.
     */
    @Override
    public RoleVO getRoleById(RoleParam param) throws RoleException {
        Role role = roleRepository.findOne(param.getId());
        if (role == null) {
            // Throw role cannot find by id parameter exception.
            throw new RoleException(ErrorType.ROL0012);
        }
        return rolePO2VO(role, String.format(ControllerConstant.SHOW, ROLE));
    }

    /**
     * Show role's VO by role's name.
     *
     * @param param         role's params
     * @return              role's VO
     * @throws RoleException        ROL0013 Cannot find any role by this name param.
     */
    @Override
    public RoleVO getRoleByName(RoleParam param) throws RoleException {
        Role role = roleRepository.findByName(param.getName());
        if (role == null) {
            // Throw role cannot find by name parameter exception.
            throw new RoleException(ErrorType.ROL0013);
        }
        return rolePO2VO(role, String.format(ControllerConstant.SHOW, ROLE));
    }

    /**
     * Update role.
     *
     * @param currentUser   current user
     * @param param         role's params
     * @return              role's VO
     * @throws RoleException        ROL0012 Cannot find any role by this id param.
     * @throws UserException        USR0012 Cannot find any user by this id param.
     * @throws GroupException       GRP0012 Cannot find any group by this id param.
     */
    @Override
    public RoleVO update(RoleParam param, User currentUser) throws RoleException, UserException, GroupException {
        Role role = roleRepository.findOne(param.getId());
        if (role == null) {
            // Throw role cannot find by id parameter exception.
            throw new RoleException(ErrorType.ROL0012);
        }
        return rolePO2VO(roleRepository.save(roleParam2PO(param, role, currentUser)),
                String.format(ControllerConstant.UPDATE, ROLE));
    }

    /**
     * Delete role.
     *
     * @param currentUser   current user
     * @param param         role's params.
     * @throws RoleException        ROL0012 Cannot find any role by this id param.
     */
    @Override
    public void delete(RoleParam param, User currentUser) throws RoleException {
        Role role = roleRepository.findOne(param.getId());
        if (role == null) {
            // Throw role cannot find by id parameter exception.
            throw new RoleException(ErrorType.ROL0012);
        }
        // Get ip and clientId
        String ip = SpringSecurityUtils.getCurrentUserIp();
        String clientId = SpringSecurityUtils.getCurrentUsername();
        logService.create(new LogParam(ip, LogType.DELETE, clientId, ResourceConstant.ROLE), currentUser);
        roleRepository.updateValidFlagFor(ValidFlag.INVALID, role.getId());
    }

    // --------------------------
    // PRIVATE FIELDS AND METHODS
    // --------------------------

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private LogService logService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ResultHelper resultHelper;

    @Autowired
    private Transformer transformer;

    private final static String ROLE = "role";

    /**
     * Transform role's param to PO.
     *
     * @param param         role's param
     * @param role          role
     * @param currentUser   currentUser
     * @return              role's PO
     * @throws UserException            USR0012 Cannot find any user by this id param.
     * @throws GroupException           GRP0012 Cannot find any group by this id param.
     */
    private Role roleParam2PO(RoleParam param, Role role, User currentUser) throws UserException, GroupException {
        // Get ip and clientId
        String ip = SpringSecurityUtils.getCurrentUserIp();
        String clientId = SpringSecurityUtils.getCurrentUsername();
        // Init createdBy, lastModifiedBy
        Long createdBy, lastModifiedBy;
        // Init createdDate
        Date createdDate = new Date();
        if (role == null) {
            createdBy = currentUser.getId();
            lastModifiedBy = createdBy;
            logService.create(new LogParam(ip, LogType.CREATE, clientId, ResourceConstant.ROLE), currentUser);
        } else {
            createdBy = role.getCreatedBy();
            createdDate = role.getCreatedDate();
            lastModifiedBy = currentUser.getId();
            logService.create(new LogParam(ip, LogType.UPDATE, clientId, ResourceConstant.ROLE), currentUser);
        }
        BeanUtils.copyProperties(param, role);
        role.setCreatedBy(createdBy);
        role.setCreatedDate(createdDate);
        role.setLastModifiedBy(lastModifiedBy);
        if (!StringUtils.isBlank(param.getUserIds())) {
            Iterable<User> users = userService.getUsersByIds(transformer.idsStr2Iterable(param.getUserIds()));
            role.setUsers(transformer.iterable2Set(users));
        }
        if (!StringUtils.isBlank(param.getGroupIds())) {
            Iterable<Group> groups = groupService.getGroupsByIds(transformer.idsStr2Iterable(param.getGroupIds()));
            role.setGroups((Set<Group>) groups);
        }
        return role;
    }

    /**
     * Transform role's PO to VO.
     *
     * @param role          role's PO
     * @param msg           return message
     * @return              role's VO
     */
    private RoleVO rolePO2VO(Role role, String msg) {
        RoleVO vo = new RoleVO();
        BeanUtils.copyProperties(role, vo);
        if (StringUtils.isBlank(msg)) {
            return vo;
        }
        vo.setMessage(msg);
        // Return success result.
        return (RoleVO) resultHelper.sucessResp(vo);
    }

    /**
     * Transform roles' PO to roles VO
     *
     * @param roles     roles' PO
     * @param msg       return message
     * @return          roles' VO
     */
    private ObjectsVO rolesPO2VO(Iterable<Role> roles, String msg) {
        List objList = poList2VOList(roles);
        ObjectsVO vos = transformer.voList2ObjectsVO(objList, msg);
        return (ObjectsVO) resultHelper.sucessResp(vos);
    }

    /**
     * Transform role's PO list to VO list.
     *
     * @param roles     role's PO list
     * @return          role's VO list
     */
    private List<RoleVO> poList2VOList(Iterable<Role> roles) {
        List<RoleVO> roleVOList = new ArrayList<>();
        for (Role role : roles) {
            RoleVO vo = rolePO2VO(role, "");
            roleVOList.add(vo);
        }
        return roleVOList;
    }
}
