package com.saintdan.framework.vo;

import java.io.Serializable;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/30/15
 * @since JDK1.8
 */
public class UserVO extends ResultVO implements Serializable {

    private static final long serialVersionUID = 6597728015488383528L;

    private String name;

    private String username;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
