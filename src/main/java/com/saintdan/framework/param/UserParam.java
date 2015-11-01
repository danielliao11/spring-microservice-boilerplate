package com.saintdan.framework.param;

import com.saintdan.framework.annotation.SignField;
import com.saintdan.framework.annotation.ValidationField;

import java.io.Serializable;

/**
 * User RESTFul param bean.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 9/22/15
 * @since JDK1.8
 */
public class UserParam extends BaseParam implements Serializable {

    private static final long serialVersionUID = -9153801716112918626L;

    @SignField
    private Long id; // user's ID

    @SignField
    @ValidationField
    private String usr; // username

    @SignField
    @ValidationField
    private String pwd; // password

    @SignField
    @ValidationField
    private String name; // user's name

    @SignField
    private String description;

    @SignField
    private String roleIds; // role ids string

    public UserParam() {

    }

    public UserParam(Long id) {
        this.id = id;
    }

    public UserParam(String usr) {
        this.usr = usr;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsr() {
        return usr;
    }

    public void setUsr(String usr) {
        this.usr = usr;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
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
}
