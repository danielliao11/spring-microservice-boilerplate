package com.saintdan.framework.param;

import com.saintdan.framework.annotation.SignField;

import javax.validation.constraints.NotNull;

/**
 * Param bean for {@link com.saintdan.framework.domain.GroupDomain}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/16/15
 * @since JDK1.8
 */
public class GroupParam extends BaseParam {

    private static final long serialVersionUID = 8542867394907970893L;

    @SignField
    private Long id; // role's ID.

    @SignField
    @NotNull(message = "Name cannot be null.")
    private String name; // role's name

    @SignField
    private String description;

    @SignField
    private String roleIds; // role ids string

    @SignField
    private String resourceIds; // resource ids string

    public GroupParam() {
    }

    public GroupParam(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }
}
