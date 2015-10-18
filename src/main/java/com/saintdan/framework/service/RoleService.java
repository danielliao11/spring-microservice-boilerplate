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

    RoleVO create(RoleParam param) throws RoleException;

    RolesVO getAllRoles() throws RoleException;

    RoleVO getRoleById(RoleParam param) throws RoleException;

    RoleVO getRoleByName(RoleParam param) throws RoleException;

    RoleVO update(RoleParam param) throws RoleException;

    void delete(RoleParam param) throws RoleException;

}
