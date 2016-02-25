package com.saintdan.framework.param;

import com.saintdan.framework.enums.LogType;

import java.io.Serializable;

/**
 * Param bean for {@link com.saintdan.framework.domain.LogDomain}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/28/15
 * @since JDK1.8
 */
public class LogParam implements Serializable {

    private static final long serialVersionUID = 891050528216283300L;

    private String loginIP;

    private LogType type;

    private String clientId;

    private String accessResource;

    public LogParam() {

    }

    public LogParam(String loginIP, LogType type, String clientId, String accessResource) {
        this.loginIP = loginIP;
        this.type = type;
        this.clientId = clientId;
        this.accessResource = accessResource;
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

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getAccessResource() {
        return accessResource;
    }

    public void setAccessResource(String accessResource) {
        this.accessResource = accessResource;
    }
}
