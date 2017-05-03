package com.saintdan.framework.vo;

import com.saintdan.framework.enums.OperationType;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * VO for {@link com.saintdan.framework.po.Log}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/27/15
 * @since JDK1.8
 */
public class LogVO implements Serializable {

  private static final long serialVersionUID = -8802363013216964724L;

  private String username;

  private String ip;

  private OperationType type;

  private LocalDateTime createDate;

  @Override public String toString() {
    final StringBuffer sb = new StringBuffer("LogVO{");
    sb.append("username='").append(username).append('\'');
    sb.append(", ip='").append(ip).append('\'');
    sb.append(", type=").append(type);
    sb.append(", createDate=").append(createDate);
    sb.append('}');
    return sb.toString();
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public OperationType getType() {
    return type;
  }

  public void setType(OperationType type) {
    this.type = type;
  }

  public LocalDateTime getCreateDate() {
    return createDate;
  }

  public void setCreateDate(LocalDateTime createDate) {
    this.createDate = createDate;
  }

  public String getLogType() {
    return getType().name();
  }
}
