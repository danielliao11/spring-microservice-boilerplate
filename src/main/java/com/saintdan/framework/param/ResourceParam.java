package com.saintdan.framework.param;

import com.saintdan.framework.annotation.SignField;

import javax.validation.constraints.NotNull;

/**
 * Resource RESTFul param bean.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/16/15
 * @since JDK1.8
 */
public class ResourceParam extends BaseParam {

    private static final long serialVersionUID = 8542867394907970893L;

    @SignField
    private Long id; // role's ID.

    @SignField
    @NotNull(message = "Name cannnot be null.")
    private String name; // role's name

    @SignField
    @NotNull(message = "Path cannnot be null.")
    private String path;

    @SignField
    @NotNull(message = "Priority cannnot be null.")
    private Integer priority;

    @SignField
    private String description;

    @SignField
    private String groupIds; // role ids string

    public ResourceParam() {

    }

    public ResourceParam(Long id) {
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(String groupIds) {
        this.groupIds = groupIds;
    }
}
