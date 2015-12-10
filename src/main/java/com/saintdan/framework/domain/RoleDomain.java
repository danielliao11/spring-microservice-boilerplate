package com.saintdan.framework.domain;

import com.saintdan.framework.exception.CommonsException;
import com.saintdan.framework.param.RoleParam;
import com.saintdan.framework.po.Role;
import com.saintdan.framework.po.User;
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
public interface RoleDomain extends BaseDomain<Role, Long> {

    /**
     * Create new role.
     *
     * @param currentUser   current user
     * @param param         role's params
     * @return              role's VO
     * @throws CommonsException        SYS0111 role already existing, name taken.
     */
    RoleVO create(RoleParam param, User currentUser) throws Exception;

    /**
     * Show all roles' VO.
     *
     * @return          roles
     * @throws CommonsException        SYS0120 No role exists.
     */
    ObjectsVO getAllRoles() throws Exception;

    /**
     * Show roles' page VO.
     *
     * @param pageable      page
     * @return              roles' page VO
     * @throws CommonsException        SYS0120 No role exists.
     */
    PageVO getPage(Pageable pageable) throws Exception;

    /**
     * Show roles by ids.
     *
     * @param ids           roles' ids
     * @return              roles' PO
     * @throws CommonsException        SYS0120 No role exists.
     */
    Iterable<Role> getRolesByIds(Iterable<Long> ids) throws Exception;

    /**
     * Show role VO by role's id.
     *
     * @param param     role's params
     * @return          role's VO
     * @throws CommonsException        SYS0122 Cannot find any role by id param.
     */
    RoleVO getRoleById(RoleParam param) throws Exception;

    /**
     * Get role's VO by name.
     *
     * @param param     role's params
     * @return          role's VO
     * @throws CommonsException        SYS0122 Cannot find any role by name param.
     */
    RoleVO getRoleByName(RoleParam param) throws Exception;

    /**
     * Update role.
     *
     * @param currentUser   current user
     * @param param         role's params
     * @return              role's VO
     * @throws CommonsException        SYS0122 Cannot find any role by id param.
     */
    RoleVO update(RoleParam param, User currentUser) throws Exception;

    /**
     * Delete role.
     *
     * @param currentUser   current user
     * @param param         role's params
     * @throws CommonsException        SYS0122 Cannot find any role by id param.
     */
    void delete(RoleParam param, User currentUser) throws Exception;

}
