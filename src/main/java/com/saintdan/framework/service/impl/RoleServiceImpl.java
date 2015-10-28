package com.saintdan.framework.service.impl;

import com.saintdan.framework.component.ResultHelper;
import com.saintdan.framework.component.Transformer;
import com.saintdan.framework.constant.ControllerConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.exception.GroupException;
import com.saintdan.framework.exception.RoleException;
import com.saintdan.framework.exception.UserException;
import com.saintdan.framework.param.RoleParam;
import com.saintdan.framework.po.Group;
import com.saintdan.framework.po.Role;
import com.saintdan.framework.po.User;
import com.saintdan.framework.repo.RoleRepository;
import com.saintdan.framework.service.GroupService;
import com.saintdan.framework.service.RoleService;
import com.saintdan.framework.service.UserService;
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
     * @param param         role's params
     * @return              role's VO
     * @throws RoleException        ROL0031 Role already existing, name taken.
     * @throws UserException        USR0012 Cannot find any user by this id param.
     * @throws GroupException       GRP0012 Cannot find any group by this id param.
     */
    @Override
    public RoleVO create(RoleParam param) throws RoleException, UserException, GroupException {
        Role role = roleRepository.findByName(param.getName());
        if (role != null) {
            // Throw role already existing, name taken.
            throw new RoleException(ErrorType.ROL0031);
        }
        return rolePO2VO(roleRepository.save(roleParam2PO(param)),
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
     * @throws RoleException        ROL0011 Cannot find any role by this name param.
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
     * @param param         role's params
     * @return              role's VO
     * @throws RoleException        ROL0012 Cannot find any role by this id param.
     * @throws UserException        USR0012 Cannot find any user by this id param.
     * @throws GroupException       GRP0012 Cannot find any group by this id param.
     */
    @Override
    public RoleVO update(RoleParam param) throws RoleException, UserException, GroupException {
        if (!roleRepository.exists(param.getId())) {
            // Throw role cannot find by id parameter exception.
            throw new RoleException(ErrorType.ROL0012);
        }
        return rolePO2VO(roleRepository.save(roleParam2PO(param)),
                String.format(ControllerConstant.UPDATE, ROLE));
    }

    /**
     * Delete role.
     *
     * @param param         role's params.
     * @throws RoleException        ROL0012 Cannot find any role by this id param.
     */
    @Override
    public void delete(RoleParam param) throws RoleException {
        Role role = roleRepository.findOne(param.getId());
        if (role == null) {
            // Throw role cannot find by id parameter exception.
            throw new RoleException(ErrorType.ROL0012);
        }
        roleRepository.delete(role);
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
    private ResultHelper resultHelper;

    @Autowired
    private Transformer transformer;

    private final static String ROLE = "role";

    /**
     * Transform role's param to PO.
     *
     * @param param         role's param
     * @return              role's PO
     * @throws UserException            USR0012 Cannot find any user by this id param.
     * @throws GroupException           GRP0012 Cannot find any group by this id param.
     */
    private Role roleParam2PO(RoleParam param) throws UserException, GroupException {
        Role role = new Role();
        BeanUtils.copyProperties(param, role);
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
