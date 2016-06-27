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
@Table( name = "logs" )
public class Log implements Serializable {

  private static final long serialVersionUID = 7088091769901805623L;

  @Id
  @GeneratedValue( generator = "logs_seq", strategy = GenerationType.SEQUENCE )
  @SequenceGenerator( name = "logs_seq", sequenceName = "logs_seq", allocationSize = 1 )
  @Column(updatable = false)
  private Long id;

  @NotEmpty
  @Column( name = "login_ip", nullable = false, length = 50 )
  private String loginIP;

  @Column( nullable = false )
  private Long userId;

  @Column( nullable = false )
  private String username;

  private String clientId;

  private String accessResource;

  @Column( nullable = false )
  private LogType type;

  @CreatedDate
  @Column( nullable = false )
  private Date createDate = new Date();

  public Log() {
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
}
