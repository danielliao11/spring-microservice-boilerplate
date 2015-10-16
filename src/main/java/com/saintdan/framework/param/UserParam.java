package com.saintdan.framework.param;

import com.saintdan.framework.annotation.ParamField;

/**
 * User RESTFul param bean.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 9/22/15
 * @since JDK1.8
 */
public class UserParam extends BaseParam {

    private Long id; // User's ID.

    @ParamField
    private String usr; // Username

    @ParamField
    private String pwd; // Password

    @ParamField
    private String name; // The user's name

    private String description;

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
}
