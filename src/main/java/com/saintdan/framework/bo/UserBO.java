package com.saintdan.framework.bo;

import com.saintdan.framework.annotation.ParamField;

/**
 * User service param bo.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 7/21/15
 * @since JDK1.8
 */
public class UserBO extends BaseBO {

    private int id;

    @ParamField
    private String usr;

    private String name;

    public UserBO(String usr) {
        this.usr = usr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsr() {
        return usr;
    }

    public void setUsr(String usr) {
        this.usr = usr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
