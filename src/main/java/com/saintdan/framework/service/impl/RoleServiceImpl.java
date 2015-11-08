package com.saintdan.framework.service.impl;

import com.saintdan.framework.component.Transformer;
import com.saintdan.framework.constant.CommonsConstant;
import com.saintdan.framework.constant.ControllerConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.enums.LogType;
import com.saintdan.framework.enums.ValidFlag;
import com.saintdan.framework.exception.CommonsException;
import com.saintdan.framework.param.RoleParam;
import com.saintdan.framework.po.Group;
import com.saintdan.framework.po.Role;
import com.saintdan.framework.po.User;
import com.saintdan.framework.repo.RoleRepository;
import com.saintdan.framework.service.GroupService;
import com.saintdan.framework.service.RoleService;
import com.saintdan.framework.service.UserService;
import com.saintdan.framework.tools.ErrorMsgHelper;
import com.saintdan.framework.vo.ObjectsVO;
import com.saintdan.framework.vo.PageVO;
import com.saintdan.framework.vo.RoleVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
public class RoleServiceImpl extends BaseServiceImpl<Role, Long> implements RoleService {

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    /**
     * Create new role.
     *
     * @param currentUser   current role
     * @param param         role's params
     * @return              role's VO
     * @throws CommonsException        SYS0111 user already existing, usr taken.
     */
    @Override
    public RoleVO create(RoleParam param, User currentUser) throws Exception {
        Role role = roleRepository.findByName(param.getName());
        if (role != null) {
            // Throw role already existing exception, name taken.
            throw new CommonsException(ErrorType.SYS0111,
                    ErrorMsgHelper.getReturnMsg(ErrorType.SYS0111, getClassT().getSimpleName(), getClassT().getSimpleName(), CommonsConstant.NAME));
        }
        return super.createByPO(RoleVO.class, roleParam2PO(param, new Role(), currentUser), currentUser);
    }

    /**
     * Show all roles' VO.
     *
     * @return          roles
     * @throws CommonsException        SYS0120 No role exists.
     */
    @Override
    public ObjectsVO getAllRoles() throws Exception {
        Iterable roles = roleRepository.findAll();
        if (((List) roles).isEmpty()) {
            // Throw no role exists exception.
            throw new CommonsException(ErrorType.SYS0120,
                    ErrorMsgHelper.getReturnMsg(ErrorType.SYS0120, getClassT().getSimpleName(), getClassT().getSimpleName()));
        }
        return transformer.pos2VO(ObjectsVO.class, roles, String.format(ControllerConstant.INDEX, getClassT()));
    }

    /**
     * Show roles' page VO.
     *
     * @param pageable      page
     * @return              roles' page VO
     * @throws CommonsException        SYS0120 No role exists.
     */
    @Override
    public PageVO getPage(Pageable pageable) throws Exception {
        Page<Role> rolePage = roleRepository.findAll(pageable);
        if (rolePage.getContent().isEmpty()) {
            // Throw no role exists exception.
            throw new CommonsException(ErrorType.SYS0120,
                    ErrorMsgHelper.getReturnMsg(ErrorType.SYS0120, getClassT().getSimpleName(), getClassT().getSimpleName()));
        }
        return transformer.poPage2VO(rolePage.getContent(), pageable, rolePage.getTotalElements(),
                String.format(ControllerConstant.INDEX, getClassT()));
    }

    /**
     * Show roles by ids.
     *
     * @param ids           roles' ids
     * @return              roles' PO
     * @throws CommonsException        SYS0120 No role exists.
     */
    @Override
    public Iterable<Role> getRolesByIds(Iterable<Long> ids) throws Exception {
        return roleRepository.findAll(ids);
    }

    /**
     * Show role VO by role's id.
     *
     * @param param     role's params
     * @return          role's VO
     * @throws CommonsException        SYS0122 Cannot find any role by id param.
     */
    @Override
    public RoleVO getRoleById(RoleParam param) throws Exception {
        Role role = roleRepository.findOne(param.getId());
        if (role == null) {
            // Throw role cannot find by id parameter exception.
            throw new CommonsException(ErrorType.SYS0122,
                    ErrorMsgHelper.getReturnMsg(ErrorType.SYS0122, getClassT().getSimpleName(), getClassT().getSimpleName(), CommonsConstant.ID));
        }
        return transformer.po2VO(RoleVO.class, role, String.format(ControllerConstant.SHOW, getClassT()));
    }

    /**
     * Get role's VO by usr.
     *
     * @param param     role's params
     * @return          role's VO
     * @throws CommonsException        SYS0122 Cannot find any role by name param.
     */
    @Override
    public RoleVO getRoleByName(RoleParam param) throws Exception {
        Role role = roleRepository.findByName(param.getName());
        if (role == null) {
            // Throw role cannot find by name parameter exception.
            throw new CommonsException(ErrorType.SYS0122,
                    ErrorMsgHelper.getReturnMsg(ErrorType.SYS0122, getClassT().getSimpleName(), getClassT().getSimpleName(), CommonsConstant.NAME));
        }
        return transformer.po2VO(RoleVO.class, role, String.format(ControllerConstant.SHOW, getClassT()));
    }

    /**
     * Update role.
     *
     * @param param     role's params
     * @return          role's VO
     * @throws CommonsException        SYS0122 Cannot find any role by id param.
     */
    @Override
    public RoleVO update(RoleParam param, User currentUser) throws Exception {
        Role role = roleRepository.findByName(param.getName());
        if (role == null) {
            // Throw cannot find any role by this id param.
            throw new CommonsException(ErrorType.SYS0122,
                    ErrorMsgHelper.getReturnMsg(ErrorType.SYS0122, getClassT().getSimpleName(), getClassT().getSimpleName(), CommonsConstant.ID));
        }
        return super.updateByPO(RoleVO.class, roleParam2PO(param, new Role(), currentUser), currentUser);
    }

    /**
     * Delete role.
     *
     * @param currentUser   current user
     * @param param         role's params
     * @throws CommonsException        SYS0122 Cannot find any role by id param.
     */
    @Override
    public void delete(RoleParam param, User currentUser) throws Exception {
        Role role = roleRepository.findOne(param.getId());
        if (role == null) {
            // Throw cannot find any role by this id param.
            throw new CommonsException(ErrorType.SYS0122,
                    ErrorMsgHelper.getReturnMsg(ErrorType.SYS0122, getClassT().getSimpleName(), getClassT().getSimpleName(), CommonsConstant.ID));
        }
        // Log delete operation.
        logHelper.logUsersOperations(LogType.DELETE, getClassT().getSimpleName(), currentUser);
        // Change valid flag to invalid.
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
    private RoleRepository roleRepository;

    @Autowired
    private Transformer transformer;

    /**
     * Transform role's param to PO.
     *
     * @param param         role's param
     * @param role          role
     * @param currentUser   currentUser
     * @return              role's PO
     */
    private Role roleParam2PO(RoleParam param, Role role, User currentUser) throws Exception {
        transformer.param2PO(getClassT(), param, role, currentUser);
        if (!StringUtils.isBlank(param.getUserIds())) {
            Iterable<User> roles = userService.getUsersByIds(transformer.idsStr2Iterable(param.getUserIds()));
            role.setUsers(transformer.iterable2Set(roles));
        }
        if (!StringUtils.isBlank(param.getGroupIds())) {
            Iterable<Group> groups = groupService.getGroupsByIds(transformer.idsStr2Iterable(param.getGroupIds()));
            role.setGroups((Set<Group>) groups);
        }
        return role;
    }

}
