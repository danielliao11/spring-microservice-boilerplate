package com.saintdan.framework.param;

import com.saintdan.framework.enums.LogType;

import java.io.Serializable;

/**
 * Log' param
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/28/15
 * @since JDK1.8
 */
public class LogParam implements Serializable {

    private static final long serialVersionUID = 891050528216283300L;

    private String loginIP;

    private Long userId;

    private String username;

    private LogType type;

    public String getLoginIP() {
        return loginIP;
    }

    public void setLoginIP(String loginIP) {
        this.loginIP = loginIP;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LogType getType() {
        return type;
    }

    public void setType(LogType type) {
        this.type = type;
    }
}
