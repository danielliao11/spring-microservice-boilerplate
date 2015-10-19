package com.saintdan.framework.service;

import com.saintdan.framework.exception.RoleException;
import com.saintdan.framework.param.RoleParam;
import com.saintdan.framework.vo.RoleVO;
import com.saintdan.framework.vo.RolesVO;

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
     */
    RoleVO create(RoleParam param) throws RoleException;

    /**
     * Show all roles' VO.
     *
     * @return              roles' VO
     * @throws RoleException        ROL0011 No role exist.
     */
    RolesVO getAllRoles() throws RoleException;

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
     * @throws RoleException        ROL0011 Cannot find any role by this name param.
     */
    RoleVO getRoleByName(RoleParam param) throws RoleException;

    /**
     * Update role.
     *
     * @param param         role's params
     * @return              role's VO
     * @throws RoleException        ROL0012 Cannot find any role by this id param.
     */
    RoleVO update(RoleParam param) throws RoleException;

    /**
     * Delete role.
     *
     * @param param         role's params.
     * @throws RoleException        ROL0012 Cannot find any role by this id param.
     */
    void delete(RoleParam param) throws RoleException;

}
