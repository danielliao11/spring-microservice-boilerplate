package com.saintdan.framework.service;

import com.saintdan.framework.exception.GroupException;
import com.saintdan.framework.exception.RoleException;
import com.saintdan.framework.exception.UserException;
import com.saintdan.framework.param.RoleParam;
import com.saintdan.framework.po.Role;
import com.saintdan.framework.vo.ObjectsVO;
import com.saintdan.framework.vo.PageVO;
import com.saintdan.framework.vo.RoleVO;
import org.springframework.data.domain.Pageable;

/**
 * Role's Service.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/16/15
 * @since JDK1.8
 */
public interface RoleService {

    /**
     * Create new role.
     *
     * @param param         role's params
     * @return              role's VO
     * @throws RoleException        ROL0031 Role already existing, name taken.
     * @throws UserException        USR0012 Cannot find any user by this id param.
     * @throws GroupException       GRP0012 Cannot find any group by this id param.
     */
    RoleVO create(RoleParam param) throws RoleException, UserException, GroupException;

    /**
     * Show all roles' VO.
     *
     * @return              roles' VO
     * @throws RoleException        ROL0011 No role exist.
     */
    ObjectsVO getAllRoles() throws RoleException;

    /**
     * Show users' page VO.
     *
     * @param pageable      page
     * @return              roles' page VO
     * @throws RoleException        ROL0011 No role exists.
     */
    PageVO getPage(Pageable pageable) throws RoleException;

    /**
     * Show roles by ids.
     *
     * @param ids           roles' ids
     * @return              roles' PO
     * @throws RoleException        ROL0012 Cannot find any role by this id param.
     */
    Iterable<Role> getRolesByIds(Iterable<Long> ids) throws RoleException;

    /**
     * Show role's VO by role's id.
     *
     * @param param         role's params
     * @return              role's VO
     * @throws RoleException        ROL0012 Cannot find any role by this id param.
     */
    RoleVO getRoleById(RoleParam param) throws RoleException;

    /**
     * Show role's VO by role's name.
     *
     * @param param         role's params
     * @return              role's VO
     * @throws RoleException        ROL0013 Cannot find any role by this name param.
     */
    RoleVO getRoleByName(RoleParam param) throws RoleException;

    /**
     * Update role.
     *
     * @param param         role's params
     * @return              role's VO
     * @throws RoleException        ROL0012 Cannot find any role by this id param.
     * @throws UserException        USR0012 Cannot find any user by this id param.
     * @throws GroupException       GRP0012 Cannot find any group by this id param.
     */
    RoleVO update(RoleParam param) throws RoleException, UserException, GroupException;

    /**
     * Delete role.
     *
     * @param param         role's params.
     * @throws RoleException        ROL0012 Cannot find any role by this id param.
     */
    void delete(RoleParam param) throws RoleException;

}
