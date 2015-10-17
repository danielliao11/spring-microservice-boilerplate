package com.saintdan.framework.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Roles VO.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/16/15
 * @since JDK1.8
 */
public class RolesVO extends ResultVO implements Serializable {

    private static final long serialVersionUID = -8801443054407506337L;

    private List<RoleVO> roleVOList;

    public List<RoleVO> getRoleVOList() {
        return roleVOList;
    }

    public void setRoleVOList(List<RoleVO> roleVOList) {
        this.roleVOList = roleVOList;
    }
}
