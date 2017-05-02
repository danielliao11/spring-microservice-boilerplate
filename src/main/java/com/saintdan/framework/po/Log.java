package com.saintdan.framework.po;

import com.saintdan.framework.enums.OperationType;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedDate;

/**
 * Log, record users' behavior.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/27/15
 * @since JDK1.8
 */
@Entity @Table(name = "logs") public class Log implements Serializable {

  private static final long serialVersionUID = 7088091769901805623L;

  @GenericGenerator(
      name = "logSequenceGenerator",
      strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
      parameters = {
          @Parameter(name = "sequence_name", value = "logs_seq"),
          @Parameter(name = "initial_value", value = "1"),
          @Parameter(name = "increment_size", value = "1")
      }
  )
  @Id
  @GeneratedValue(generator = "logSequenceGenerator")
  @Column(updatable = false)
  private Long id;

  @NotEmpty
  @Column(nullable = false, length = 50)
  private String ip;

  @Column(nullable = false)
  private Long userId;

  @Column(nullable = false)
  private String username;

  private String clientId;

  private String accessResource;

  @Column(nullable = false)
  private OperationType type;

  @CreatedDate
  @Column(nullable = false)
  private LocalDateTime createdDate = LocalDateTime.now();

  public Log() {}

  @Override public String toString() {
    final StringBuffer sb = new StringBuffer("Log{");
    sb.append("id=").append(id);
    sb.append(", ip='").append(ip).append('\'');
    sb.append(", userId=").append(userId);
    sb.append(", username='").append(username).append('\'');
    sb.append(", clientId='").append(clientId).append('\'');
    sb.append(", accessResource='").append(accessResource).append('\'');
    sb.append(", type=").append(type);
    sb.append(", createdDate=").append(createdDate);
    sb.append('}');
    return sb.toString();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
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

  public OperationType getType() {
    return type;
  }

  public void setType(OperationType type) {
    this.type = type;
  }

  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(LocalDateTime createdDate) {
    this.createdDate = createdDate;
  }
}
