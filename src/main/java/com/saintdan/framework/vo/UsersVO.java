package com.saintdan.framework.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Users VO
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/16/15
 * @since JDK1.8
 */
public class UsersVO extends ResultVO implements Serializable {

    private static final long serialVersionUID = -3876887091814072039L;

    private List<UserVO> userVOList;

    public List<UserVO> getUserVOList() {
        return userVOList;
    }

    public void setUserVOList(List<UserVO> userVOList) {
        this.userVOList = userVOList;
    }
}
