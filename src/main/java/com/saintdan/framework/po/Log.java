package com.saintdan.framework.po;

import com.saintdan.framework.enums.LogType;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Log, record users' behavior.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/27/15
 * @since JDK1.8
 */
@Entity
@Table(name = "logs")
public class Log implements Serializable {

    private static final long serialVersionUID = 7088091769901805623L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    @Column(name = "login_ip", nullable = false, length = 50)
    private String loginIP;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String username;

    private String clientId;

    @Column(nullable = false)
    private LogType type;

    @CreatedDate
    @Column(nullable = false)
    private Date createDate = new Date();

    public Log() {

    }

    public Log(String loginIP, Long userId, String username, LogType type) {
        this.loginIP = loginIP;
        this.userId = userId;
        this.username = username;
        this.type = type;
    }

    public Log(String loginIP, Long userId, String username, String clientId, LogType type) {
        this.loginIP = loginIP;
        this.userId = userId;
        this.username = username;
        this.clientId = clientId;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
