package com.saintdan.framework.param;

import com.saintdan.framework.annotation.SignField;

import javax.validation.constraints.NotNull;

/**
 * Role RESTFul param bean.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/16/15
 * @since JDK1.8
 */
public class RoleParam extends BaseParam {

    private static final long serialVersionUID = 8542867394907970893L;

    @SignField
    private Long id; // role's ID.

    @SignField
    @NotNull(message = "Name cannnot be null.")
    private String name; // role's name

    @SignField
    private String description;

    @SignField
    private String userIds; // user ids string

    @SignField
    private String groupIds; // group ids string

    public RoleParam() {
    }

    public RoleParam(Long id) {
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

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }

    public String getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(String groupIds) {
        this.groupIds = groupIds;
    }
}
