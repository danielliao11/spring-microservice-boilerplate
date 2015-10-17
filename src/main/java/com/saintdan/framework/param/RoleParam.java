package com.saintdan.framework.param;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Role RESTFul param bean.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/16/15
 * @since JDK1.8
 */
public class RoleParam extends BaseParam implements Serializable {

    private static final long serialVersionUID = 8542867394907970893L;

    private Long id; // role's ID.

    private String name; // role's name

    private String description;

    private Set<Long> userIds = new HashSet<>(); // user ids

    private Set<Long> groupIds = new HashSet<>(); // group ids

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

    public Set<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(Set<Long> userIds) {
        this.userIds = userIds;
    }

    public Set<Long> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(Set<Long> groupIds) {
        this.groupIds = groupIds;
    }
}
