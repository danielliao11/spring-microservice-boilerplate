package com.saintdan.framework.vo;

import com.saintdan.framework.enums.LogType;

import java.io.Serializable;
import java.util.Date;

/**
 * Log's VO.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/27/15
 * @since JDK1.8
 */
public class LogVO implements Serializable {

    private static final long serialVersionUID = -8802363013216964724L;

    private String username;

    private String loginIP;

    private LogType type;

    private Date createDate;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLoginIP() {
        return loginIP;
    }

    public void setLoginIP(String loginIP) {
        this.loginIP = loginIP;
    }

    public LogType getType() {
        return type;
    }

    public void setType(LogType type) {
        this.type = type;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getLogType() {
        return getType().name();
    }
}
